/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miner;

import PeerToPeer.client.ImpClient;
import PeerToPeer.server.ImpServer;
import common.Calculator;
import entity.Block;
import entity.Core;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
public class PoW{
    ArrayList<ImpClient> OtherPeers;
    private ImpServer impServer;
    private Block oldBlock;
    private Block newBlock;
    private Core core;
    private int dificult;
    private int oldIndex;
    private boolean IsStoped;
    private Calculator cal;
    
    public PoW(ArrayList<ImpClient> OtherPeers, ImpServer impServer, Block oldBlock, Block newBlock) {
        this.OtherPeers = OtherPeers;
        this.impServer = impServer;
        this.oldBlock = oldBlock;
        this.newBlock = newBlock;
        core = oldBlock.getCore();
        this.dificult = core.getDifficult();
        this.oldIndex = Integer.parseInt(oldBlock.getIndex());
        this.IsStoped = false;
        this.cal = new Calculator();
    }

    public void setIsStoped(boolean IsStoped) {
        this.IsStoped = IsStoped;
    }
    public void started() throws NoSuchAlgorithmException{
        newBlock.setIndex(String.valueOf(oldIndex+1));
        newBlock.setCore(core);
        
        newBlock.setConfirmations(1);
        newBlock.setPreviousHeaderHash(oldBlock.getHeaderHash());
        newBlock.setMerkleRootHash(cal.calculateMerkleRootHash(newBlock.getTrans()));
        
        for(long i=0; ; i++){
            if(IsStoped|| impServer.getIsBlockCreated())
                break;
            String hex = String.format("%x", i);
            newBlock.setNonce(hex);
            String hasHeader =cal.calulateHeaderHash(newBlock);
            if(isHashValid(hasHeader, dificult)){
                newBlock.setHeaderHash(hasHeader);
                break;
            }
        } 
        if(!IsStoped&&!impServer.getIsBlockCreated()){
            System.out.println("NewBlock : "+newBlock.getHeaderHash()+" "+newBlock.getNonce());
            
            for(int j=0; j < OtherPeers.size(); j++){
                try {
                    OtherPeers.get(j).sendNewBlockToOtherPeer(newBlock);
                } catch (RemoteException ex) {
                    System.out.println("ko gui dc block");
                }
            }
            impServer.updateNewBlockInMyself(newBlock);            
            impServer.setIsCreatingBlock(false);
            impServer.setIsBlockCreated(false);
        }
    }
    	private static String repeat(String str, int repeat) {
		final StringBuilder buf = new StringBuilder();
		for (int i = 0; i < repeat; i++) {
			buf.append(str);
		}
		return buf.toString();
	}
	public static boolean isHashValid(String hash, int difficulty) {
		String prefix = repeat("0", difficulty);
		return hash.startsWith(prefix);
	}
//    @Override
//    public void run(){
//        
//    }
}
