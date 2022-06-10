import com.sun.nio.sctp.*;
import java.net.*;
import java.nio.*;

public class Server {
    static InetSocketAddress serverAddress;
    static int SERVER_PORT = 3456;
    static ByteBuffer buf;
    static SctpChannel sctpChannel;
    static String comando;
    static SctpServerChannel serverChannel;


    public static void main(String[] args) throws Exception {
        //abertura do servidor SCTP
        serverChannel = SctpServerChannel.open();
        //Criação um endereço de socket definindo o número da porta.
        serverAddress = new InetSocketAddress(SERVER_PORT);
        //Vincula o socket do canal a um endereço e configura o socket para escutar associações
        serverChannel.bind(serverAddress);
        comando = "";

        System.out.println("Servidor aberto.");
        //verifica se o server recebeu a associação de um cliente
        if ((sctpChannel = serverChannel.accept()) != null) {
            System.out.println("Conexão do servidor recebida.");

            while (!comando.equalsIgnoreCase("close")){
                buf = ByteBuffer.allocateDirect(65536);

                //Recebe a mensagem do cliente
                MessageInfo messageInfo =
                        sctpChannel.receive(buf, null, null);

                int len = messageInfo.bytes();

                // operações de get, put
                buf.flip();

                byte[] data = new byte[len];

                //transferencia de bytes para o array
                buf.get(data);

                // conversão bytes > String
                comando = new String(data, 0, data.length);

                System.out.println("\n>>>Comando recebido de "+messageInfo.address()+": " + comando);

                //execução comando
                ComandosTerminal.rodarComando(comando);

                //limpeza buffer
                buf.clear();
            }

            System.out.println("Conexão do servidor encerrada.");

            //fechamento do canal
            serverChannel.close();
        }
    }

}