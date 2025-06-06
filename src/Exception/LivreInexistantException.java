package Exception; 

public class LivreInexistantException extends Exception{
    
    public LivreInexistantException(){
        super("Le livre que vous avez mis n'existe pas");
    }
}