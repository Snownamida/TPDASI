/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dao.JpaUtil;
import metier.modele.Client;
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
        ClientService.inscrireClient("Taider", "Samy", "sqgsg@qdf.sf", "123456", "Lyon", "1234567890",
                "2003-08-27");
        // ClientService.inscrireClient(client2);

        System.out.println("数据库中的客户们 : ");
        System.out.println(ClientService.consulterListeClients());

        System.out.println(EmployeeService.consulterListeEmployes());

        JpaUtil.fermerFabriquePersistance();

    }

}
