/*
Sorocaba, 03 de novembro de 2024
Matteo Cileneo Savan
UFSCar Sorocaba
*/
package servidor;

import java.net.Socket;
import java.io.*;




/**
 *
 * @author matte
 */
class ClientHandler extends Thread {

    private Socket clienteSocket;

    public ClientHandler(Socket socket) {
        this.clienteSocket = socket;
    }

    @Override
    public void run() {
        try {
            //abrindo um fluxo de bytes:
            InputStream  fluxo_entrada_bytes_socket = clienteSocket.getInputStream();
            //para receber texto (converte fluxo de bytes para fluxo de caracteres). Também conseguimos pegar a opção desejada:
            InputStreamReader fluxo_entrada_caracteres_socket = new InputStreamReader(fluxo_entrada_bytes_socket, "UTF-8");
            
            //pegando a opção desejada:
            int opcao_recebida = fluxo_entrada_caracteres_socket.read();
            System.out.println("Opção recebida do cliente: " + opcao_recebida);
            
            if (opcao_recebida==1){ //receber arquivo do cliente
                //para receber um arquivo de bytes com um buffer e acelerar o recebimento do arquivo
                    //adicionaremos buffer ao fluxo de entrada de bytes usando BufferedInputStream:
                BufferedInputStream fluxo_entrada_bytes_buffer = new BufferedInputStream(fluxo_entrada_bytes_socket);

                //para recebermos uma frase, ou seja, um conjunto de caracteres, precisamos
                    //adicionar um buffer ao fluxo de entrada de caracteres usando bufferedReader:
                BufferedReader fluxo_entrada_caracteres_buffer = new BufferedReader(fluxo_entrada_caracteres_socket);
                String formato_recebido;
                formato_recebido = fluxo_entrada_caracteres_buffer.readLine();
                Servidor.formato = formato_recebido;
                System.out.println("O formato recebido do cliente é: " + formato_recebido);
                
                //agora vamos começar a lidar com o recebimento e gravação do arquivo recebido:
                FileOutputStream arquivo_auxiliar = new FileOutputStream("C:\\Users\\matte\\Desktop\\auxiliar_servidor"+Servidor.formato); //cria o arquivo
                
                
                byte[] buffer = new byte[4096]; //usaremos um bloco de 4KB para ir armazenando os pacotes recebidos
                int bytes_lidos; //para guardar o retorno de read() pois se for -1 então chegamos no final do arquivo e caso contrario guarda a quantidade de bytes lidos

                while ((bytes_lidos = fluxo_entrada_bytes_buffer.read(buffer)) != -1) {
                    arquivo_auxiliar.write(buffer, 0, bytes_lidos); //gravando no arquivo
                }
                
                System.out.println("Arquivo " + Servidor.formato + " recebido com sucesso!");

                arquivo_auxiliar.close();
                fluxo_entrada_bytes_buffer.close();
            
            }
            else{//enviar arquivo ao cliente
                
                //criando o fluxo de bytes que usaremos:
                OutputStream fluxo_saida_bytes_socket = clienteSocket.getOutputStream();
                //criando o fluxo que converte texto em bytes que usaremos:
                OutputStreamWriter fluxo_saida_textobytes_socket = new OutputStreamWriter(fluxo_saida_bytes_socket, "UTF-8");

                
                //se não tem arquivo no servidor, então não transmitiremos:
                if(Servidor.formato.equals("")){
                    System.out.println("não existe arquivo no servidor");
                    fluxo_saida_textobytes_socket.write("n");
                    fluxo_saida_textobytes_socket.flush();
                }
                    //se tem arquivo no servidor, então vamos transmiti-lo:
                else{
                    //para ler o arquivo em bytes:
                    FileInputStream arquivo_auxiliar = new FileInputStream("C:\\Users\\matte\\Desktop\\auxiliar_servidor"+Servidor.formato);

                    //enviando o tipo do arquivo
                    fluxo_saida_textobytes_socket.write(Servidor.formato);
                    fluxo_saida_textobytes_socket.flush();
                    
                    //para enviar o arquivo:
                    byte[] buffer = new byte[4096]; //usaremos um bloco de 4KB para ir armazenando os pacotes recebidos
                    int bytes_lidos; //para guardar o retorno de read() pois se for -1 então chegamos no final do arquivo e caso contrario guarda a quantidade de bytes lidos
                    //lendo e enviando o arquivo:
                    while ((bytes_lidos = arquivo_auxiliar.read(buffer)) != -1) {
                        fluxo_saida_bytes_socket.write(buffer, 0, bytes_lidos); //enviando o que foi lido
                    }
                    
                    System.out.println("Arquivo enviado com sucesso");
                    fluxo_saida_textobytes_socket.close();
                    arquivo_auxiliar.close();
                    fluxo_saida_textobytes_socket.close();
                    fluxo_saida_bytes_socket.close();
                    }
                fluxo_entrada_bytes_socket.close();
                fluxo_entrada_caracteres_socket.close();
                }
            
  
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
