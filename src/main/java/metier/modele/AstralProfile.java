/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author snownamida
 */

@Entity
public class AstralProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String couleurPorteBonheur;
    private String animalTotem;
    private String signeAstroChinois;
    private String signeDuZodiaque;
    private String codeRGBCouleur;

    public AstralProfile(String couleurPorteBonheur, String animalTotem, String signeAstroChinois,
            String signeDuZodiaque, String codeRGBCouleur) {
        this.couleurPorteBonheur = couleurPorteBonheur;
        this.animalTotem = animalTotem;
        this.signeAstroChinois = signeAstroChinois;
        this.signeDuZodiaque = signeDuZodiaque;
        this.codeRGBCouleur = codeRGBCouleur;
    }

    public String getCouleurPorteBonheur() {
        return couleurPorteBonheur;
    }

    public void setCouleurPorteBonheur(String couleurPorteBonheur) {
        this.couleurPorteBonheur = couleurPorteBonheur;
    }

    public String getAnimalTotem() {
        return animalTotem;
    }

    public void setAnimalTotem(String animalTotem) {
        this.animalTotem = animalTotem;
    }

    public String getSigneAstroChinois() {
        return signeAstroChinois;
    }

    public void setSigneAstroChinois(String signeAstroChinois) {
        this.signeAstroChinois = signeAstroChinois;
    }

    public String getSigneDuZodiaque() {
        return signeDuZodiaque;
    }

    public void setSigneDuZodiaque(String signeDuZodiaque) {
        this.signeDuZodiaque = signeDuZodiaque;
    }

    public String getCodeRGBCouleur() {
        return codeRGBCouleur;
    }

    public void setCodeRGBCouleur(String codeRGBCouleur) {
        this.codeRGBCouleur = codeRGBCouleur;
    }


}
