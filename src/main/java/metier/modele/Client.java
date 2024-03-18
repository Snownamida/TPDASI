/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author jsun
 */
@Entity
public class Client {

    @Id
    private Long id;
    private String nom;
    private String prenom;
    private String mail;
    private String motDePasse;
    private String adressePostale;
    private Double latitude;
    private Double longitude;

    @Override
    public String toString() {
        return String.format("Client:\nid=%s;nom=%s;prenom=%s;mail=%s;motDePasse=%s;adressePostale=%s;latitude=%s;longitude=%s;",
                id, nom, prenom, mail, motDePasse, adressePostale, latitude, longitude);
    }

    public Client(String nom, String prenom, String mail, String adressePostale) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.adressePostale = adressePostale;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setAdressePostale(String adressePostale) {
        this.adressePostale = adressePostale;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getMail() {
        return mail;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getAdressePostale() {
        return adressePostale;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

}
