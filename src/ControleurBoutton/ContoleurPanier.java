package ControleurBoutton;

import App.AppLibrairie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ContoleurPanier implements EventHandler<ActionEvent> {
    private AppLibrairie app;

    public ContoleurPanier(AppLibrairie appLibrairie){
        this.app = appLibrairie;
    }


    @Override
    public void handle(ActionEvent e){
        this.app.fenetrePanier();
    }
    
}