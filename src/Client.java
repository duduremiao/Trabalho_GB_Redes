import com.sun.nio.sctp.*;
import java.io.*;
import java.net.*;
import java.nio.*;


public class Client {
    static InetSocketAddress serverAddr;
    static int SERVER_PORT = 3456;

    static String msg;
    static SctpChannel channel;
    static BufferedReader br;


    public static void main(String[] args) throws IOException {
        //Criação um endereço de socket definindo o hostname e número da porta.
        InetAddress host = InetAddress.getByName("localhost");
        serverAddr = new InetSocketAddress(host, SERVER_PORT);
        //buffer para leitura das mensagens
        br = new BufferedReader(new InputStreamReader(System.in));
        //abertura do canal SCTP
        channel = SctpChannel.open(serverAddr, 10, 10);



        MessageInfo messageInfo;

        do {

            final ByteBuffer byteBuffer = ByteBuffer.allocate(65536);


            System.out.println("Digite a mensagem: ");
            //leitura da mensagem digitada
            msg = br.readLine();

            //cria um array com a mesagem digitada convertida em bytes
            byte[] message = msg.getBytes();

            //criação de uma instância de MessageInfo
            messageInfo = MessageInfo.createOutgoing(null, 0);

            //tranferencia dos bytes do array para o buffer
            byteBuffer.put(message);

            byteBuffer.flip();

            //envio da mensagem para o Server
            channel.send(byteBuffer, messageInfo);



        } while (!msg.equals("close"));

        //fechamento do canal
        channel.close();
    }


}