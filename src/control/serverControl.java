/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author admin
 */
public class serverControl {
    private int port=8888;
    private String host="localhost";
    private ServerSocket myServer;
    private Socket clientSocket;
    private Dao dao;

    public serverControl() {
        open();
        dao= new Dao();
        while (true) {            
            listenning();
        }
    }
    
    public void open(){
        try {
            myServer= new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(serverControl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void listenning(){
        Object o= receiveData();
        if(o instanceof String){
            String name=(String)o;
            List<User> list= dao.search(name);
            sendData(list);
        }
        else{
            User u= (User)o;
            boolean check=dao.addUser(u);
            sendData(check);
        }
    }
    
    public Object receiveData(){
        Object o= new Object();
        try {
            // server chap nhan socket tu client
            clientSocket= myServer.accept();
            ObjectInputStream ois= new ObjectInputStream(clientSocket.getInputStream());
            o=ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(serverControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(serverControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
    
    public void sendData(Object o){
        try {
            ObjectOutputStream oos= new ObjectOutputStream(clientSocket.getOutputStream());
            oos.writeObject(o);
        } catch (IOException ex) {
            Logger.getLogger(serverControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
