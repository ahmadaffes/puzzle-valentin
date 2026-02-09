package gloo.projet.metier;


public enum Direction {
    HAUT(-1, 0),
    BAS(1, 0),
    DROITE(0, 1),
    GAUCHE(0, -1);

    private int dLigne;
    private int dColonne;

    private Direction(int dLigne, int dColonne) {
        this.dLigne = dLigne;
        this.dColonne = dColonne;
    }

    public int getdLigne() { return dLigne; }
    public int getdColonne() { return dColonne; }
}
