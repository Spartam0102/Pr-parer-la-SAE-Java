package IHM.Controleur;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

public class ControleurCompteur implements EventHandler<ActionEvent> {
    
    private Label labelCompteur;
    private String operation; 
    private int valeurMin;
    private int valeurMax;
    
    public ControleurCompteur(Label labelCompteur, String operation, int valeurMin, int valeurMax) {
        this.labelCompteur = labelCompteur;
        this.operation = operation;
        this.valeurMin = valeurMin;
        this.valeurMax = valeurMax;
    }
    
    @Override
    public void handle(ActionEvent event) {
        int valeurActuelle = Integer.parseInt(labelCompteur.getText());
        
        if ("plus".equals(operation)) {
            if (valeurActuelle < valeurMax) {
                labelCompteur.setText(String.valueOf(valeurActuelle + 1));
            }
        } else if ("moins".equals(operation)) {
            if (valeurActuelle > valeurMin) {
                labelCompteur.setText(String.valueOf(valeurActuelle - 1));
            }
        }
    }
}