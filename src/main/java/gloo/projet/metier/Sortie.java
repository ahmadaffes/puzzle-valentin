package gloo.projet.metier;

public class Sortie extends AbstractCase {

    @Override
    public boolean accepter(BlocElementaire b) {
        // La sortie n'accepte que le bloc principal (par convention le n° "0")
        if (b.getBloc().getNumero().equals("0")) {
            System.out.println("VICTOIRE ! Le bloc principal a atteint la sortie !");
            return true;
        }
        return false;
    }
    
    @Override
    public String toString() {
        return " > "; 
    }
}