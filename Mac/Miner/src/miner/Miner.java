/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miner;

import PeerToPeer.Peer;
import PeerToPeer.client.ImpClient;
import PeerToPeer.server.ImpServer;
import common.HandlerFile;
import entity.Block;
import entity.Transaction;
import java.net.SocketException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import miner.GUI.Index;
import miner.GUI.InitialSetting;
import mncoin.TransactionOutput;
import system.Config;

/**
 *
 * @author Admin
 */
public class Miner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, SocketException {
        // TODO code application logic here
        String systemWallet = "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAET2qhZqxcyFT3nFJ162oWx9W8NHu9xmBRrafdzOeUCLDrGqbxkWJJ+1ZKgFd24wLsQXyEthbWXeE1CKzL5KBUcw==";
        
        Peer myself = new Peer(); // khoi tao va ket noi vao mang peer to peer
        ArrayList<ImpClient> OtherPeers = myself.getPeers(); // lay ket noi voi cac peer khac trong mang
        ImpServer impServer = myself.getServer();
        HandlerFile hf = new HandlerFile(); // khoi tao class xu ly file ( doc, ghi file và tao folder)
        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Miner.class.getName()).log(Level.SEVERE, null, ex);
        }
        Index indexGui = new Index(OtherPeers, impServer);
        impServer.setIndexgui(indexGui);
        if(hf.ReadFileConfig()==false){ // kiem tra xem may tinh da khoi tao cai dat ban dau chua, neu chua thi hien form de khoi tao cai dat
            InitialSetting init = new InitialSetting(myself, OtherPeers);
            init.setVisible(true);             
        }else{
//            hf.ReadFileConfig();
            Config config = hf.getConfig();
        //    System.out.println(config.getLocationSaveBlockchain()+" "+config.getLocationSaveBlockchain());            
            indexGui.setVisible(true);
//            while(true){
//                if()
//            }
        //    PoW pow = new PoW(OtherPeers);
        }
        
        if(hf.getConfig().isIsBlockchainReady()){
            System.out.println(hf.getConfig().getRewardAddressWallet());
            String walletReward = hf.getConfig().getRewardAddressWallet();
            while(true){
            //    System.out.println("ll");
                if(!indexGui.getIsStopMining()&& !impServer.getIsCreatingBlock()){
                    Block TopBlock = impServer.getTopBlock();
                    ArrayList<Transaction> DataTransaction = impServer.getWaitingTransaction();
                    indexGui.setDataListTransaction(DataTransaction);
                    String TimeCreate = java.time.LocalDateTime.now().toString();
                    float reward = (float) TopBlock.getCore().getReward();
                    TransactionOutput[] transReward= {new TransactionOutput(systemWallet, walletReward, reward, "EPC",0, TimeCreate)};
                    DataTransaction.add(new Transaction(TopBlock.getCore().getSystemAddress(),walletReward,TimeCreate,"EPC", reward, transReward));
                    Transaction[] trans = DataTransaction.toArray(new Transaction[impServer.getWaitingTransaction().size()]);
                    System.out.println("trán "+trans.length);
                    impServer.setIsCreatingBlock(true);
                    
                //    System.out.println(TopBlock.getNonce());
                    Block newBlock = new Block();
                    newBlock.setMiner(walletReward);
                    newBlock.setTimestamp(TimeCreate);
                    newBlock.setTrans(trans);
                    PoW pow = new PoW(OtherPeers, impServer, TopBlock, newBlock ); 
                    try {
                        pow.started();
                    } catch (NoSuchAlgorithmException ex) {
                        Logger.getLogger(Miner.class.getName()).log(Level.SEVERE, null, ex);
                    }
//                    if(DataTransaction.size()>0){
//                        DataTransaction.remove(DataTransaction.size()-1);
//                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Miner.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        //    System.out.println("kkk");
            
        }
    }  
}
