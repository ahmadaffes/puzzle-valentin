package gloo.projet;

import gloo.projet.controle.Controleur;
import gloo.projet.metier.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainGUI {

    public static Controleur initialiserPartie() {
        int taille = 3; 
        Plateau plateau = new Plateau(taille, taille);

        // 1. Créer le plateau vide
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                plateau.ajouterCase(new Case(), i, j);
            }
        }

        // 2. Créer les 9 morceaux d'image (0 à 8)
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ids.add(String.valueOf(i));
        }
        // Mélanger
        Collections.shuffle(ids);

        // 3. Remplir TOUTES les cases (pas de trou)
        int compteur = 0;
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                String idBloc = ids.get(compteur++);
                ajouterBlocSimple(plateau, idBloc, i, j);
            }
        }
        
        return new Controleur(plateau);
    }

    private static void ajouterBlocSimple(Plateau p, String id, int lig, int col) {
        Bloc b = new Bloc(p, id);
        p.ajouterBloc(b);
        BlocElementaire be = new BlocElementaire(b);
        b.addElement(be);
        
        Position pos = new Position(lig, col);
        AbstractCase laCase = p.getCase(pos);
        if (laCase != null) {
            be.setCase(laCase);
        }
    }
}