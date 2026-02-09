package gloo.projet;

import gloo.projet.controle.Controleur;
import gloo.projet.metier.*;

public class TestSlidingBloc {

    public static void main(String[] args) {
        System.out.println("=== 1. CRÉATION DU PLATEAU ===");
        Plateau plateau = new Plateau(5, 5);
        
        // Remplir de cases vides
        for(int i=0; i<5; i++) {
            for(int j=0; j<5; j++) {
                plateau.ajouterCase(new Case(), i, j);
            }
        }

        // Ajouter un MUR en (2,1)
        plateau.ajouterCase(new Mur(), 2, 1);
        plateau.ajouterCase(new Sortie(), 0, 3);
        System.out.println("=== 2. AJOUT DU BLOC 1 (Cible: 2,2) ===");
        Bloc b1 = new Bloc(plateau, "1");
        BlocElementaire be1 = new BlocElementaire(b1);
        b1.addElement(be1);
        plateau.ajouterBloc(b1);
        
        // LIEN IMPORTANT : On récupère la case du plateau et on y met l'élément
        AbstractCase caseB1 = plateau.getCase(new Position(2, 2));
        if(caseB1 != null) {
            be1.setCase(caseB1); // Cela met à jour la case aussi !
        } else {
            System.err.println("ERREUR : Case (2,2) introuvable !");
        }

        System.out.println("=== 3. AJOUT DU BLOC 0 (Cible: 3,3) ===");
        Bloc b0 = new Bloc(plateau, "0");
        BlocElementaire be0 = new BlocElementaire(b0);
        b0.addElement(be0);
        plateau.ajouterBloc(b0);
        
        AbstractCase caseB0 = plateau.getCase(new Position(3, 3));
        if(caseB0 != null) {
            be0.setCase(caseB0);
        }

        // Affichage initial pour vérification
        System.out.println("\nÉTAT INITIAL DU PLATEAU :");
        System.out.println(plateau.toString());

        // --- DÉBUT DU JEU ---
        Controleur controleur = new Controleur(plateau);

        System.out.println("--- SCÉNARIO 1 : Sélection du bloc 1 en (2,2) ---");
        controleur.selection(2, 2);

        System.out.println("\n--- SCÉNARIO 2 : Déplacement GAUCHE (Attendu: Bloqué par Mur #) ---");
        controleur.action(Direction.GAUCHE);
        System.out.println(plateau.toString());

        System.out.println("\n--- SCÉNARIO 3 : Déplacement DROITE (Attendu: Succès) ---");
        controleur.action(Direction.DROITE);
        System.out.println(plateau.toString());

        System.out.println("\n--- SCÉNARIO 4 : Sélection Bloc 0 (3,3) et déplacement HAUT ---");
        controleur.selection(3, 3);
        controleur.action(Direction.HAUT);
        System.out.println(plateau.toString());
    }
}