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
public class UserServices {

    // Ce service identifie un employe à partir de son adresse mail, puis vérifie si
    // le mot de passe indiqué correspond au mot de passe enregistré. Ce service
    // renvoie l'entité Employe si l'authentification a réussie, ou null en cas
    // d'échec.
    public static long authentifierEmploye(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Employee employee = EmployeeDao.findByEmail(mail);
        if (employee != null && employee.getMotDePasse().equals(motDePasse)) {
            return employee.getId();
        } else {
            return -1;
        }
    }

    public static Employee chercherEmployeParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return EmployeeDao.findByEmail(mail);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    public static List<Employee> consulterListeEmployes() {
        JpaUtil.creerContextePersistance();
        return EmployeeDao.getAll();
    }


    public static List<Medium> consulterListeMediums() {
        JpaUtil.creerContextePersistance();
        return MediumDao.getAll();
    }

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

    public static void FinConsultation(Consultation consultation, String comment, int duration) {
        consultation.setCommentaire(comment);
        consultation.setDuree(duration);
        ConsultationDao.update(consultation);
    }

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

    // Ce service identifie un client à partir de son adresse mail, puis vérifie si
    // le mot de passe indiqué correspond au mot de passe enregistré. Ce service
    // renvoie l'entité Client si l'authentification a réussie, ou null en cas
    // d'échec.
    public static long authentifierClient(String mail, String motDePasse) {

        JpaUtil.creerContextePersistance();
        Client client = ClientDao.findByEmail(mail);
        if (client != null && client.getMotDePasse().equals(motDePasse)) {
            return client.getId();
        } else {
            return -1;
        }
    }

    public static Client chercherClientParMail(String mail) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findByEmail(mail);
    }

    public static Client chercherClientParId(Long id) {
        JpaUtil.creerContextePersistance();
        return ClientDao.findById(id);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    // (nom/prénom).
    public static List<Client> consulterListeClients() {
        JpaUtil.creerContextePersistance();
        return ClientDao.getAll();
    }

    private static void sendConfirmationEmail(Client client) {
        String subject = "Inscription réussie";
        String body = "Merci, votre inscription a été enregistrée avec succès.";

        Message.envoyerMail("noreply@votreentreprise.com", client.getMail(), subject, body);
    }

    private static void sendErrorEmail(String destinataire, String error) {
        String subject = "Erreur lors de l'inscription";
        String body = "Désolé, une erreur est survenue lors de votre inscription.\nErreur : " + error;

        Message.envoyerMail("noreply@votreentreprise.com", destinataire, subject, body);
    }

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
