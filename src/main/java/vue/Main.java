/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.List;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeeDao;
import dao.JpaUtil;
import dao.MediumDao;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import metier.service.AppointmentService;
import metier.service.AuthenticationService;
import metier.service.ClientService;
import metier.service.EmployeeService;

/**
 *
 * @author jsun
 */
public class Main {

    // ANSI 转义码
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String SEPARATOR = ANSI_RED + "--------------------------------------------------" + ANSI_RESET;

    public static void main(String[] args) {
        JpaUtil.desactiverLog();
        JpaUtil.creerFabriquePersistance();

        EmployeeService.InitEmployee();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Inscription client" + ANSI_RESET);
        ClientService.inscrireClient("Taider", "Samy", "sqgsg@qdf.sf", "123456", "Lyon", "1234567890",
                "2003-08-27");
        System.err.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Client dans BD : " + ANSI_RESET);
        List<Client> clients = ClientService.consulterListeClients();
        System.out.println(clients);
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Employes dans BD : " + ANSI_RESET);
        List<Employee> employes = EmployeeService.consulterListeEmployes();
        System.out.println(employes);

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Trying to take an appointment... " + ANSI_RESET);
        Consultation consultation = AppointmentService.CreateAppointment(clients.get(0), employes.get(0), "2024-9-30",
                null, 20);
        consultation.setCommentaire("this client was very depressed and i helped him");
        ConsultationDao.update(consultation);
        System.out.println(consultation);
        System.out.println();

        TestAuthentification();

        System.out.println(SEPARATOR);
        List<Consultation> consultationList = clients.get(0).getConsultations();
        System.out.println(
                "previous consultations for client : " + clients.get(0).getNom() + " " + clients.get(0).getPrenom());

        for (Consultation c : consultationList) {
            // Print employee and comment
            System.out.println("Employee : ");
            System.out.println(c.getEmployee());
            System.out.println("Comment : ");
            System.out.printf(c.getCommentaire());
            System.out.println("\n");
        }
        System.out.println("--------------------------------------------------");
        System.out.println("astral profile of this client : \n" + clients.get(0).getAstralProfile());
        System.out.println("--------------------------------------------------");

        // Get help
        System.out.println("Get help !!!");
        int love = 1;
        int health = 1;
        int job = 1;
        List<String> predictions = EmployeeService.GetHelp(clients.get(0), love, health, job);
        String lovePrediction = predictions.get(0);
        String healthPrediction = predictions.get(1);
        String jobPrediction = predictions.get(2);

        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + lovePrediction);
        System.out.println("[ Santé ] " + healthPrediction);
        System.out.println("[Travail] " + jobPrediction);
        System.out.println("--------------------------------------------------");
        System.out.println("Fin de la consultation...");
        String comment = "very intresting consultation";
        int duration = 30;
        EmployeeService.FinConsultation(consultation, comment, duration);
        ConsultationDao.getAll();
        for (Consultation c : ConsultationDao.getAll()) {
            System.out.println(c);
        }
        System.out.println("--------------------------------------------------");
        System.out.println("Voici les Medium qui existent dans la base de données : ");
        EmployeeService.InitMedium();
        List<Medium> mediums = MediumDao.getAll();
        for (Medium m : mediums) {
            System.out.println(m);
        }
        JpaUtil.fermerFabriquePersistance();
    }

    private static void TestAuthentification() {
        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Authetication of an employee with correct infos" + ANSI_RESET);
        Object[] result = AuthenticationService.Authenticate("john.smith@predictif.fr", "password1");
        if (result != null) {
            System.out.println(ANSI_RED + "authetification...OK " + ANSI_RESET);
            if (result[1].equals("employee")) {
                System.out.println("Employee : " + EmployeeDao.findById((Long) result[0]));
            } else {
                System.out.println("Client : " + ClientDao.findById((Long) result[0]));
            }
        } else {
            System.out.println("authetification...KO ");
        }
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Authetication of an employee with incorrect infos" + ANSI_RESET);
        result = AuthenticationService.Authenticate("john.smith@predictif.fr", "password2");
        if (result != null) {
            System.out.println(ANSI_RED + "authetification...OK " + ANSI_RESET);
            if (result[1].equals("employee")) {
                System.out.println("Employee : " + EmployeeDao.findById((Long) result[0]));
            } else {
                System.out.println("Client : " + ClientDao.findById((Long) result[0]));
            }
        } else {
            System.out.println("authetification...KO ");
        }
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Authetication of an client with correct infos" + ANSI_RESET);
        result = AuthenticationService.Authenticate("sqgsg@qdf.sf", "123456");
        if (result != null) {
            System.out.println(ANSI_RED + "authetification...OK " + ANSI_RESET);
            if (result[1].equals("employee")) {
                System.out.println("Employee : " + EmployeeDao.findById((Long) result[0]));
            } else {
                System.out.println("Client : " + ClientDao.findById((Long) result[0]));
            }
        } else {
            System.out.println("authetification...KO ");
        }
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Authetication of an client with incorrect infos" + ANSI_RESET);
        result = AuthenticationService.Authenticate("sqgsg@qdf.sf", "1234567");

        if (result != null) {
            System.out.println(ANSI_RED + "authetification...OK " + ANSI_RESET);
            if (result[1].equals("employee")) {
                System.out.println("Employee : " + EmployeeDao.findById((Long) result[0]));
            } else {
                System.out.println("Client : " + ClientDao.findById((Long) result[0]));
            }
        } else {
            System.out.println("authetification...KO ");
        }
        System.out.println();

    }

}