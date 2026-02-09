package gloo.projet.metier;

import java.util.Objects;

public class Position {
    private int ligne;
    private int colonne;

    public Position(int ligne, int colonne) {
        this.ligne = ligne;
        this.colonne = colonne;
    }

    public int getLigne() { return ligne; }
    public int getColonne() { return colonne; }

    public Position voisine(Direction d) {
        return new Position(ligne + d.getdLigne(), colonne + d.getdColonne());
    }

    @Override
    public int hashCode() {
        return Objects.hash(colonne, ligne);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position other = (Position) obj;
        return colonne == other.colonne && ligne == other.ligne;
    }
    
    @Override
    public String toString() {
        return "(" + ligne + "," + colonne + ")";
    }
}