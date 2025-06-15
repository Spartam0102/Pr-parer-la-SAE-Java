package Exception;

import Java.*;

public class LivreEnRuptureDeStockException extends Exception {

    public LivreEnRuptureDeStockException(Livre livre) {
        super("Le livre " + livre + " est en rupture de stock");
    }
}