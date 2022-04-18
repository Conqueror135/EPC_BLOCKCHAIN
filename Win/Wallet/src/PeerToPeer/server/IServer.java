/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerToPeer.server;

import PeerToPeer.client.IClient;
import entity.Block;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import mncoin.TransactionInput;
import mncoin.TransactionOutput;


/**
 *
 * @author Admin
 */
public interface IServer extends Remote{
    public void addConnectedAble(IClient client, String IpClient) throws RemoteException;
    public void broadCastMessage(String msg) throws RemoteException;
    
    // Blockchain
    public Block getBlock(int index)throws RemoteException; // lay 1 block cu the theo thu tu
    public ArrayList<Block> getBlocks(int begin, int end) throws RemoteException; // lay nhieu block
    public int GetStatusDataBlockchain() throws RemoteException; // lay trang thai cua server
    public  boolean getBlockFromFile() throws RemoteException; // yeu cau server lay du lieu blockchain dua vao bo nho nhanh
    public void setIsReadyToDownloadBlocks() throws RemoteException;
    public void setIsHandlingGetBlocks() throws RemoteException;
    public int getNumberOfBlocks() throws RemoteException; // lay so luong cua cac block trong blockchain
    public boolean updateBlockchain(Block block) throws RemoteException;
    public boolean handlerTransactions(ArrayList<TransactionInput> inputs, String PubSender, String PubRecipient,  float TotalValue, float value, String Signature,String Algorithm, String CreateTime) throws RemoteException;
    public ArrayList<TransactionOutput> getBalance(String PublicKey) throws RemoteException;
    // end Blockchain
}
