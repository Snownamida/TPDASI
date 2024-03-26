/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import dao.JpaUtil;
import metier.modele.Employee;
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
        Employee client1 = new Employee("Hugo", "Victor", "vhugo@paris.fr", "Paris");
        Employee client2 = new Employee("Yourcenar", "Marguerite", "vhugo@paris.fr", "Tooloose");
        Employee client3 = new Employee("Zola", "Emile", "ezola@gmail.com", "Lyon");
        Employee client4 = new Employee("SUN", "Jixiang", "namida@gmail.com", "Nanjing");
        // Client client5 = new Client("Snow", "Namida", "namida@gmail.com", "Nanjing");

        JpaUtil.creerFabriquePersistance();

        ClientService.inscrireClient(client1);
        ClientService.inscrireClient(client2);
        ClientService.inscrireClient(client3);
        ClientService.inscrireClient(client4);
        // ClientService.inscrireClient(client5);

        ClientService.updatePassword(client1, "和平");
        ClientService.updatePassword(client2, "发展");
        ClientService.updatePassword(client3, "和谐");
        ClientService.updatePassword(client4, "看不见我");

        System.out.println("内存中的客户们 : ");
        System.out.println(client1);
        System.out.println(client2);
        System.out.println(client3);
        System.out.println(client4);
        // System.out.println(client5);

        Employee testClient = ClientService.authentifierClient("namida@gmail.com", "错误的密码");
        if (testClient != null) {
            System.out.println("认证成功 : " + testClient);
        } else {
            System.out.println("认证失败");
        }

        testClient = ClientService.authentifierClient("namida@gmail.com", "看不见我");
        if (testClient != null) {
            System.out.println("认证成功 : " + testClient);
        } else {
            System.out.println("认证失败");
        }

        System.out.println("数据库中的客户们 : ");
        System.out.println(ClientService.consulterListeClients());

        JpaUtil.fermerFabriquePersistance();
        

    }

}
