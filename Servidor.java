/*
Sorocaba, 03 de novembro de 2024
Matteo Cileneo Savan
UFSCar Sorocaba
*/
package servidor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static String mensagemRecebida = "";
    public static String formato = "";
    public static void main(String[] args) throws IOException {
        try {
           

            ServerSocket servidorSocket = new ServerSocket(12000);
            System.out.println("Servidor ouvindo a porta 12000");

            while (true) {
                Socket clienteSocket = servidorSocket.accept();
                System.out.println("Cliente conectado: " + clienteSocket.getInetAddress().getHostAddress());
                
                //para o multithreading:
                Thread clientThread = new ClientHandler(clienteSocket);
                clientThread.start();
            }
        }catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
    
        }
    }
}