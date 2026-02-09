package gloo.projet.metier;

import java.util.ArrayList;
import java.util.List;

public class Bloc {
    private String numero;
    private Plateau plateau;
    private List<BlocElementaire> elements = new ArrayList<>();

    public Bloc(Plateau plateau, String numero) {
        this.plateau = plateau;
        this.numero = numero;
    }

    public void addElement(BlocElementaire element) {
        this.elements.add(element);
    }
    
    public String getNumero() { return numero; }

    public void deplacer(Direction d) {
        boolean possible = true;
        List<AbstractCase> casesActuelles = new ArrayList<>();
        List<AbstractCase> casesCibles = new ArrayList<>();

        // 1. VÉRIFICATION
        for (BlocElementaire be : elements) {
            AbstractCase caseActuelle = be.getCase();
            // Retrouver la position via le plateau (méthode de secours car Case n'a pas Pos)
            Position posActuelle = trouverPosition(caseActuelle);
            
            if (posActuelle == null) continue;
            
            Position posVoisine = posActuelle.voisine(d);
            AbstractCase caseVoisine = plateau.getCase(posVoisine);

            if (caseVoisine == null || !caseVoisine.accepter(be)) {
                possible = false;
                break;
            }
            casesActuelles.add(caseActuelle);
            casesCibles.add(caseVoisine);
        }

        // 2. EXÉCUTION
        if (possible && !casesActuelles.isEmpty()) {
            System.out.println("-> Déplacement du bloc " + numero + " : SUCCÈS");
            
            // Libérer les anciennes cases
            for (AbstractCase c : casesActuelles) {
                c.setBlocElementaire(null);
            }
            
            // Occuper les nouvelles cases
            for (int i = 0; i < elements.size(); i++) {
                BlocElementaire be = elements.get(i);
                AbstractCase nouvelleCase = casesCibles.get(i);
                be.setCase(nouvelleCase); // Refait le lien
            }
        } else {
            System.out.println("-> Déplacement du bloc " + numero + " : IMPOSSIBLE (Bloqué)");
        }
    }
    
    private Position trouverPosition(AbstractCase c) {
        // Recherche la position (i,j) correspondant à la case c
        for(int i=0; i<50; i++) { // Taille max arbitraire pour recherche
             for(int j=0; j<50; j++) {
                 Position p = new Position(i, j);
                 if (plateau.getCase(p) == c) return p;
             }
        }
        return null;
    }
}