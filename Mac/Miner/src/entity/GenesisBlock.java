/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import common.HandlerFile;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import system.Config;

/**
 *
 * @author Admin
 */
public class GenesisBlock {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HandlerFile hf = new HandlerFile();
    
        Core core;
        core = new Core(6969696969.0, 5, 50.0, 4);
        Block a1 = new Block();
        a1.setIndex("0");
        a1.setHeaderHash("00000000000000000003bf96a323f067b32c45cd2b966b46ea04571f9d369a7f");
        a1.setMerkleRootHash("3456034d278c522efb18991d2da6e0381d8edffdb1ac6dbd2788fef3e0db8c35");
        a1.setMiner("MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEFopTPD5fYYcggdsjDgj8e1F7jC5hkjwW/DsRRztPjfjosuIykyYdxaLzxYFZdv11dvhF8s0orZFBOJ2kDW78zQ==");
        a1.setTimestamp("2021-05-13T11:11:11.111");
    //    System.out.println(java.time.LocalDateTime.now().toString());
        a1.setPreviousHeaderHash(null);
        a1.setNonce("13052000");
        a1.setTrans(null);
        a1.setConfirmations(1);
        a1.setCore(core);
        Block b[]= {a1};
    
       if(hf.WriteFileBlockchain(b,"D:\\EPC\\" )){
           System.out.println("Write file Blocks success!");
       } else{
           System.out.println("Error write file Blocks!");
       }
       if(hf.WriteBlockToFileTopBlock(a1,"D:\\EPC\\" )){
           System.out.println("Write file Top Block success!");
       } else{
           System.out.println("Error write file TopBlock!");
       }
    }
}
