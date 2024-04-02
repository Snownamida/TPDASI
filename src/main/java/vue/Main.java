/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.ClientDao;
import dao.EmployeeDao;
import dao.JpaUtil;
import metier.modele.Client;
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
    public static void main(String[] args) {

        JpaUtil.creerFabriquePersistance();
        EmployeeService.InitEmployee();
        Client client1 = new Client("Taider", "Samy", "sqgsg@qdf.sf", "123456", "Lyon", "1234567890",
                "2003-08-27");
        ClientService.inscrireClient(client1);
        // ClientService.inscrireClient(client2);

        System.out.println("client dans memoire : ");
        System.out.println(client1);
        // System.out.println(client2);

        System.out.println("client dans BD : ");
        System.out.println(ClientService.consulterListeClients());

        System.out.println("employes dans BD : ");
        System.out.println(EmployeeService.consulterListeEmployes());

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

        JpaUtil.fermerFabriquePersistance();
    }

}
