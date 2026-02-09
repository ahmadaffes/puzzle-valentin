package gloo.projet.metier;

public class Mur extends AbstractCase {
    @Override
    public boolean accepter(BlocElementaire b) {
        return false; // Un mur bloque tout
    }
}