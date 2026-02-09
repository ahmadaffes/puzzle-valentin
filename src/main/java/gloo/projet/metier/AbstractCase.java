package gloo.projet.metier;

public abstract class AbstractCase {
    // Le lien vers l'élément de bloc posé sur cette case
    protected BlocElementaire blocElementaire;
    
    public Bloc getBloc() {
        if (blocElementaire != null) {
            return blocElementaire.getBloc();
        }
        return null;
    }

    // Cette méthode est appelée par BlocElementaire.setCase()
    public void setBlocElementaire(BlocElementaire blocElementaire) {
        this.blocElementaire = blocElementaire;
    }
    public BlocElementaire getBlocElementaire() {
        return this.blocElementaire;
    }
    
    public abstract boolean accepter(BlocElementaire b);
}