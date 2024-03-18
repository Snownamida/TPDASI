/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import metier.modele.Client;

/**
 *
 * @author snownamida
 */
public class ClientDao {

    public static void create(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }

}
