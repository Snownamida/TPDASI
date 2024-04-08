/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.List;
import java.util.Map;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeeDao;
import dao.JpaUtil;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import metier.service.InitializationService;
import metier.service.UserServices;


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

        InitializationService.InitEmployee();
        InitializationService.InitMedium();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Inscription client" + ANSI_RESET);
        UserServices.inscrireClient("Taider", "Samy", "samsam@qdf.fr", "123456", "Lyon", "1234567890",
                "2003-08-27");
        UserServices.inscrireClient("Sun", "Jixiang", "jsun@htk.com", "456789", "Paris", "0987654321",
                "2003-12-21");
        System.err.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Clients dans BD : " + ANSI_RESET);
        List<Client> clients = UserServices.consulterListeClients();
        System.out.println(clients);
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Employes dans BD : " + ANSI_RESET);
        List<Employee> employes = UserServices.consulterListeEmployes();
        System.out.println(employes);
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Mediums dans BD : " + ANSI_RESET);
        List<Medium> mediums = UserServices.consulterListeMediums();
        System.out.println(mediums);
        System.out.println();

        TestAuthentification();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Essayons de prendre RDV... " + ANSI_RESET);
        String dateString = "2024-9-30";
        System.out.println(ANSI_RED + "Date choisie : " + dateString + ANSI_RESET);
        System.out.println(ANSI_RED + "Liste des Mediums dispos " + ANSI_RESET);
        List<Medium> availableMediums = UserServices.getAvailableMediums(dateString);
        System.out.println(availableMediums);
        System.out.println();

        Medium mediumChosen = availableMediums.get(1);
        System.out.println(ANSI_RED + "Medium chosen : " + ANSI_RESET);
        System.out.println(mediumChosen);
        System.out.println();

        Employee employeeChosen = UserServices.chooseEmployee(mediumChosen, dateString);
        System.out.println(ANSI_RED + "Employee chosen : " + ANSI_RESET);
        System.out.println(employeeChosen);
        System.out.println();
        Consultation consultation = UserServices.CreateAppointment(clients.get(0),
                employeeChosen,
                dateString,
                mediumChosen);
        ConsultationDao.update(consultation);
        System.out.println(consultation);
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Re-check Mediums dispos" + ANSI_RESET);
        List<Medium> availableMediums2 = UserServices.getAvailableMediums(dateString);
        System.out.println(availableMediums2);
        System.out.println();

        System.out.println(SEPARATOR);
        List<Consultation> consultationList = clients.get(0).getConsultations();
        System.out.println(ANSI_RED +
                "anciennes consultations du client : " + clients.get(0).getNom() + " " + clients.get(0).getPrenom()
                + ANSI_RESET);
        System.out.println(consultationList);
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(
                ANSI_RED + "profile astral du client : \n" + ANSI_RESET);
        System.out.println(clients.get(0).getAstralProfile());
        System.out.println(consultationList);

        // Get help
        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "A l'aide !!!" + ANSI_RESET);
        int love = 1;
        int health = 1;
        int job = 1;
        List<String> predictions = UserServices.GetHelp(clients.get(0), love, health, job);
        String lovePrediction = predictions.get(0);
        String healthPrediction = predictions.get(1);
        String jobPrediction = predictions.get(2);
        System.out.println("~<[ Prédictions ]>~");
        System.out.println("[ Amour ] " + lovePrediction);
        System.out.println("[ Santé ] " + healthPrediction);
        System.out.println("[Travail] " + jobPrediction);
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Fin de la consultation..." + ANSI_RESET);
        String comment = "consultation très interessante";
        int duration = 30;
        System.out.println(ANSI_RED +
                "commentaire laissé : " + comment + "\n" + "durée : " + duration + " minutes" + ANSI_RESET);
        UserServices.FinConsultation(consultation, comment, duration);
        System.out.println(ANSI_RED + "Consultations dans BD : " + ANSI_RESET);
        ConsultationDao.getAll();
        System.out.println(ConsultationDao.getAll());
        System.out.println();

        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Statistiques de l'entreprise : " + ANSI_RESET);
        System.out.println();
        // add consultations for samy
        

        System.out.println("consultations par client :");
        System.out.println();

        Map<Client, Long> consultationCountPerClient = ConsultationDao.getConsultationCountPerClient();
        for (Map.Entry<Client, Long> entry : consultationCountPerClient.entrySet()) {
            System.out.println(entry.getKey().getPrenom() + " : " + entry.getValue());
        }

        JpaUtil.fermerFabriquePersistance();

    }

    private static void TestAuthentification() {
        System.out.println(SEPARATOR);
        System.out.println(ANSI_RED + "Authetication of an employee with correct info" + ANSI_RESET);
        Object[] result = UserServices.Authenticate("john.smith@predictif.fr", "password1");
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
        System.out.println(ANSI_RED + "Authetication of an employee with incorrect info" + ANSI_RESET);
        result = UserServices.Authenticate("john.smith@predictif.fr", "password2");
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
        System.out.println(ANSI_RED + "Authetication of an client with correct info" + ANSI_RESET);
        result = UserServices.Authenticate("samsam@qdf.fr", "123456");
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
        System.out.println(ANSI_RED + "Authetication of an client with incorrect info" + ANSI_RESET);
        result = UserServices.Authenticate("samsam@qdf.fr", "1234567");

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