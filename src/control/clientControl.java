/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author admin
 */
// tcp thi cong port,host
// khac SOcket: clientSocket, serverSocket
// add, update, search delete
public class clientControl {
    private int port=8888;
    private Socket clientSocket, serverSocket;
    private String host="localhost";

    public clientControl() {
        open();
    }
    
    public void open(){
        try {
            clientSocket= new Socket();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void close(){
        try {
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sendName(String name){
        try {
            // lay dia chi Server can gui
            serverSocket= new Socket(host, port);
            ObjectOutputStream oos= new ObjectOutputStream(serverSocket.getOutputStream());
            oos.writeObject(name);
            oos.flush();
        } catch (IOException ex) {
            Logger.getLogger(clientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void sendUser(User u){
        try {
            serverSocket= new Socket(host, port);
            ObjectOutputStream oos= new ObjectOutputStream(serverSocket.getOutputStream());
            oos.writeObject(u);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // da co cong gui roi thi lay cong do de nhan di lieu
    public List<User> receiveUser(){
        List<User> list= new ArrayList<>();
        try {
            ObjectInputStream ois= new ObjectInputStream(serverSocket.getInputStream());
            list= (List<User>)ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public boolean receiveMess(){
        boolean check=false;
        try {
            ObjectInputStream ois= new ObjectInputStream(serverSocket.getInputStream());
            check= (boolean)ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(clientControl.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(clientControl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return check;
    }
}
