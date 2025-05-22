public class VendeurDejaExistantException extends Exception{
    
    public VendeurDejaExistantException(Vendeur vendeur){
        super("Le vendeur " + vendeur + " existe déjà");
    }
}
