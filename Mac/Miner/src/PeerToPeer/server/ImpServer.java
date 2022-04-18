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
import miner.GUI.Index;
import mncoin.MnCoin;
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
    private ArrayList<TransactionInput> TotalInputs;
    private boolean IsBlockCreated;
    Index indexgui;
    public ImpServer(ArrayList<ImpClient> peers, ArrayList<String> otherPeers) throws RemoteException{
        this.isReadyToDownloadBlocks= false;
        this.peers = peers;
        this.otherPeers = otherPeers;
        this.IsCreatingBlock = false;
        this.WaitingTransaction = new ArrayList<Transaction>();
        this.UTXOs = new HashMap<String,TransactionOutput>();
        this.TotalInputs = new ArrayList<TransactionInput>();
        this.IsBlockCreated = false;
        HandlerFile hf = new HandlerFile();
        if(hf.ReadFileConfig()){
            if(hf.getConfig().isIsBlockchainReady()){
                this.isReadyToDownloadBlocks=true;
                
                if(hf.ReadFileConfig()){
    //                System.out.println("file config ok");
                    Config config = hf.getConfig();
                    System.out.println(config.getLocationSaveBlockchain());
                    if(hf.ReadFileBlockChain(config.getLocationSaveBlockchain())){
    //                    System.out.println("file block ok");
                        this.blocks = new ArrayList<Block>(Arrays.asList(hf.getBlocks()));
                    }else{
                        System.out.println("no ok blocks");
                    }
                    if(hf.ReadBlockFromFileTopBlock(config.getLocationSaveBlockchain())){
                        System.out.println("contructor read topblock");
                        this.TopBlock = hf.getTopBlock();
                    }  
                    for(Block block : this.blocks){
                        if(block.getTrans()!=null)
                        for(Transaction tran : block.getTrans()){
                            for(TransactionOutput out : tran.getOutputs()){
                                this.UTXOs.put(out.id, out);
                            }
                        }
                    }                    
                }                
            }
        }
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
        System.out.println("getblock "+index+"  " + blocks.get(index).getNonce());
        System.out.println(blocks.get(index).getHeaderHash());
        return blocks.get(index);
    }

    @Override
    public ArrayList<Block> getBlocks(int begin, int end) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int GetStatusDataBlockchain() throws RemoteException {

        HandlerFile hf = new HandlerFile();
        if(hf.ReadFileConfig()){
            Config config = hf.getConfig();
            if(config.isIsBlockchainReady() == false){
                return -2;
            }else{
                return 1;
            }
        }else {
            return -2;
        }
    }
    @Override
    public boolean getBlockFromFile(){
        System.out.println("client call");
        HandlerFile hf = new HandlerFile();
        if(hf.ReadFileConfig()){
            System.out.println("file config ok");
            Config config = hf.getConfig();
            System.out.println(config.getLocationSaveBlockchain());
            if(hf.ReadFileBlockChain(config.getLocationSaveBlockchain())){
                System.out.println("file block ok");
                blocks = new ArrayList<>(Arrays.asList(hf.getBlocks()));
                return true;
            }
        }
        System.out.println("file ko ok");
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
        int newIndex = Integer.parseInt(block.getIndex());
        try {
            if(! MnCoin.isValidBlock(TopBlock, block)){// kiem tra xem block moi co hop le hay ko
                System.out.println("block ko hop le");
                return false;
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        this.IsBlockCreated= true;
        this.TopBlock = block;
        if(block.getTrans().length>1&&WaitingTransaction.size()>1){
            for(int i=block.getTrans().length-1;i>=0;i--)
                WaitingTransaction.remove(i); // xoá các giao dịch trong bang tam sau khi tạo block thành công!
        }
        for(int i=TotalInputs.size()-1;i>=0;i--){
            UTXOs.remove(TotalInputs.get(i).UTXO);// xoa cac output da dc tieu va xoa cac input
            TotalInputs.remove(i);
        }
        for(Transaction tran : block.getTrans()){
            for(TransactionOutput out : tran.getOutputs()){
                out.Status=1;
                this.UTXOs.put(out.id, out);
            }
        }    
        HandlerFile hf = new HandlerFile();
        if(hf.ReadFileConfig()){
            if(blocks.size()>0){
                blocks.add(block);
                if(hf.WriteBlockToFileTopBlock(block, hf.getConfig().getLocationSaveBlockchain() )){
                    System.out.println("Write file Topblock ok!");
                }
                if(hf.WriteFileBlockchain( blocks.toArray(new Block[blocks.size()]), hf.getConfig().getLocationSaveBlockchain()))
                    return true;                
            }            
        }

        return false;
    }
    public boolean updateNewBlockInMyself(Block block){
            
        HandlerFile hf = new HandlerFile();
        if(block.getTrans()!=null)
        for(Transaction tran : block.getTrans()){
            for(TransactionOutput out : tran.getOutputs()){
                out.Status=1;
                this.UTXOs.put(out.id, out);
            }
        }
        for(int i=TotalInputs.size()-1;i>=0;i--){// xoa cac output da dc tieu va xoa cac input
            UTXOs.remove(TotalInputs.get(i).UTXO);
            TotalInputs.remove(i);
        }        
        this.TopBlock = block;
        if(block.getTrans().length>0&&WaitingTransaction.size()>0){
            for(int i=block.getTrans().length-1;i>=0;i--)
                WaitingTransaction.remove(i); // xoá các giao dịch trong bang tam sau khi tạo block thành công!
        }
        System.out.println("size transaction: "+WaitingTransaction.size());
        if(hf.ReadFileConfig()){
            if(blocks.size()>0){
                blocks.add(block);
                if(hf.WriteBlockToFileTopBlock(block, hf.getConfig().getLocationSaveBlockchain() )){
                    System.out.println("Write file Topblock ok!");
                }
                if(hf.WriteFileBlockchain( blocks.toArray(new Block[blocks.size()]), hf.getConfig().getLocationSaveBlockchain()))
                    return true;                
            }            
        }    
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

    public boolean getIsBlockCreated() {
        return IsBlockCreated;
    }

    public void setIsBlockCreated(boolean IsBlockCreated) {
        this.IsBlockCreated = IsBlockCreated;
    }

    public void setIndexgui(Index indexgui) {
        this.indexgui = indexgui;
    }
    

    @Override
    public boolean handlerTransactions(ArrayList<TransactionInput> inputs, String PubSender, String PubRecipient,  float TotalValue, float value,String Signature,String Algorithm, String CreateTime) throws RemoteException {
        System.out.println("wallet id sending transaction!");
        ArrayList<TransactionOutput> outputs = new ArrayList<TransactionOutput>();
        String planttext = PubSender+ PubRecipient+ String.valueOf(value)+CreateTime;
        String transactionId="";
        JSONObject sig = new JSONObject();
        try {
            sig.put("publicKey", PubSender);
            sig.put("signature", Signature);
            sig.put("algorithm", Algorithm);            
        } catch (JSONException ex) {
            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            transactionId = Calculator.stringHash(planttext);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        
        try {
            if(!DigiSig.Verify(sig, planttext)){ 
                System.out.println("Chu ky ko hop le!");
                return false;
            }
        } catch (NoSuchAlgorithmException|InvalidKeySpecException|InvalidKeyException|UnsupportedEncodingException|SignatureException|JSONException ex) {
            return false;
        } 
	for(TransactionInput input : inputs) {
            input.UTXO = UTXOs.get(input.transactionOutputId);
	}
        float leftOver = TotalValue -value;
        try {
            outputs.add(new TransactionOutput(PubSender, PubRecipient, value, transactionId,0, CreateTime));
            if(leftOver > 0)
                outputs.add(new TransactionOutput(PubSender, PubSender, leftOver, transactionId, 0, CreateTime));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
        
        for(TransactionInput input : inputs) {
            if(input.UTXO == null) continue; //if Transaction can't be found skip it 
           // UTXOs.remove(input.UTXO.id);
           TransactionOutput tran = input.UTXO;
           TotalInputs.add(input);
           tran.Status= -1;
            System.out.println(tran);
           UTXOs.put(input.UTXO.id,tran);
	}        
        try {
            //UTXOs.put(output.id, output);
            WaitingTransaction.add(new Transaction(PubSender, PubRecipient, CreateTime, DigiSig.getSignature(sig), value, outputs.toArray(new TransactionOutput[outputs.size()] )));
            indexgui.addTransactionToWatingTable(new Transaction(PubSender, PubRecipient, CreateTime, DigiSig.getSignature(sig), value, outputs.toArray(new TransactionOutput[outputs.size()] )));
        } catch (JSONException ex){
            System.out.println("loi add waiting transaction!");
            return false;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ImpServer.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

        return true;
    }

    public ArrayList<Transaction> getWaitingTransaction() {
        
        return WaitingTransaction;
    }

    @Override
    public ArrayList<TransactionOutput> getBalance(String PublicKey) throws RemoteException {
        System.out.println("wallet calling");
        ArrayList<TransactionOutput>  trans = new ArrayList<TransactionOutput>();
    //    System.out.println(PublicKey);
        for(Map.Entry<String, TransactionOutput> entry : UTXOs.entrySet()){
    //        System.out.println(entry.getValue().recipient);
            if(entry.getValue().isMine(PublicKey)||entry.getValue().isSender(PublicKey)){
                trans.add(entry.getValue());
            }
        }
        for(Transaction tt: WaitingTransaction){
            if(tt.getSender().equals(PublicKey)||tt.getReceiver().equals(PublicKey)&&!tt.getSender().equals(TopBlock.getCore().getSystemAddress())){
                for(TransactionOutput to: tt.getOutputs()){
                    trans.add(to);
                }
            }
        }
        System.out.println(trans.size());
        return trans;
    }
    
}