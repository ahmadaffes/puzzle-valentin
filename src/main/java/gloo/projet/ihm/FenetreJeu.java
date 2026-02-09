package gloo.projet.ihm;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import gloo.projet.controle.Controleur;
import gloo.projet.metier.Direction;

public class FenetreJeu extends JFrame implements KeyListener, MouseListener {
    private Controleur controleur;
    private PanneauPlateau panneau;

    public FenetreJeu(Controleur controleur) {
        this.controleur = controleur;
        
        // Configuration de la fenêtre
        this.setTitle("Sliding Block - Gloo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Ajout du panneau de dessin
        this.panneau = new PanneauPlateau(controleur);
        panneau.setPreferredSize(new Dimension(300, 300)); // 5 cases * 50px + marge
        this.add(panneau);
        
        // Abonnement aux événements
        this.addKeyListener(this);
        this.panneau.addMouseListener(this); // Pour sélectionner à la souris
        
        this.pack();
        this.setVisible(true);
    }

    // --- Gestion du Clavier (Déplacement) ---
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        
        switch (code) {
            case KeyEvent.VK_UP:    controleur.action(Direction.HAUT); break;
            case KeyEvent.VK_DOWN:  controleur.action(Direction.BAS); break;
            case KeyEvent.VK_LEFT:  controleur.action(Direction.GAUCHE); break;
            case KeyEvent.VK_RIGHT: controleur.action(Direction.DROITE); break;
        }
        
        // IMPORTANT : Forcer le redessin après une action
        panneau.repaint();
    }

    // --- Gestion de la Souris (Sélection) ---
    @Override
    public void mouseClicked(MouseEvent e) {
        // Convertir le pixel x,y en ligne,colonne
        int tailleCase = 50; // Doit être cohérent avec PanneauPlateau
        int col = e.getX() / tailleCase;
        int ligne = e.getY() / tailleCase;
        
        controleur.selection(ligne, col);
        panneau.repaint();
    }

    
    @Override public void keyTyped(KeyEvent e) {}
    @Override public void keyReleased(KeyEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}