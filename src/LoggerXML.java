import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class LoggerXML {    
    private static String riceviComeStringa(int p) throws IOException {
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
    
    private static void inviaComeStringa(int p, LogGestioneMagazzino l) throws IOException {
        try (Socket sock = new Socket(l.getIpServer(), p);
            DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        ) {
            dos.writeUTF(l.toString());
        } 
    }
    
    public static void logAvvio(int p, String ipc, String ips) throws IOException {
        LogGestioneMagazzino l = new LogGestioneMagazzino("Avvio", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logTermine(int p, String ipc, String ips) throws IOException {
        LogGestioneMagazzino l = new LogGestioneMagazzino("Avvio", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logPressionePulsante(int p, String ipc, String ips, String pulsante) throws IOException {
        LogGestioneMagazzino l = new LogGestioneMagazzino("Pressione", ipc, ips, pulsante);
        inviaComeStringa(p, l);
    }
    
    public static void logRicerca(int p, String ipc, String ips) throws IOException {
        LogGestioneMagazzino l = new LogGestioneMagazzino("Ricerca", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logListaMaterialiVisuale(int p, String ipc, String ips) throws IOException {
        LogGestioneMagazzino l = new LogGestioneMagazzino("ListaMaterialiVisuale", ipc, ips);
        inviaComeStringa(p, l);
    }
    
    public static void logModificaMonitor(int p, String ipc, String ips) throws IOException {
        LogGestioneMagazzino l = new LogGestioneMagazzino("ModificaMonitor", ipc, ips);
        inviaComeStringa(p, l);
    }
}
