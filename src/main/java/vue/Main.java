/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import metier.modele.Client;
import metier.service.ClientService;

/**
 *
 * @author jsun
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Client client1 = new Client("Hugo", "Victor", "vhugo@paris.fr", "Paris");
        Client client2 = new Client("Yourcenar", "Marguerite", "vhugo@paris.fr", "Tooloose");
        Client client3 = new Client("Zola", "Emile", "ezola@gmail.com", "Lyon");

        JpaUtil.creerFabriquePersistance();

        ClientService.inscrireClient(client1);
        ClientService.inscrireClient(client2);
        ClientService.inscrireClient(client3);

        System.out.println(client1);
        System.out.println(client2);
        System.out.println(client3);

    }

}
