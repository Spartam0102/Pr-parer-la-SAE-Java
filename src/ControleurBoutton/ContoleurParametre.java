package ControleurBoutton;

import App.AppLibrairie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class ContoleurParametre implements EventHandler<ActionEvent> {
    private AppLibrairie app;

    public ContoleurParametre(AppLibrairie appLibrairie){
        this.app = appLibrairie;
    }


    @Override
    public void handle(ActionEvent e){
        this.app.afficherParametre();
    }
    
}
