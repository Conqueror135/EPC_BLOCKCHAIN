/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mncoin;

import PeerToPeer.client.ImpClient;
import common.Calculator;
import common.DigiSig;
import java.io.UnsupportedEncodingException;
import java.rmi.RemoteException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Admin
 */
public class HandlerWallet {
    private TransactionOutput[] myUTXO;
    private String PubSender;
    private String PubRecipient;
    private JSONObject Signature;
    private ArrayList<ImpClient> OtherPeers;
    private ArrayList<TransactionOutput> outputs;
    private ArrayList<TransactionInput> inputs;
    private String CreateTime;
    private float value;
    private float TotalValue;
    
    public HandlerWallet(ArrayList<ImpClient> OtherPeers, TransactionOutput[] myUTXO, String PubSender, String PubRecipient, float value) {
        this.OtherPeers = OtherPeers;
        this.myUTXO = myUTXO;
        this.PubSender = PubSender;
        this.PubRecipient = PubRecipient;
        this.outputs = new ArrayList<TransactionOutput>();
        this.inputs = new ArrayList<TransactionInput>();
        this.CreateTime = java.time.LocalDateTime.now().toString();
        this.value = value;
        this.TotalValue = 0;
    }
    public boolean initBeforeSendFunds(){
        for(TransactionOutput tran : myUTXO ){
            inputs.add(new TransactionInput(tran.id));
            TotalValue+= tran.value;
            if(TotalValue>=value)
                break;
        }
        if(TotalValue<value) 
            return false;
        return true;
    }
    public boolean generateSignature(String PrivSender){
        //DigiSig digisig = new DigiSig();
        String plaintext = PubSender+ PubRecipient+ String.valueOf(value)+CreateTime;
        try {
            Signature = DigiSig.Sig(PubSender , PrivSender, plaintext);
            return true;
        } catch (NoSuchAlgorithmException|InvalidAlgorithmParameterException|InvalidKeyException|UnsupportedEncodingException|SignatureException|JSONException|InvalidKeySpecException ex) {
            return false;
        }
    }
    public boolean Send() throws RemoteException, JSONException{
        for(ImpClient otherPeer : OtherPeers){
            otherPeer.sendTransactionToOtherPeer(inputs,PubSender, PubRecipient, TotalValue, value, Signature, CreateTime);
        }
        return true;
    }
}
