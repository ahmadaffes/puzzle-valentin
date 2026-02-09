package gloo.projet.metier;

public class BlocElementaire {
    private Bloc bloc;
    private AbstractCase maCase; 
    
    public BlocElementaire(Bloc bloc) {
        this.bloc = bloc;
    }

    public Bloc getBloc() {
        return bloc;
    }

    public void setCase(AbstractCase nouvelleCase) {
        this.maCase = nouvelleCase;
        if (maCase != null) {
            // On dit à la case "Je suis sur toi"
            maCase.setBlocElementaire(this);
        }
    }
    
    public AbstractCase getCase() {
        return maCase;
    }
}