/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.RollbackException;

import com.google.maps.model.LatLng;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeeDao;
import dao.JpaUtil;
import dao.MediumDao;
import metier.modele.AstralProfile;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import util.AstroNetApi;
import util.GeoNetApi;
import util.Message;

/**
 *
 * @author staider
 */
/**
 * This class provides various services related to users, such as authentication, user retrieval, and consultation management.
 */
public class UserServices {

    /**
     * Authenticates an employee based on their email and password.
     * 
     * @param mail The email of the employee.
     * @param motDePasse The password of the employee.
     * @return The ID of the authenticated employee, or -1 if authentication fails.
     */
    public static long authentifierEmploye(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Employee employee = EmployeeDao.findByEmail(mail);
        if (employee != null && employee.getMotDePasse().equals(motDePasse)) {
            return employee.getId();
        } else {
            return -1;
        }
    }

    /**
     * Cherche un employé par son adresse e-mail.
     * 
     * @param mail l'adresse e-mail de l'employé à chercher
     * @return l'employé correspondant à l'adresse e-mail spécifiée, ou null si aucun employé n'est trouvé
     */
    public static Employee chercherEmployeParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return EmployeeDao.findByEmail(mail);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    /**
     * Consulte la liste des employés.
     *
     * @return la liste des employés
     */
    public static List<Employee> consulterListeEmployes() {
        JpaUtil.creerContextePersistance();
        return EmployeeDao.getAll();
    }


    /**
     * Consults the list of all available mediums.
     *
     * @return the list of all available mediums
     */
    public static List<Medium> consulterListeMediums() {
        JpaUtil.creerContextePersistance();
        return MediumDao.getAll();
    }

    /**
     * Retrieves the predictions based on the client's astrological profile and
     * other parameters.
     *
     * @param couleurPorteBonheur The client's lucky color.
     * @param animalTotem         The client's spirit animal.
     * @param love                The love prediction.
     * @param health              The health prediction.
     * @param job                 The job prediction.
     * @return The list of predictions.
     */
    public static List<String> GetHelp(Client client, int love, int health, int job) {
        AstroNetApi astroApi = new AstroNetApi();
        try {
            List<String> predictions = astroApi.getPredictions(client.getAstralProfile().getCouleurPorteBonheur(),
                    client.getAstralProfile().getAnimalTotem(), love, health, job);
            return predictions;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates the comment and duration of a consultation.
     * 
     * @param consultation The consultation to be updated.
     * @param comment The new comment for the consultation.
     * @param duration The new duration for the consultation.
     */
    public static void FinConsultation(Consultation consultation, String comment, int duration) {
        consultation.setCommentaire(comment);
        consultation.setDuree(duration);
        ConsultationDao.update(consultation);
    }

    /**
     * Creates a new consultation with the given parameters.
     *
     * @param dateString  The date of the consultation in the format "yyyy-MM-dd".
     * @param commentaire The comment for the consultation.
     * @param medium      The medium for the consultation.
     * @param client      The client for the consultation.
     * @param employee    The employee for the consultation.
     * @return The created consultation, or null if an error occurred.
     */
    public static Consultation creerConsultation(String dateString, String commentaire, Medium medium,
            Client client, Employee employee) {

        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        try {
            Consultation consultation = new Consultation(date, medium, client, employee, commentaire);
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            ConsultationDao.create(consultation);
            client.getConsultations().add(consultation);
            ClientDao.update(client);
            JpaUtil.validerTransaction();
            return consultation;
        } catch (RollbackException re) {
            re.printStackTrace();
            JpaUtil.annulerTransaction();
            return null;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            return null;
        }
    }

    /**
     * This method is used to register a client.
     * 
     * @param nom            the last name of the client
     * @param prenom         the first name of the client
     * @param mail           the email address of the client
     * @param motDePasse     the password of the client
     * @param adressePostale the postal address of the client
     * @param telephone      the phone number of the client
     * @param birthdateString the birthdate of the client in the format "yyyy-MM-dd"
     * @return true if the client is successfully registered, false otherwise
     */
    public static Boolean inscrireClient(String nom, String prenom, String mail, String motDePasse,
            String adressePostale, String telephone,
            String birthdateString) {

        Date birthdate;
        try {
            birthdate = new SimpleDateFormat("yyyy-MM-dd").parse(birthdateString);
        } catch (ParseException e) {
            e.printStackTrace();
            Message.envoyerMail("noreply@votreentreprise.com", mail, "Erreur lors de l'inscription",
                    "Invalid date format. Please use yyyy-MM-dd");
            return false;
        }

        try {
            Client client = new Client(nom, prenom, mail, motDePasse, adressePostale, telephone, birthdate);
            LatLng clientCoord = GeoNetApi.getLatLng(adressePostale);
            if (clientCoord == null) {
                sendErrorEmail(mail, "Adresse postale invalide");
                return false;
            }

            client.setLatitude(clientCoord.lat);
            client.setLongitude(clientCoord.lng);

            AstroNetApi astroApi = new AstroNetApi();

            List<String> profil = astroApi.getProfil(prenom, birthdate);
            String signeZodiaque = profil.get(0);
            String signeChinois = profil.get(1);
            String couleur = profil.get(2);
            String animal = profil.get(3);

            AstralProfile astralProfile = new AstralProfile(couleur, animal, signeChinois, signeZodiaque);

            client.setAstralProfile(astralProfile);

            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();
            ClientDao.create(client);
            JpaUtil.validerTransaction();

            // Send confirmation email
            sendConfirmationEmail(client);
            return true;

        } catch (RollbackException re) {
            re.printStackTrace();
            JpaUtil.annulerTransaction();
            sendErrorEmail(mail, "Erreur lors de l'enregistrement dans la base de données (RollbackException)");
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            JpaUtil.annulerTransaction();
            sendErrorEmail(mail, "Erreur lors de l'enregistrement dans la base de données (Other Exception)");
            return false;
        }
    }

    /**
     * Authenticates a client based on their email and password.
     * 
     * @param mail The email of the client.
     * @param motDePasse The password of the client.
     * @return The ID of the authenticated client, or -1 if authentication fails.
     */
    public static long authentifierClient(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Client client = ClientDao.findByEmail(mail);
        if (client != null && client.getMotDePasse().equals(motDePasse)) {
            return client.getId();
        } else {
            return -1;
        }
    }

    /**
     * Cherche un client par son adresse e-mail.
     *
     * @param mail l'adresse e-mail du client à chercher
     * @return le client correspondant à l'adresse e-mail spécifiée, ou null s'il n'existe pas
     */
    public static Client chercherClientParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findByEmail(mail);
    }

    /**
     * Cherche un client par son identifiant.
     *
     * @param id L'identifiant du client à chercher.
     * @return Le client correspondant à l'identifiant spécifié, ou null si aucun client n'est trouvé.
     */
    public static Client chercherClientParId(Long id) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findById(id);
    }

    /**
     * Consulte la liste des clients.
     * 
     * @return la liste des clients
     */
    public static List<Client> consulterListeClients() {
        JpaUtil.creerContextePersistance();
        return ClientDao.getAll();
    }

    /**
     * Sends a confirmation email to the specified client.
     *
     * @param client the client to send the email to
     */
    private static void sendConfirmationEmail(Client client) {
        String subject = "Inscription réussie";
        String body = "Merci, votre inscription a été enregistrée avec succès.";

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }

    /**
     * Sends an error email to the specified recipient.
     *
     * @param destinataire the email address of the recipient
     * @param error the error message to be included in the email body
     */
    private static void sendErrorEmail(String destinataire, String error) {
        String subject = "Erreur lors de l'inscription";
        String body = "Désolé, une erreur est survenue lors de votre inscription.\nErreur : " + error;

        Message.envoyerMail("noreply@votreentreprise.com", destinataire, subject, body);
    }

    /**
     * Authenticates a user based on their email and password.
     * 
     * @param email    the email of the user
     * @param password the password of the user
     * @return an array containing the user's ID and role if authentication is successful, or null if authentication fails
     */
    public static Object[] Authenticate(String email, String password) {
        // Extract domain name
        String[] parts = email.split("@");
        if (parts.length == 2) {
            String domain = parts[1];
            // Check if domain name is "predictif"
            if (domain.equals("predictif.fr")) {
                Long id = authentifierEmploye(email, password);
                if (id != -1) {
                    return new Object[] { id, "employee" };
                } else {
                    System.out.println("Invalid email or password");
                    return null;
                }
            } else {
                Long id = authentifierClient(email, password);
                if (id != -1) {
                    return new Object[] { id, "client" };
                } else {
                    System.out.println("Invalid email or password");
                    return null;
                }
            }
        } else {
            System.out.println("Invalid email format");
            return null;
        }
    }

    /**
     * Retrieves the list of available mediums based on the given date.
     *
     * @param dateString The date in the format "yyyy-MM-dd".
     * @return The list of available mediums.
     */
    public static List<Medium> getAvailableMediums(String dateString) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        List<Employee> employees = EmployeeDao.getAvailable(date);
        List<String> availableSex = employees.stream().map(Employee::getGenre).distinct().collect(Collectors.toList());
        return MediumDao.getAll().stream().filter(medium -> availableSex.contains(medium.getGenre()))
                .collect(Collectors.toList());
    }

    /**
     * Chooses an available employee based on the given medium and date.
     *
     * @param date The date for which to retrieve available employees.
     * @return A list of available employees.
     */
    public static Employee chooseEmployee(Medium medium, String dateString) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        List<Employee> employees = EmployeeDao.getAvailable(date);
        List<Employee> availableEmployees = employees.stream()
                .filter(employee -> employee.getGenre().equals(medium.getGenre()))
                .collect(Collectors.toList());
        if (availableEmployees.isEmpty()) {
            System.err.println("Aucun employé disponible");
            return null;
        }
        // Choose a random employee
        return availableEmployees.get((int) (Math.random() * availableEmployees.size()));
    }

    /**
     * Creates an appointment for a client with an employee using the specified date and medium.
     * 
     * @param client the client for the appointment
     * @param employee the employee for the appointment
     * @param date the date of the appointment
     * @param medium the medium for the appointment
     * @return the created consultation appointment
     */
    public static Consultation CreateAppointment(Client client, Employee employee, String date, Medium medium) {
        try {
            Consultation consultation = creerConsultation(date,
                    null, medium, client, employee);
            if (consultation == null)
                throw new Exception("Erreur lors de la création du rendez-vous");

            Message.envoyerNotification(client.getNom(),
                    "Bonjour " + client.getPrenom() + ",\n" + "Votre rendez-vous avec " + employee.getPrenom() + " "
                            + employee.getNom() + " est confirmé pour le " + date + ".\n"
                            + "Vous pouvez me contacter au "
                            + employee.getTelephone() + " pour toute question.\n" + "Cordialement,\n" + employee.getPrenom()
                            + " " + employee.getNom());
            return consultation;
        } catch (Exception e) {
            Message.envoyerMail("noreply@votreentreprise.com", client.getMail(),
                    "Erreur lors de la création du rendez-vous",
                    "Bonjour " + client.getPrenom() + ",\n"
                            + "Nous avons rencontré un problème lors de la création de votre rendez-vous avec "
                            + employee.getPrenom() + " " + employee.getNom() + " pour le " + date + ".\n"
                            + "Veuillez nous excuser pour la gêne occasionnée.\n" + "Cordialement");
            e.printStackTrace();
            return null;
        }
    }

}
