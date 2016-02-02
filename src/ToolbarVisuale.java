import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class ToolbarVisuale extends BorderPane {
    private final Label labelTitolo;
    private final Button save;
    private final ApplicationController appConBind;
    
    public String getTitoloTxt() {
        return labelTitolo.getText();
    }
    
    public void setTitoloTxt(String t) {
        labelTitolo.getStyleClass().remove("errore");  
        labelTitolo.getStyleClass().remove("messaggioOk");        
        labelTitolo.setText(t);
    }
    
    public void titoloConErrore(String t) { //(1)
        labelTitolo.getStyleClass().add("errore");
        labelTitolo.getStyleClass().remove("messaggioOk");                
        labelTitolo.setText(t);
    }
        
    public void titoloConEsitoOk(String txt) { //(2)
        labelTitolo.getStyleClass().add("messaggioOk");
        labelTitolo.getStyleClass().remove("errore");                
        labelTitolo.setText(txt);
    }
    
    private void configuraStileToolbar() { 
        save.setId("tbb-save");
        this.setId("toolBar");
        this.setMinHeight(55);        
        this.setMaxHeight(55);        
        save.setMinSize(60, 45);
        save.getStyleClass().add("toolBar-button");    
        labelTitolo.getStyleClass().add("titolo");
    }
    
    private void inviaLog() {
        try {
            ConfigurazioneXMLParametri params = ConfigurazioneXML.getDelegationLink().getParams();
            LoggerXML.logPressionePulsante(params.getPort(), params.getIpClient(), params.getIpServer(), "Salva");
        } catch (Exception ex) {
            appConBind.mostraErroreToolbar("Errore nell'invio log");
        }
    }
    
    private void posizionaComponenti() { //(3)
        HBox left = new HBox();
        HBox right = new HBox(8);
        this.setCenter(labelTitolo);
        right.setPadding(new Insets(0, 16, 0, 0));
        right.setAlignment(Pos.CENTER);
        right.getChildren().addAll(save);
        left.setMinWidth(60);
        left.setMaxWidth(60);
        this.setRight(right);
        this.setLeft(left);         
    }
    
    private void configuraPulsanti() { 
        save.setOnAction((ActionEvent e) -> { // (4)
            if(appConBind.getCurrent().isModificato()) {
                try {
                    appConBind.getCurrent().setModificato(false);
                    appConBind.getCurrent().salvaModificheDB();
                    appConBind.aggiornaPannelloPrincipale();
                    titoloConEsitoOk("Materiale salvato con successo.");
                } catch (SQLException ex) {
                    appConBind.mostraErroreToolbar("Errore nel collegamento al DB");
                }
            }
            inviaLog();
        });
    }
    
    public ToolbarVisuale(String titolo){
        super();

        this.save = new Button();
        this.labelTitolo = new Label(titolo);
        appConBind = ApplicationController.getDelegationLink();      
        
        configuraStileToolbar();
        posizionaComponenti();
        configuraPulsanti();
    }
}

/*
Commenti
Classe che si occupa di realizzare la Toolbar, quindi mostrare il titolo di navigazione e 
implementare un tasto per salvare le modifiche fatte all'oggetto current.

1) Metodo utilizzato per segnalare un errore all'utente. Viene cambiato il titolo nella Toolbar e viene 
mostrato in rosso.

2) Metodo utilizzato per segnalare che una certa azione ha avuto esito positivo (viene mostrato in verde)

3) Viene utilizzato il layout borderpane per realizzare una toolbar con pulsanti e titolo. Viene aggiunto
dello spazio sulla parte sinistra della barra per non creare squilibri nella disposizione degli elementi.
In questo modo il titolo rimane al centro della barra.

4) Metodo che implementa il salvataggio su database delle modifiche fatte a current
utilizzando l'oggetto ArchivioMagazzino.
*/