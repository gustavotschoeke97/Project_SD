/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mesa_voto;


// TCPServer2.java: Multithreaded server
import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author Admin
 */
public class Mesa_voto {

    public static void main(String args[]){
        int numero=0;
        
        try{
            int serverPort = 6001;
            System.out.println("A Escuta no Porto 6000");
            ServerSocket listenSocket = new ServerSocket(serverPort);
            System.out.println("LISTEN SOCKET = "+listenSocket);
            while(true) {
                Socket clientSocket = listenSocket.accept(); // BLOQUEANTE
                System.out.println("CLIENT_SOCKET (created at accept()) = "+ clientSocket);
                numero ++;
                new Connection(clientSocket, numero);
            }
        }catch(IOException e)
        {System.out.println("Listen:" + e.getMessage());}
    }
}
//= Thread para tratar de cada canal de comunicação com um cliente
class Connection extends Thread {
    PrintWriter outToClient;
    BufferedReader inFromClient = null;
    Socket clientSocket;
    int thread_number;
    
    public Connection (Socket aClientSocket, int numero) {
        thread_number = numero;
        try{
            clientSocket = aClientSocket;
             // create streams for writing to and reading from the socket
            inFromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            outToClient = new PrintWriter(clientSocket.getOutputStream());
            this.start();
        }catch(IOException e){System.out.println("Connection:" + e.getMessage());}
        

        try{
        //=============================
          // create a thread for reading from the keyboard and writing to the server
          new Thread() {
            public void run() {
              Scanner keyboardScanner = new Scanner(System.in);
              while(!clientSocket.isClosed()) {
                String readKeyboard = keyboardScanner.nextLine();
                outToClient.println(readKeyboard);
                outToClient.flush();
              }
            }
          }.start();

          // the main thread loops reading from the server and writing to System.out
          String messageFromServer;
          while((messageFromServer = inFromClient.readLine()) != null)
            System.out.println(messageFromServer);
        } catch (IOException e) {
          if(inFromClient == null)
            System.out.println("\nErro no reader!");
          System.out.println(e.getMessage());
        } finally {
          try { inFromClient.close(); } catch (Exception e) {}
        }
    }
} 

