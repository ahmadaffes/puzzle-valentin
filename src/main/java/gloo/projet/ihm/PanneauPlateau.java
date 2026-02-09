package gloo.projet.ihm;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
import gloo.projet.controle.Controleur;
import gloo.projet.metier.AbstractCase;
import gloo.projet.metier.Mur;
import gloo.projet.metier.Sortie;

public class PanneauPlateau extends JPanel {
    private Controleur controleur;
    private int tailleCase = 50; // Taille en pixels d'une case

    public PanneauPlateau(Controleur controleur) {
        this.controleur = controleur;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g); // Nettoie l'écran

        int nbLignes = controleur.getNbLignes();
        int nbCols = controleur.getNbColonnes();

        for (int i = 0; i < nbLignes; i++) {
            for (int j = 0; j < nbCols; j++) {
                int x = j * tailleCase;
                int y = i * tailleCase;

                // 1. Demander au contrôleur ce qu'il y a ici
                AbstractCase c = controleur.getCase(i, j);

                // 2. Dessiner selon le type
                if (c instanceof Mur) {
                    // --- MUR ---
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(x, y, tailleCase, tailleCase);
                } 
                else if (c instanceof Sortie) {
                    // --- SORTIE ---
                    // Fond Vert clair
                    g.setColor(new Color(144, 238, 144)); 
                    g.fillRect(x, y, tailleCase, tailleCase);
                    
                    // Texte "EXIT" en noir
                    g.setColor(Color.BLACK);
                    g.drawString("EXIT", x + 10, y + 30);
                } 
                else if (c != null && c.getBloc() != null) {
                    // --- BLOC ---
                    if (controleur.isBlocSelectionne(c)) {
                        g.setColor(Color.RED); // Bloc sélectionné en ROUGE
                    } else if (c.getBloc().getNumero().equals("0")) {
                        g.setColor(Color.BLUE); // Bloc Principal en BLEU
                    } else {
                        g.setColor(Color.ORANGE); // Autres blocs en ORANGE
                    }

                    // Dessin du carré (légèrement plus petit pour voir les bords)
                    g.fillRect(x + 2, y + 2, tailleCase - 4, tailleCase - 4);
                    
                    // Numéro du bloc en blanc
                    g.setColor(Color.WHITE);
                    g.drawString(c.getBloc().getNumero(), x + 20, y + 30);
                } 
                else {
                    // --- CASE VIDE ---
                    g.setColor(Color.LIGHT_GRAY);
                    g.drawRect(x, y, tailleCase, tailleCase);
                }
            }
        }
    }
}