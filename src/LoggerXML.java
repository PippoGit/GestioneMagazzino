import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggerXML {    
    public static String riceviComeStringa(int p) throws IOException { //(1)
        String xml;
        try ( 
            ServerSocket servsock = new ServerSocket(p); 
            Socket sock = servsock.accept();
            DataInputStream dis = new DataInputStream(sock.getInputStream());
        ) {
          xml = dis.readUTF();
        }
        return xml;
    }
    
    public static void inviaComeStringa(int p, LogGestioneMagazzino l) throws IOException { //(1)
        try (Socket sock = new Socket(l.getIpServer(), p);
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        ) {
            dos.writeUTF(l.toString());
        } 
    }
    
    public static void logAvvio(int p, String ipc, String ips) throws IOException { //(2)
        LogGestioneMagazzino l = new LogGestioneMagazzino("Avvio", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logTermine(int p, String ipc, String ips) throws IOException {//(2)
        LogGestioneMagazzino l = new LogGestioneMagazzino("Avvio", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logPressionePulsante(int p, String ipc, String ips, String pulsante) throws IOException {//(2)
        LogGestioneMagazzino l = new LogGestioneMagazzino("Pressione", ipc, ips, pulsante);
        inviaComeStringa(p, l);
    }
    
    public static void logRicerca(int p, String ipc, String ips, String info) throws IOException {//(2)
        LogGestioneMagazzino l = new LogGestioneMagazzino("Ricerca", ipc, ips, info);
        inviaComeStringa(p, l);
    }
    
    public static void logListaMaterialiVisuale(int p, String ipc, String ips) throws IOException {//(2)
        LogGestioneMagazzino l = new LogGestioneMagazzino("ListaMaterialiVisuale", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logModificaMonitor(int p, String ipc, String ips) throws IOException {//(2)
        LogGestioneMagazzino l = new LogGestioneMagazzino("ModificaMonitor", ipc, ips);
        inviaComeStringa(p, l);
    }
}

/*
Classe che realizza le operazioni di Logging (utilizza un socket per stabilire una connessione con il server).

1) I metodi riceviCOmeStringa ed InviaComeStringa sono abbastanza standard, inviano su un socket un stringa dell'oggetto
l di tipo LogGestioneMagazzino.

2) Metodi con i quali vengono effettuate specifiche azioni di log.

*/