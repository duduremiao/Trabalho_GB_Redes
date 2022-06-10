import java.io.*;
import java.util.ArrayList;

public class ComandosTerminal {

    public static void rodarComando(final String comando) {

        final ArrayList<String> comandos = new ArrayList<>();
        //criação do comando a ser executado
        comandos.add("/bin/bash");
        comandos.add("-c");
        comandos.add(comando);
        BufferedReader br = null;
        try {
            //criação de um ProcessBuilder com o comando a ser executado
            ProcessBuilder pb = new ProcessBuilder(comandos);

            //Criação de um Process utilizandoas informações do ProcessBuilder
            Process process = pb.start();

            //obtenção das informções de saída
            InputStream is = process.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);

            br = new BufferedReader(isr);

            String line;

            System.out.println("\n[RETORNO DO COMANDO]\n");

            //imprime o retorno do comando, caso exista
            while ((line = br.readLine()) != null) {
                System.out.println(line );
            }

            System.out.println("\n----------------------\n");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            finalizacaoSegura(br);
        }
    }
    //fechamento do buffer
    private static void finalizacaoSegura(final Closeable buffer) {
        try {
            if (buffer != null) {
                buffer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}