package trabalhoGA;
import java.io.IOException;
//import java.lang.ProcessBuilder.Redirect;
//import java.io.File;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;
public class PeerUDPteste {
    private DatagramSocket udpSocket;
    private InetAddress serverAddress1, serverAddress2;//implementacao de SocketAddress
    private int port;
    private Scanner scanner;
 
    public PeerUDPteste(int port) throws SocketException, IOException {//construtor p/ server
        this.port = port;
        this.udpSocket = new DatagramSocket(this.port);//Constructs a datagram socket and binds it to the specified port on the local host machine.
    }
    //abaixo utilizado para que tres computadores estejam envolvidos
    private PeerUDPteste(String destinationAddr, String destinationAddr2, int port) throws IOException {//construtor p/ cliente
        this.serverAddress1 = InetAddress.getByName(destinationAddr);//endereco IP
        this.serverAddress2 = InetAddress.getByName(destinationAddr2);
        this.port = port;
        udpSocket = new DatagramSocket(this.port);
        scanner = new Scanner(System.in);//para input de mensagem
    }
   

    public void listen() throws Exception { //daemon para receber mensagens
        System.out.println("-- Executando servidor UDP aqui (" + InetAddress.getLocalHost() + ") --");
        String msg;
        boolean loop = true;
        while (loop) {
            
            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);//para receber pacotes de tamanho buf.length
            
            // bloqueia para receber pacotes
            udpSocket.receive(packet);
            msg = new String(packet.getData()).trim();
            
            System.out.println(
                "Mensagem de " + packet.getAddress().getHostAddress() + ": " + msg);
            //File log = new File("log.txt");Somente usado no ProcessBuilder
            if((msg.toUpperCase()).equals("EXIT")){
                loop = false;}
            /*else {    //Nao funciona
                ProcessBuilder pb = new ProcessBuilder(msg);
                pb.redirectOutput(Redirect.appendTo(log));
                Process process = pb.start();
            }*/
        }//Process p = new ProcessBuilder(msg).start();
        //p.redirectOutput(File file) se nao tiver como mandar p/ rede, mas dai precisa ser cliente
    }
    
    private void start() throws IOException {//input dados e envia pacote
        System.out.println("-- Executando cliente UDP aqui (" + InetAddress.getLocalHost() + ") --");
        String in;
        boolean loop = true;
        while (loop) {
            in = scanner.nextLine();
            
            DatagramPacket p = new DatagramPacket(
                in.getBytes(), in.getBytes().length, serverAddress1, port);
            //recebe string como um array de bytes, seu tamanho, IP e porta de destino
            this.udpSocket.send(p);  
            p = new DatagramPacket(
                in.getBytes(), in.getBytes().length, serverAddress2, port);
            this.udpSocket.send(p);
            if((in.toUpperCase()).equals("EXIT")){
                loop = false;}                  
        }
        
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1){
            PeerUDPteste server = new PeerUDPteste(Integer.parseInt(args[0]));//server recebe porta
            server.listen();
        }
        else if (args.length == 3){
            PeerUDPteste client = new PeerUDPteste(args[0], args[1], Integer.parseInt(args[2]));//cliente recebe IP e porta
            client.start();
        }
        else{
            System.out.println("Use 1 ou 3 argumentos");
        }
    }
}
