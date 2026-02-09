package gloo.projet.metier;

import java.util.HashMap;
import java.util.Map;

public class Plateau {
    private int nbLignes;
    private int nbColonnes;
    // Utilisation de HashMap pour retrouver les cases par coordonnées
    private Map<Position, AbstractCase> kases = new HashMap<>();
    private Map<String, Bloc> blocs = new HashMap<>();

    public Plateau(int nbLignes, int nbColonnes) {
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
    }

    public void ajouterCase(AbstractCase c, int l, int col) {
        kases.put(new Position(l, col), c);
    }

    public void ajouterBloc(Bloc b) {
        blocs.put(b.getNumero(), b);
    }

    public AbstractCase getCase(Position p) {
        return kases.get(p);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // En-tête des colonnes
        sb.append("   ");
        for(int j=0; j<nbColonnes; j++) sb.append(j + "  ");
        sb.append("\n");
        
        for (int i = 0; i < nbLignes; i++) {
            sb.append(i + " ");
            for (int j = 0; j < nbColonnes; j++) {
                AbstractCase c = kases.get(new Position(i, j));
                if (c instanceof Mur) {
                    sb.append(" # ");
                } else if (c.getBloc() != null) {
                    sb.append(" " + c.getBloc().getNumero() + " ");
                } else if (c instanceof Sortie) {
                	sb.append(" > ");
                } else {
                    sb.append(" . ");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}