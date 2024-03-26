/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package metier.modele;

import javax.persistence.Entity;

/**
 *
 * @author snownamida
 */
@Entity
public class Cartomancien extends Medium {

    protected Cartomancien() {
    }

    public Cartomancien(String denomination, String presentation, String genre, String profilePhotoPath) {
        super(denomination, presentation, genre, profilePhotoPath);
    }

}
