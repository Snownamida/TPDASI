/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 *
 * @author snownamida
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Medium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String denomination;
    private String presentation;
    private String genre;
    private String profilePhotoPath;

    protected Medium() {
    }

    public Medium(String denomination, String presentation, String genre, String profilePhotoPath) {
        this.denomination = denomination;
        this.presentation = presentation;
        this.genre = genre;
        this.profilePhotoPath = profilePhotoPath;
    }

    @Override
    public String toString() {
        return "Medium ["
                + "denomination=" + denomination
                + ", genre=" + genre
                + ", id=" + id
                + ", presentation=" + presentation
                + ", profilePhotoPath=" + profilePhotoPath + "]";
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

}
