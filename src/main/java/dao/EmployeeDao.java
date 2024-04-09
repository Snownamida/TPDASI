/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import metier.modele.Employee;

/**
 *
 * @author snownamida
 */
public class EmployeeDao {

    public static void create(Employee employee) {
        JpaUtil.obtenirContextePersistance().persist(employee);
    }

    public static void update(Employee employee) {
        JpaUtil.obtenirContextePersistance().merge(employee);
    }

    public static Employee findByEmail(String email) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employee> query = em.createQuery("SELECT c FROM Employee c WHERE c.email = :email", Employee.class);
        query.setParameter("email", email);
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

    // getAll
    public static List<Employee> getAll() {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employee> query = em.createQuery("SELECT c FROM Employee c", Employee.class);
        return query.getResultList();
    }

    // select employee from Employee e where e.id not in (select c.employee.id from
    // Consultation c where c.date = :date)
    public static List<Employee> getAvailable(Date date) {
        EntityManager em = JpaUtil.obtenirContextePersistance();
        TypedQuery<Employee> query = em.createQuery(
                "SELECT e FROM Employee e WHERE e.id NOT IN (SELECT c.employee.id FROM Consultation c WHERE c.date = :date)",
                Employee.class);
        query.setParameter("date", date);
        List<Employee> results = query.getResultList();
        return results;
    }

}
