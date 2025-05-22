public class MagasinInexistantException extends Exception{
    
    public MagasinInexistantException(){
        super("Le magasin que vous avez mis n'existe pas");
    }
}