/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerToPeer.client;

import PeerToPeer.server.IServer;
import entity.Block;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.PublicKey;
import java.util.ArrayList;
import mncoin.TransactionInput;
import mncoin.TransactionOutput;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class ImpClient extends UnicastRemoteObject implements IClient{
    private ArrayList<String> otherPeers;
    private String targerServer;
    IServer server;
    InetAddress IP;
    public ImpClient(IServer server, ArrayList<String> otherPeers, String targerServer, int step) throws RemoteException{
        this.targerServer = targerServer;
        this.server = server;
        this.otherPeers = otherPeers;
        try {
            IP = InetAddress.getLocalHost();
        } catch (UnknownHostException ex) {
        //    Logger.getLogger(ImpClient.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(step==0){
            server.addConnectedAble(this, IP.getHostAddress());
        }
    }

    public String getTargerServer() {
        return targerServer;
    }
    @Override
    public void notifyToOtherPeers(String msg) throws RemoteException {
    //    System.out.println("T");
        msg = "["+IP.getHostAddress()+"] "+msg;
        server.broadCastMessage(msg);
    }
    public int checkStatusReadyToDowloadBlockchain() throws RemoteException{ // kiem tra xem peer khacs co san sang de download data blockchain ko
        return server.GetStatusDataBlockchain();
    }
    public boolean requestOtherPeerGetBlockFromFile() throws RemoteException{
        server.setIsHandlingGetBlocks();
        if(server.getBlockFromFile()){
            server.setIsReadyToDownloadBlocks();
            return true;
        }else{
            return false;
        }
    }
    public int getNumberOfBlocksFromOtherPeer() throws RemoteException{
        return server.getNumberOfBlocks();
    }
    public Block getBlockFromOtherPeers(int index) throws RemoteException{ // lay data cua mot block cu the tuw peer khac
    //    if(server.GetStatusDataBlockchain() == 1){
            return server.getBlock(index);
    //    }
    }
    public boolean sendNewBlockToOtherPeer(Block block) throws RemoteException{
        return server.updateBlockchain(block);
    }
    public boolean sendTransactionToOtherPeer(ArrayList<TransactionInput> inputs, String PubSender, String PubRecipient, float TotalValue, float value, JSONObject Signature, String CreateTime) throws RemoteException, JSONException{
        String Sig = Signature.getString("signature");
        String Algorithm = Signature.getString("algorithm");
        return server.handlerTransactions(inputs,PubSender, PubRecipient, TotalValue, value, Sig,Algorithm, CreateTime);
    }
    public ArrayList<TransactionOutput> getBalance(String PublicKey) throws RemoteException{
        return server.getBalance(PublicKey);
    }
}
