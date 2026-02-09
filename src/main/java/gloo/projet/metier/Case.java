package gloo.projet.metier;

public class Case extends AbstractCase {
    @Override
    public boolean accepter(BlocElementaire b) {
        // Accepte si vide OU si c'est déjà le même bloc (glissement sur soi-même)
        return this.blocElementaire == null || this.blocElementaire.getBloc() == b.getBloc();
    }
}