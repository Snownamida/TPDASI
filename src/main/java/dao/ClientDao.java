/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Client;

import java.util.List;

/**
 *
 * @author snownamida
 */
public class ClientDao {

    public static void create(Client client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }

    public static Client findByEmail(String mail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c WHERE c.mail = :mail", Client.class);
        query.setParameter("mail", mail);
        List<Client> results = query.getResultList();
        if (!results.isEmpty()) {
            // 返回第一个找到的客户（如果有）
            return results.get(0);
        } else {
            // 没有找到与该电子邮件相对应的客户
            return null;
        }
    }

    public static Client findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Client.class, id);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    // (nom/prénom).
    public static List<Client> getAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c ORDER BY c.nom, c.prenom", Client.class);
        return query.getResultList();
    }

    public static void update(Client client) {
        JpaUtil.obtenirContextePersistance().merge(client);
    }

}
