/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import entity.Block;
import entity.Transaction;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class Calculator {
    public static String calculateHashBlock(Block block) throws NoSuchAlgorithmException{
        String record = block.getIndex()+block.getPreviousHeaderHash()+block.getTimestamp()+block.getMerkleRootHash()+block.getNonce()+block.getConfirmations()+block.getMiner();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(record.getBytes(StandardCharsets.UTF_8)); 
        return bytesToHex(encodedhash);
    }
    public String calculateMerkleRootHash(Transaction[] trans) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        String record = "";
        for(int i=0; i<trans.length; i++){
            String tran = trans[i].toString();
            byte[] encodedhash = digest.digest(tran.getBytes(StandardCharsets.UTF_8));
            record += bytesToHex(encodedhash);
        }
        byte[] hash = digest.digest(record.getBytes(StandardCharsets.UTF_8)); 
        return bytesToHex(hash);        
    }
    public String calulateHeaderHash(Block block) throws NoSuchAlgorithmException{
        String record = block.getIndex()+block.getPreviousHeaderHash()+block.getTimestamp()+block.getMerkleRootHash()+String.valueOf(block.getConfirmations())+block.getNonce()+block.getCore().toString();
        return stringHash(record);
    }
    public static String stringHash(String s) throws NoSuchAlgorithmException{
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(s.getBytes(StandardCharsets.UTF_8)); 
        return bytesToHex(encodedhash);        
    }
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
    public static String getStringFromKey(Key key){
        return Base64.getEncoder().encodeToString(key.getEncoded());
    }
//    public static void main(String[] args){
//        ArrayList<Transaction> trans = new ArrayList<Transaction>();
//        trans.add(new Transaction("Thang", "Than", 5.2, 0.02));
//        trans.add(new Transaction("Thanh", "Than", 8.2, 0.02));
//        try {
////            System.out.println(testHash("ThangThanThanh"));
////            System.out.println(testHash("ThangThanThanh"));
//            System.out.println(calculateMerkleRootHash(trans));
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(Calculator.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
//    private String Index;
//    private String PreviousHeaderHash;
//    private String Timestamp;
//    private String MerkleRootHash;
//    private String Nonce;
//    private int Confirmations;
//    private String Miner;   