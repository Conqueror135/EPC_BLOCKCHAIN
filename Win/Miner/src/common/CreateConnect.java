/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import PeerToPeer.client.ImpClient;
import PeerToPeer.server.IServer;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class CreateConnect extends Thread{
    String ipOther;
    ArrayList<ImpClient> peers;
    ArrayList<String> otherPeers;
    
    public CreateConnect( ArrayList<ImpClient> peers, ArrayList<String> otherPeers, String ipOther) {
        this.ipOther = ipOther;
        this.peers = peers;
        this.otherPeers = otherPeers;
    }
    @Override
    public void run(){
        InetAddress IP;    
        if(otherPeers.contains(ipOther)==true){
            otherPeers.remove(ipOther);
        }
        otherPeers.add(ipOther); 
        try {
            IServer server = (IServer)Naming.lookup("rmi://"+ipOther+"/EPC");
            ImpClient client2= new ImpClient( server, otherPeers, ipOther, 1);
            this.peerIsExist(peers, client2);
            peers.add(client2);
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            //System.out.println("[System] Server failed: " + e);  
        }
    }
    private void peerIsExist(ArrayList<ImpClient> peers, ImpClient client ){
        for(int i=peers.size()-1 ;i>=0;i--){
            if(peers.get(i).getTargerServer().equals(client.getTargerServer())== true){
                peers.remove(i);
            }
        }
    }    
}
