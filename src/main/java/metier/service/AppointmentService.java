package metier.service;

import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import util.Message;

public class AppointmentService {
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
