/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerToPeer.server;

import PeerToPeer.client.ImpClient;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;

/**
 *
 * @author Admin
 */
public class ServerDriver extends Thread{
    private ImpServer server;
    private ArrayList<ImpClient> peers;
    private ArrayList<String> otherPeers;
    public ServerDriver(ArrayList<ImpClient> peers, ArrayList<String> otherPeers) {
        this.peers = peers;
        this.otherPeers=otherPeers;
    }
    @Override
    public void run() {
        try {
	// register a name to RMI registry, for binding chatServer
            LocateRegistry.createRegistry(1099);

            server = new ImpServer(peers, otherPeers);
            String url = "rmi://localhost/EPC";
            Naming.rebind(url, server);
            System.out.println("Your server is already!");
        } catch (RemoteException | MalformedURLException e) {
	}
    }

    public ImpServer getServer() {
    //    System.out.println("Seve" + this.server.getTopBlock().getNonce());
        return this.server;
    }
}
