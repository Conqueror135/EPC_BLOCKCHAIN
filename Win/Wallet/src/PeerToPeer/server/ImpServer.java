/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PeerToPeer.server;

import PeerToPeer.client.ImpClient;
import PeerToPeer.client.IClient;
import common.Calculator;
import common.CreateConnect;
import common.DigiSig;
import common.HandlerFile;
import entity.Block;
import entity.Transaction;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import mncoin.TransactionInput;
import mncoin.TransactionOutput;
import org.json.JSONException;
import org.json.JSONObject;
import system.Config;

/**
 *
 * @author Admin
 */
public class ImpServer extends UnicastRemoteObject implements IServer{

    private ArrayList<ImpClient> peers;
    private ArrayList<String> otherPeers;
    private ArrayList<Block> blocks;
    private boolean isReadyToDownloadBlocks;
    private boolean isHandlingGetBlocks;
    private Block TopBlock;
    private boolean IsCreatingBlock;
    private  Map<String,TransactionOutput> UTXOs ;
    private ArrayList<Transaction> WaitingTransaction;
    
    public ImpServer(ArrayList<ImpClient> peers, ArrayList<String> otherPeers) throws RemoteException{
        this.isReadyToDownloadBlocks= false;
        this.peers = peers;
        this.otherPeers = otherPeers;
//        this.IsCreatingBlock = false;
//        this.WaitingTransaction = new ArrayList<Transaction>();
//        this.UTXOs = new HashMap<String,TransactionOutput>();
//        HandlerFile hf = new HandlerFile();
//        if(hf.ReadFileConfig()){
//            if(hf.getConfig().isIsBlockchainReady()){
//                this.isReadyToDownloadBlocks=true;
//                
//                if(hf.ReadFileConfig()){
//    //                System.out.println("file config ok");
//                    Config config = hf.getConfig();
//                    System.out.println(config.getLocationSaveBlockchain());
//                    if(hf.ReadFileBlockChain(config.getLocationSaveBlockchain())){
//    //                    System.out.println("file block ok");
//                        this.blocks = new ArrayList<Block>(Arrays.asList(hf.getBlocks()));
//                    }else{
//                        System.out.println("no ok blocks");
//                    }
//                    if(hf.ReadBlockFromFileTopBlock(config.getLocationSaveBlockchain())){
//                        System.out.println("contructor read topblock");
//                        this.TopBlock = hf.getTopBlock();
//                    }  
//                    for(Block block : this.blocks){
//                        if(block.getTrans()!=null)
//                        for(Transaction tran : block.getTrans()){
//                            for(TransactionOutput out : tran.getOutputs()){
//                                //    transactionId = Calculator.stringHash(planttext);
////                                System.out.println(out.toString());
//                                this.UTXOs.put(out.id, out);
//                            }
//                        }
//                    }                    
//                }                
//
//
//            }
//        }
    }
    @Override
    public void broadCastMessage(String msg) throws RemoteException {
        System.out.println(msg);
    }
    @Override
    public void addConnectedAble(IClient client, String IpClient) throws RemoteException {
        CreateConnect createConect = new CreateConnect(peers, otherPeers, IpClient);
        createConect.start();   
    }  
    
    // Handle Blockchain
    @Override
    public Block getBlock(int index) throws RemoteException {
        System.out.println("getblock "+blocks.size()+"  " + blocks.get(index).getNonce());
        return blocks.get(index);
    }

    @Override
    public ArrayList<Block> getBlocks(int begin, int end) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetStatusDataBlockchain() throws RemoteException {

//        HandlerFile hf = new HandlerFile();
//        if(hf.ReadFileConfig()){
//            Config config = hf.getConfig();
//            if(config.isIsBlockchainReady() == false){
//                return -2;
//            }else{
//                return 1;
//            }
//        }else {
//            return -2;
//        }
    return 1;
    }
    @Override
    public boolean getBlockFromFile(){
//        System.out.println("client call");
//        HandlerFile hf = new HandlerFile();
//        if(hf.ReadFileConfig()){
//            System.out.println("file config ok");
//            Config config = hf.getConfig();
//            System.out.println(config.getLocationSaveBlockchain());
//            if(hf.ReadFileBlockChain(config.getLocationSaveBlockchain())){
//                System.out.println("file block ok");
//                blocks = new ArrayList<>(Arrays.asList(hf.getBlocks()));
//                return true;
//            }
//        }
//        System.out.println("file ko ok");
        return false;
    }

    @Override
    public void setIsReadyToDownloadBlocks() throws RemoteException {
        System.out.println("client set isReady");
        isReadyToDownloadBlocks = true;
    }

    @Override
    public void setIsHandlingGetBlocks() throws RemoteException {
        System.out.println("client set isHanlding ok");
        isHandlingGetBlocks = true;
    }

    @Override
    public int getNumberOfBlocks() throws RemoteException {
        System.out.println("client get num");
        return blocks.size();
    }

    @Override
    public boolean updateBlockchain(Block block) throws RemoteException {
//        int newIndex = Integer.parseInt(block.getIndex());
//    //    Block[] blocks = new Block[newIndex];
//        HandlerFile hf = new HandlerFile();
//        if(hf.ReadFileConfig()){
//            if(blocks.size()>0){
//                blocks.add(block);
//                if(hf.WriteBlockToFileTopBlock(block, hf.getConfig().getLocationSaveBlockchain() )){
//                    System.out.println("Write file Topblock ok!");
//                }
//                if(hf.WriteFileBlockchain( blocks.toArray(new Block[blocks.size()]), hf.getConfig().getLocationSaveBlockchain()))
//                    return true;                
//            }            
//        }

        return false;
    }
    public boolean updateNewBlockInMyself(Block block){
//        HandlerFile hf = new HandlerFile();
//        if(hf.ReadFileConfig()){
//            if(blocks.size()>0){
//                blocks.add(block);
//                if(hf.WriteBlockToFileTopBlock(block, hf.getConfig().getLocationSaveBlockchain() )){
//                    System.out.println("Write file Topblock ok!");
//                }
//                if(hf.WriteFileBlockchain( blocks.toArray(new Block[blocks.size()]), hf.getConfig().getLocationSaveBlockchain()))
//                    return true;                
//            }            
//        }    
        return false;
    }    
    // setter and getter
    public Block getTopBlock() {
        return TopBlock;
    }

    public void setIsCreatingBlock(boolean IsCreatingBlock) {
        this.IsCreatingBlock = IsCreatingBlock;
    }

    public boolean getIsCreatingBlock() {
        return IsCreatingBlock;
    }

    @Override
    public boolean handlerTransactions(ArrayList<TransactionInput> inputs, String PubSender, String PubRecipient,  float TotalValue, float value, String Signature,String Algorithm, String CreateTime) throws RemoteException {
//        ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
//        String planttext = PubSender+ PubRecipient+ String.valueOf(TotalValue)+CreateTime;
//        String transactionId="";
//        try {
//            transactionId = Calculator.stringHash(planttext);
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//        
//        
//        try {
//            if(!DigiSig.Verify(Signature, planttext)){ 
//                System.out.println("Chu ky ko hop le!");
//                return false;
//            }
//        } catch (NoSuchAlgorithmException|InvalidKeySpecException|InvalidKeyException|UnsupportedEncodingException|SignatureException|JSONException ex) {
//            return false;
//        } 
//	for(TransactionInput input : inputs) {
//            input.UTXO = UTXOs.get(input.transactionOutputId);
//	}
//        float leftOver = TotalValue -value;
//        try {
//            outputs.add(new TransactionOutput(PubRecipient, value, transactionId));
//            if(leftOver > 0)
//                outputs.add(new TransactionOutput(PubSender, leftOver, transactionId));
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
//            return false;
//        }
//        
//        for(TransactionInput input : inputs) {
//            if(input.UTXO == null) continue; //if Transaction can't be found skip it 
//            UTXOs.remove(input.UTXO.id);
//	}        
//        try {
//            //UTXOs.put(output.id, output);
//            WaitingTransaction.add(new Transaction(PubSender, PubRecipient, CreateTime, DigiSig.getSignature(Signature), value, outputs.toArray(new TransactionOutput[outputs.size()] )));
//        } catch (JSONException ex){
//            System.out.println("loi add waiting transaction!");
//            return false;
//        }

        return true;
    }

    public ArrayList<Transaction> getWaitingTransaction() {
        
        return WaitingTransaction;
    }

    @Override
    public ArrayList<TransactionOutput> getBalance(String PublicKey) throws RemoteException {
        ArrayList<TransactionOutput>  trans = new ArrayList<TransactionOutput>();
//        for(Map.Entry<String, TransactionOutput> entry : UTXOs.entrySet()){
//            if(entry.getValue().isMine(PublicKey)){
//                trans.add(entry.getValue());
//            }
//        }
        return trans;
    }
    
}