public class VendeurInexistantException extends Exception{
    
    public VendeurInexistantException(){
        super("Le vendeur que vous avez mis n'existe pas");
    }
}