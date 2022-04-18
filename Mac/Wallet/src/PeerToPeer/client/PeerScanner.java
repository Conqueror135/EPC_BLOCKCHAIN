/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerToPeer.client;

import PeerToPeer.server.IServer;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class PeerScanner {
    ArrayList<ImpClient> peers;
    ArrayList<String> IpOtherPeers;
    
    public PeerScanner(ArrayList<ImpClient> peers, ArrayList<String> IpOtherPeers) {
        this.peers = peers;
        this.IpOtherPeers = IpOtherPeers;
    }
    public void scan() throws SocketException{
        
        
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress  IP = (InetAddress) ee.nextElement();
                if(!IP.getHostAddress().contains(":")){
                    try {
                        this.checkHosts(BreakIp(IP.getHostAddress()), IP.getHostAddress());
                    } catch (UnknownHostException ex) {
                        Logger.getLogger(PeerScanner.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            }
        }        
    }
    private void checkHosts( String subnet, String MyIp) throws UnknownHostException{
        for (int i=2;i<255;i++){
            String Host=subnet + "." + i;
            if(!Host.equals(MyIp)){
                CheckServer check = new CheckServer(peers, IpOtherPeers, Host);
                check.start();
            }          
        }      
    }
    private String BreakIp(String ip) {
    	 return ip.substring(0,ip.lastIndexOf(".") );
    }
}
class CheckServer extends Thread{

    private String ipOtherPeer;
    private ImpClient client;
    private ArrayList<ImpClient> peers;
    ArrayList<String> IpOtherPeers; 
    
    public CheckServer(ArrayList<ImpClient> peers, ArrayList<String> IpOtherPeers, String ipOtherPeer) {
        this.peers = peers;
        this.ipOtherPeer = ipOtherPeer;
        this.IpOtherPeers = IpOtherPeers; 
    }
    @Override
     public void run(){
         
        try {
            if(IpOtherPeers.contains(ipOtherPeer) == false){
                IServer server = (IServer)Naming.lookup("rmi://"+ipOtherPeer+"/EPC");
                
                client= new ImpClient( server, IpOtherPeers,ipOtherPeer, 0);
                IpOtherPeers.add(ipOtherPeer);
                if(peers.contains(client) == false)
                    peers.add(client);                
            }
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
	//System.out.println("[System] Server failed: " + e+" "+ipOtherPeer);        
	}
    }
}