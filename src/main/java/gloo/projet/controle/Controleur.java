package gloo.projet.controle;

import gloo.projet.metier.*;

public class Controleur {
    private Plateau plateau;
    private Bloc blocSelectionne;

    public Controleur(Plateau plateau) {
        this.plateau = plateau;
    }

    // --- Méthodes existantes (Logique Jeu) ---
    
    public void selection(int ligne, int colonne) {
        Position p = new Position(ligne, colonne);
        AbstractCase c = plateau.getCase(p);
        if (c != null && c.getBloc() != null) {
            this.blocSelectionne = c.getBloc();
            System.out.println("Bloc " + blocSelectionne.getNumero() + " sélectionné.");
        }
    }

    public void action(Direction direction) {
        if (blocSelectionne != null) {
            blocSelectionne.deplacer(direction);
        }
    }

    // --- Nouvelles Méthodes pour l'IHM (Le Pattern MVC du TD) ---

    public int getNbLignes() { return 5; } // Ou plateau.getNbLignes() si vous l'avez ajouté
    public int getNbColonnes() { return 5; }

    // L'IHM demande : "Qu'est-ce qu'il y a à la case (i, j) ?"
    public AbstractCase getCase(int ligne, int colonne) {
        return plateau.getCase(new Position(ligne, colonne));
    }
    
    // Pour savoir si le bloc à cette position est celui sélectionné (pour le colorier différemment)
    public boolean isBlocSelectionne(AbstractCase c) {
        return blocSelectionne != null && c != null && c.getBloc() == blocSelectionne;
    }
}