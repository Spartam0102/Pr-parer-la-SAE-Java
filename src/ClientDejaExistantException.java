public class ClientDejaExistantException extends Exception{
    
    public ClientDejaExistantException(Client client){
        super("Le client " + client + " existe déjà");
    }
}

