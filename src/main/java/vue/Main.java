/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.util.List;

import dao.ClientDao;
import dao.EmployeeDao;
import dao.JpaUtil;
import metier.modele.Client;
import metier.modele.Consultation;
import metier.modele.Employee;
import metier.modele.Medium;
import metier.modele.Spirite;
import metier.service.AppointmentService;
import metier.service.AuthenticationService;
import metier.service.ClientService;
import metier.service.EmployeeService;

/**
 *
 * @author jsun
 */
public class Main {

    /**
     * @param args the command line arguments
     */

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

    public static void main(String[] args) {

        JpaUtil.creerFabriquePersistance();
        EmployeeService.InitEmployee();
        ClientService.inscrireClient("Taider", "Samy", "sqgsg@qdf.sf", "123456", "Lyon", "1234567890",
                "2003-08-27");
        // ClientService.inscrireClient(client2);

        System.out.println("client dans BD : ");
        List<Client> clients = ClientService.consulterListeClients();
        System.out.println(clients);

        System.out.println("employes dans BD : ");
        List<Employee> employes = EmployeeService.consulterListeEmployes();
        System.out.println(employes);

        System.out.println(ANSI_GREEN + "Trying to take an appointment... " + ANSI_RESET);
        AppointmentService.CreateAppointment(clients.get(0), employes.get(0), "2024-9-30");

        System.out.println("authetification... ");

        Object[] result = AuthenticationService.Authenticate("john.smith@predictif.fr", "password1");
        if (result != null) {
            System.out.println("authetification...OK ");
            if (result[1].equals("employee")) {
                System.out.println("Employee : " + EmployeeDao.findById((Long) result[0]));
            } else {
                System.out.println("Client : " + ClientDao.findById((Long) result[0]));
            }
        } else {
            System.out.println("authetification...KO ");
        }




        List<Consultation> consultationList = client1.getConsultations();
        System.out.println("--------------------------------------------------");
        System.out.println("previous consultations for client : " + clients.get(0).getNom() + " " + clients.get(0).getPrenom());

        for (Consultation consultation : consultationList) {
            // Print employee and comment
            System.out.println("Employee : ");
            System.out.println(consultation.getEmployee());
            System.out.println("Comment : ");
            System.out.printf(consultation.getCommentaire());
            System.out.println("\n");
        }
        System.out.println("--------------------------------------------------");
        JpaUtil.fermerFabriquePersistance();
        System.out.println("astral profile of this client : \n" + clients.get(0).getAstralProfile());
        System.out.println("--------------------------------------------------");



    }

    

}
