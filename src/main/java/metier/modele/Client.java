/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jsun
 */
@Entity
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    @Column(unique = true)
    private String mail;
    private String motDePasse;
    private String adressePostale;
    private Double latitude;
    private Double longitude;
    private String telephone;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Embedded
    private AstralProfile astralProfile;

    @OneToMany
    private List<Consultation> consultations;

    @Override
    public String toString() {
        return "Client [" + "nom=" + nom + ", prenom=" + prenom
                + ", adressePostale=" + adressePostale
                + ", birthdate=" + birthdate
                + ", id=" + id
                + ", latitude=" + latitude + ", longitude=" + longitude
                + ", mail=" + mail + ", motDePasse=" + motDePasse
                + ", telephone=" + telephone + ", astralProfile=" + astralProfile + "]";
    }

    protected Client() {
    }

    public Client(String nom, String prenom, String mail, String motDePasse, String adressePostale, String telephone,
            Date birthdate) {
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.motDePasse = motDePasse;
        this.adressePostale = adressePostale;
        this.telephone = telephone;
        this.birthdate = birthdate;

    }

    public List<Consultation> getConsultations() {
        return consultations;
    }

    public void setConsultations(List<Consultation> consultations) {
        this.consultations = consultations;
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

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone() {
        return telephone;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public AstralProfile getAstralProfile() {
        return astralProfile;
    }

    public void setAstralProfile(AstralProfile astralProfile) {
        this.astralProfile = astralProfile;
    }

}
