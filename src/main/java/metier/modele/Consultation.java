/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author snownamida
 */
@Entity
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date date;
    private int duree;
    private String commentaire;
    @ManyToOne
    private Medium medium;
    @ManyToOne
    private Client client;
    @ManyToOne
    private Employee employee;

    protected Consultation() {
    }

    public Consultation(Date date, Medium medium, Client client, Employee employee, String commentaire) {
        this.date = date;
        this.medium = medium;
        this.client = client;
        this.employee = employee;
        this.commentaire = commentaire;
    }

    @Override
    public String toString() {
        return "Consultation ["
                + "\nclient=" + client
                + "\nemployee=" + employee
                + "\nmedium=" + medium
                + "\ncommentaire=" + commentaire
                + ", date=" + date
                + ", duree=" + duree + "]";
    }

    public Medium getMedium() {
        return medium;
    }

    public void setMedium(Medium medium) {
        this.medium = medium;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}
