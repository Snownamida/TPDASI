/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import metier.modele.Employee;

/**
 *
 * @author snownamida
 */
public class ClientDao {

    public static void create(Employee client) {
        JpaUtil.obtenirContextePersistance().persist(client);
    }

    public static Employee findByEmail(String mail) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employee> query = em.createQuery("SELECT c FROM Client c WHERE c.mail = :mail", Employee.class);
        query.setParameter("mail", mail);
        List<Employee> results = query.getResultList();
        if (!results.isEmpty()) {
            // 返回第一个找到的客户（如果有）
            return results.get(0);
        } else {
            // 没有找到与该电子邮件相对应的客户
            return null;
        }

    }

    public static Employee findById(Long id) {
        return JpaUtil.obtenirContextePersistance().find(Employee.class, id);
    }

    // Ce service renvoie toutes les entités Client triées par ordre alphabétique
    // (nom/prénom).
    public static List<Employee> getAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employee> query = em.createQuery("SELECT c FROM Client c ORDER BY c.nom, c.prenom", Employee.class);
        return query.getResultList();
    }

    public static void update(Employee client) {
        JpaUtil.obtenirContextePersistance().merge(client);
    }

}
