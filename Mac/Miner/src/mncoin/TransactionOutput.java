/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mncoin;

import common.Calculator;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

/**
 *
 * @author Admin
 */
public class TransactionOutput implements Serializable  {
	public String id;
	public String recipient; //also known as the new owner of these coins.
	public float value; //the amount of coins they own
	public String parentTransactionId; //the id of the transaction this output was created in
	public int Status;
        public String timeStamp;
        public String sender;
	//Constructor
	public TransactionOutput(String sender, String recipient, float value, String parentTransactionId, int Status, String timeStamp) throws NoSuchAlgorithmException {
		this.sender = sender;
                this.recipient = recipient;
		this.value = value;
		this.parentTransactionId = parentTransactionId;
		this.id = Calculator.stringHash(recipient+Float.toString(value)+parentTransactionId+java.time.LocalDateTime.now().toString());
                this.Status = Status;
                this.timeStamp = timeStamp;
	}
	
	//Check if coin belongs to you
	public boolean isMine(String publicKey) {
		return (publicKey == null ? recipient == null : publicKey.equals(recipient));
	}    
        public boolean isSender(String publickey){
            return (publickey == null ? recipient == null : publickey.equals(sender));
        }
        @Override
        public String toString(){
            return id+"_"+recipient+"_"+value+"_"+parentTransactionId+"_"+timeStamp;
        }
}
