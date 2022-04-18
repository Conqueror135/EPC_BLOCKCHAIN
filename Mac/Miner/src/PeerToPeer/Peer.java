/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerToPeer;

import PeerToPeer.client.ImpClient;
import PeerToPeer.client.PeerScanner;
import PeerToPeer.server.ImpServer;
import PeerToPeer.server.ServerDriver;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Peer {

    /**
     * @param args the command line arguments
     */
    private ArrayList<ImpClient> peers;// danh sach chua cac peer trong mang
    private ArrayList<String> otherPeers;// danh sach chua ip cac peer trong mang
    private ServerDriver server; // server dung de lang nghe cac yeu cau tu nhung peer khac
    
    public Peer(){
        peers = new ArrayList<ImpClient>();
        otherPeers = new ArrayList<String>(); 
        server = new ServerDriver(peers, otherPeers);
        
        server.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }
        PeerScanner scannerPeer = new PeerScanner(peers, otherPeers);
        scannerPeer.scan();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
        }          
    }
    
//    public static void main(String[] args) throws RemoteException {
//        // TODO code application logic here
//        Scanner in = new Scanner(System.in);
//        ArrayList<ImpClient> peers = new ArrayList<ImpClient>(); // danh sach chua cac peer trong mang
//        ArrayList<String> otherPeers = new ArrayList<String>(); // danh sach chua ip cac peer trong mang
//        ServerDriver server = new ServerDriver(peers, otherPeers);
//        server.start();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        PeerScanner scannerPeer = new PeerScanner(peers, otherPeers);
//        scannerPeer.scan();
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(Peer.class.getName()).log(Level.SEVERE, null, ex);
//        }  
//        System.out.println("Say somthing!");
//        while(true){
//            System.out.println("Number of peers: "+peers.size());
//            String  msg = in.nextLine();
//            for(int i =0; i< peers.size(); i++){
//                peers.get(i).notifyToOtherPeers(msg);
//            }    
//        }        
//    }

    public ArrayList<ImpClient> getPeers() {
        return peers;
    }

    public ArrayList<String> getOtherPeers() {
        return otherPeers;
    }

    public ImpServer getServer() {
        return this.server.getServer();
    }
}
