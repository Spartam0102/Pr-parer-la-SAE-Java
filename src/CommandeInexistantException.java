public class CommandeInexistantException extends Exception{
    
    public CommandeInexistantException(){
        super("La commande que vous avez mis n'existe pas");
    }
}