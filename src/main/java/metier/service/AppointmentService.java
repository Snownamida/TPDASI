package metier.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import dao.EmployeeDao;
import dao.MediumDao;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import util.Message;

public class AppointmentService {
    public static List<Medium> getAvailableMediums(String dateString) {
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        List<Employee> employees = EmployeeDao.getAvailable(date);
        List<String> availableSex = employees.stream().map(Employee::getSex).distinct().collect(Collectors.toList());
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
                .filter(employee -> employee.getSex().equals(medium.getGenre()))
                .collect(Collectors.toList());
        if (availableEmployees.isEmpty()) {
            System.err.println("Aucun employé disponible");
            return null;
        }
        // Choose a random employee
        return availableEmployees.get((int) (Math.random() * availableEmployees.size()));
    }

    public static Consultation CreateAppointment(Client client, Employee employee, String date, Medium medium,
            int duree) {
        try {
            Consultation consultation = ConsultationService.creerConsultation(date,
                    duree, null, medium, client, employee);
            if (consultation == null)
                throw new Exception("Erreur lors de la création du rendez-vous");

            Message.envoyerNotification(client.getNom(),
                    "Bonjour " + client.getPrenom() + ",\n" + "Votre rendez-vous avec " + employee.getPrenom() + " "
                            + employee.getNom() + " est confirmé pour le " + date + ".\n"
                            + "Vous pouvez me contacter au "
                            + employee.getPhone() + " pour toute question.\n" + "Cordialement,\n" + employee.getPrenom()
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
