/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Server_RMI;

/**
 *
 * @author gustavo
 */
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import static java.time.Clock.system;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ClientTest implements Comunication_client {
     Server_RMI server1;
     
     
    public ClientTest()throws RemoteException{
        super();
    }
    
    public void reply_on_client()throws RemoteException{
        System.out.println("Cliente:ok");
    }
   
    
    public static void main(String args[]){
        try{
        
            Comunication_server h = (Comunication_server) LocateRegistry.getRegistry(7000).lookup("connection_RMI");
            ClientTest c = new ClientTest();
            //h.subscribe("stub",(Comunication_client)  c);
            String reply=h.Test_connection();
            System.out.println(reply);
            Integer i=1;
            System.out.println(h.returnList(i).toString());
            
        
        }catch(RemoteException re){
            re.getMessage();
        } catch (NotBoundException ex) {
            ex.getMessage();
        }
    }
}
