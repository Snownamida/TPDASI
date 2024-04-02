/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import javax.persistence.Embeddable;

/**
 *
 * @author snownamida
 */

@Embeddable
public class AstralProfile {

    private String couleurPorteBonheur;
    private String animalTotem;
    private String signeAstroChinois;
    private String signeDuZodiaque;

    @Override
    public String toString() {
        return "AstralProfile [animalTotem=" + animalTotem + ", couleurPorteBonheur=" + couleurPorteBonheur
                + ", signeAstroChinois=" + signeAstroChinois + ", signeDuZodiaque=" + signeDuZodiaque + "]";
    }

    protected AstralProfile() {
    }

    public AstralProfile(String couleurPorteBonheur, String animalTotem, String signeAstroChinois,
            String signeDuZodiaque) {
        this.couleurPorteBonheur = couleurPorteBonheur;
        this.animalTotem = animalTotem;
        this.signeAstroChinois = signeAstroChinois;
        this.signeDuZodiaque = signeDuZodiaque;
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

}
