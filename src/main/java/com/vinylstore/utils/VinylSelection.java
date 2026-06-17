package com.vinylstore.utils;

import com.vinylstore.models.Vinyl;

/**
 * Permet de passer un vinyle sélectionné entre les pages (catalogue → détails)
 * Sans avoir à modifier le load FXML
 * 
 * @author Toi-même (BTS SIO SLAM)
 */
public class VinylSelection {

    private static Vinyl vinyleSelectionne;

    public static void setVinyle(Vinyl v) {
        vinyleSelectionne = v;
    }

    public static Vinyl getVinyle() {
        return vinyleSelectionne;
    }
}
