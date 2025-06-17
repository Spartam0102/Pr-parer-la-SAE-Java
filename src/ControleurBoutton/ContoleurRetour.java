package ControleurBoutton;

import App.AppLibrairie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ContoleurRetour implements EventHandler<ActionEvent> {
    private AppLibrairie app;

    public ContoleurRetour(AppLibrairie appLibrairie){
        this.app = appLibrairie;
    }


    @Override
    public void handle(ActionEvent e){
        this.app.fenetrePrecedente();
    }
    
}