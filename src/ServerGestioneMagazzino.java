import java.io.*;

public class ServerGestioneMagazzino {
    private static int port = 29794; //(1)
    private static final String FILE_NAME = "LogGestioneMagazzino"; //(1)
    
    private static void appendLog(String recv) { //(2)
        try (FileWriter writer = new FileWriter(FILE_NAME + ".txt", true)){
            writer.append(recv + "\n");
        } catch (IOException ex) {
            System.out.println("Errore nell'append del file di log.");
        }
    }
    
    public static void main(String[] args) {
        try {
            port = ConfigurazioneXML.getDelegationLink().getParams().getPort();
        } catch (Exception ex) {
            System.out.println("Errore nell'apertura del file di configurazione.");
        }
        while(true) {
            try {
                String recv = LoggerXML.riceviComeStringa(port);
                System.out.println(recv);
                if(ValidazioneXML.valida(recv, FILE_NAME)) //(3)
                    appendLog(recv);//(3)
            } catch (IOException ex) {
                System.out.println("Errore nell'invio su socket");
            }
        }
    }
    
}
/*
La classe si occupa di realizzare un semplice Server di logging. I compiti svolti dalla classe sono piuttosto semplici,
all'arrivo di un messaggio di log, se il messaggio risulta essere valido, viene inserito in append in un file testuale.

1) Il server opera sulla porta 29794 e suppone che il fie di log sia nominato "LogGestioneMagazzino.txt"
2) Il metodo effettua una semplice operazione di append al file di log. In caso di errore stampa a video informazioni dell'eccezione;
3) Viene richiamata la classe ValidazioneXML ed il metodo Valida per verificare se quanto ricevuto rispetta la grammatica del file
"LogGestioneMagazzino.xsd".

*/