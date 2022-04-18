/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wallet;

import PeerToPeer.Peer;
import PeerToPeer.client.ImpClient;
import PeerToPeer.server.ImpServer;
import common.HandlerFile;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import mncoin.TransactionOutput;
import wallet.GUI.Index;

/**
 *
 * @author Admin
 */
public class Wallet {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Peer myself = new Peer(); // khoi tao va ket noi vao mang peer to peer
        ArrayList<ImpClient> OtherPeers = myself.getPeers(); // lay ket noi voi cac peer khac trong mang
        ImpServer impServer = myself.getServer();
        System.out.println(OtherPeers);
        HandlerFile hf = new HandlerFile(); // khoi tao class xu ly file ( doc, ghi file và tao folder)
        Index indexGui = new Index(OtherPeers); 
        indexGui.setVisible(true);
        while(true){
            float value = 0;
            float pending =0;
            if(hf.ReadFileConfig()){
                for(ImpClient otherPeer : OtherPeers){
                    try {
                        ArrayList<TransactionOutput> myOutput = otherPeer.getBalance(hf.getConfig().getAddressWallet());
                        indexGui.setMyUTXO(myOutput.toArray(new TransactionOutput[myOutput.size()]));
                        for(int i =0; i< myOutput.size();i++)
                            if(myOutput.get(i).Status==1)
                                value+= myOutput.get(i).value;
                            else
                                pending+=myOutput.get(i).value;
                        break;
                        //    System.out.println(myOutput.get(i).toString());
                    } catch (RemoteException ex) {
                        Logger.getLogger(Wallet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }                  
            }

            indexGui.updateBalance(value, pending, value+pending);
            System.out.println("kk");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Wallet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
    
}
