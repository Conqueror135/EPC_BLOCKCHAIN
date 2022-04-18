/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Block implements Serializable{
    //--Header
    private String Index;
    private String PreviousHeaderHash;
    private String Timestamp;
    private String MerkleRootHash;
    private String Nonce;
    private int Confirmations;
    private String Miner; 
    private Core core;
    //- end
    private String HeaderHash;
    private Transaction[] Trans;


    public void setIndex(String Index) {
        this.Index = Index;
    }
    // Setter
    public void setPreviousHeaderHash(String PreviousHeaderHash) {
        this.PreviousHeaderHash = PreviousHeaderHash;
    }

    public void setTimestamp(String Timestamp) {
        this.Timestamp = Timestamp;
    }

    public void setMerkleRootHash(String MerkleRootHash) {
        this.MerkleRootHash = MerkleRootHash;
    }

    public void setNonce(String Nonce) {
        this.Nonce = Nonce;
    }

    public void setHeaderHash(String HeaderHash) {
        this.HeaderHash = HeaderHash;
    }

    public void setTrans(Transaction[] Trans) {
        this.Trans = Trans;
    }

    public void setConfirmations(int Confirmations) {
        this.Confirmations = Confirmations;
    }

    public void setMiner(String Miner) {
        this.Miner = Miner;
    }

    public void setCore(Core core) {
        this.core = core;
    }
    public Block(){
        
    }
    // Getter
    public String getIndex() {
        return Index;
    }

    public String getPreviousHeaderHash() {
        return PreviousHeaderHash;
    }

    public String getTimestamp() {
        return Timestamp;
    }

    public String getMerkleRootHash() {
        return MerkleRootHash;
    }

    public String getNonce() {
        return Nonce;
    }

    public String getHeaderHash() {
        return HeaderHash;
    }

    public Transaction[] getTrans() {
        return Trans;
    }

    public int getConfirmations() {
        return Confirmations;
    }

    public String getMiner() {
        return Miner;
    }

    public Core getCore() {
        return core;
    }
}