package gloo.projet;

import gloo.projet.controle.Controleur;
import gloo.projet.metier.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonSiteControlleur {

    private Controleur controleur = MainGUI.initialiserPartie();
    private Position selectionEnCours = null;

    // --- CONFIGURATION ---
    // Mets ici le nom de ta nouvelle image romantique !
    private final String NOM_IMAGE = "/Sv2.jpg"; 
    private final int TAILLE_CASE = 100; 

    @GetMapping("/")
    public String afficherJeu() {
        boolean victoire = estGagne();
        StringBuilder html = new StringBuilder();

        html.append("<html><head>");
        // Animation automatique
        html.append("<meta name=\"view-transition\" content=\"same-origin\" />");
        // Ajout d'une jolie police Google Fonts (Dancing Script)
        html.append("<link href='https://fonts.googleapis.com/css2?family=Dancing+Script:wght@700&display=swap' rel='stylesheet'>");
        
        html.append("<style>");
        // Thème romantique : fond dégradé rose
        html.append("body { font-family: 'Helvetica', sans-serif; text-align: center; background: linear-gradient(to bottom right, #ffdde1, #ee9ca7); color: #5a2a2a; margin: 0; padding: 20px; }");
        html.append("h1 { font-family: 'Dancing Script', cursive; font-size: 3em; color: #d32f2f; text-shadow: 1px 1px 2px white; }");
        
        // Le plateau avec une bordure dorée/rose
        html.append(".plateau { display: inline-block; border: 8px solid #e91e63; border-radius: 10px; background: #fff0f5; padding: 5px; box-shadow: 0 10px 20px rgba(0,0,0,0.2); }");
        html.append(".ligne { display: flex; }");
        
        // Les cases
        html.append(".case { width: " + TAILLE_CASE + "px; height: " + TAILLE_CASE + "px; border: 1px solid #ffb6c1; display: block; box-sizing: border-box; cursor: pointer; }");
        // On force la largeur ET la hauteur (300px 300px)
        html.append(".bloc { background-image: url('" + NOM_IMAGE + "'); background-size: " + (TAILLE_CASE * 3) + "px " + (TAILLE_CASE * 3) + "px; transition: all 0.3s; border-radius: 4px; }");
        
        // Nouvelle sélection : Rose vif brillant
        html.append(".selection { border: 4px solid #ff1493 !important; z-index: 100; box-shadow: 0 0 15px #ff1493; transform: scale(1.05); }");
        
        // Le message de victoire
        html.append(".message-victoire { background-color: rgba(255, 255, 255, 0.9); border: 3px solid #d32f2f; padding: 20px; border-radius: 15px; margin-top: 20px; animation: pop 0.5s ease-out; }");
        html.append(".message-victoire h2 { font-family: 'Dancing Script', cursive; color: #d32f2f; font-size: 2.5em; margin: 0; }");
        html.append("@keyframes pop { from { transform: scale(0); } to { transform: scale(1); } }");

        html.append("</style></head><body>");

        html.append("<h1>❤️ Notre Puzzle d'Amour ❤️</h1>");

        html.append("<div class='plateau'>");
        for (int i = 0; i < 3; i++) {
            html.append("<div class='ligne'>");
            for (int j = 0; j < 3; j++) {
                AbstractCase c = controleur.getCase(i, j);
                int idMorceau = Integer.parseInt(c.getBloc().getNumero());
                
                // Calculs position image
                int ligOrigine = idMorceau / 3;
                int colOrigine = idMorceau % 3;
                int posX = -1 * colOrigine * TAILLE_CASE;
                int posY = -1 * ligOrigine * TAILLE_CASE;
                
                String styleImage = "background-position: " + posX + "px " + posY + "px;";
                // Identifiant pour l'animation view-transition
                String viewTransition = "view-transition-name: piece-" + idMorceau + ";";

                String classe = "case bloc";
                // Si gagné, on empêche de cliquer en retirant le lien
                String lien = victoire ? "#" : "/clic?l=" + i + "&c=" + j;

                if (!victoire && estSelectionne(i, j)) {
                    classe += " selection";
                }

                html.append("<a href='" + lien + "' class='" + classe + "' style='" + styleImage + viewTransition + "'></a>");
            }
            html.append("</div>");
        }
        html.append("</div>");

        // --- AFFICHAGE DU MESSAGE SI VICTOIRE ---
        if (victoire) {
            html.append("<div class='message-victoire'>");
            html.append("<h2>✨ Happy Valentine's Day! ✨</h2>");
            html.append("<p>You've put the pieces of my heart back together. I love you! ❤️</p>");
            html.append("</div>");
        } else {
            html.append("<p style='color: #d32f2f;'>Click on two squares to swap them and reconstruct the image...</p>");
        }

        html.append("<br><a href='/reset' style='color: #d32f2f; text-decoration: underline;'>Recommencer</a>");
        html.append("</body></html>");

        return html.toString();
    }

    @GetMapping("/clic")
    public String gererClic(@RequestParam int l, @RequestParam int c) {
        // Si c'est déjà gagné, on ne fait plus rien
        if (estGagne()) return afficherJeu();

        Position clicActuel = new Position(l, c);

        if (selectionEnCours == null) {
            selectionEnCours = clicActuel;
        } else {
            if (l != selectionEnCours.getLigne() || c != selectionEnCours.getColonne()) {
                echangerCases(selectionEnCours, clicActuel);
            }
            selectionEnCours = null;
        }
        return afficherJeu();
    }

    @GetMapping("/reset")
    public String reset() {
        controleur = MainGUI.initialiserPartie();
        selectionEnCours = null;
        return afficherJeu();
    }

    // --- LOGIQUE MÉTIER ---

    // Vérifie si l'image est correctement reconstituée
    private boolean estGagne() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // Le bloc attendu à la position (i, j) est celui qui a l'ID "i*3 + j"
                // Ex: à la ligne 1, col 1, on attend le bloc (1*3 + 1) = "4"
                String idAttendu = String.valueOf(i * 3 + j);
                
                AbstractCase c = controleur.getCase(i, j);
                // Si la case est vide OU si le numéro ne correspond pas, ce n'est pas gagné
                if (c == null || c.getBloc() == null || !c.getBloc().getNumero().equals(idAttendu)) {
                    return false;
                }
            }
        }
        // Si on a parcouru toute la grille sans erreur, c'est gagné !
        return true;
    }

    private boolean estSelectionne(int l, int c) {
        return selectionEnCours != null && selectionEnCours.getLigne() == l && selectionEnCours.getColonne() == c;
    }

    // (Inclut la correction du bug des blocs fantômes)
    private void echangerCases(Position p1, Position p2) {
        AbstractCase c1 = controleur.getCase(p1.getLigne(), p1.getColonne());
        AbstractCase c2 = controleur.getCase(p2.getLigne(), p2.getColonne());

        BlocElementaire b1 = getBlocElementaire(c1);
        BlocElementaire b2 = getBlocElementaire(c2);

        c1.setBlocElementaire(null);
        c2.setBlocElementaire(null);

        if (b1 != null) b1.setCase(c2);
        if (b2 != null) b2.setCase(c1);
    }
    
    // Nécessite l'ajout du getter dans AbstractCase.java (voir réponses précédentes)
    private BlocElementaire getBlocElementaire(AbstractCase c) {
       return c.getBlocElementaire(); 
    }
}