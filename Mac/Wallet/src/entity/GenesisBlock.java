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
//       if(hf.WriteFileConfig(new Config("D:\\EPC\\","MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAEFopTPD5fYYcggdsjDgj8e1F7jC5hkjwW/DsRRztPjfjosuIykyYdxaLzxYFZdv11dvhF8s0orZFBOJ2kDW78zQ==","123",true))){
//           System.out.println("ok");
//       }else{
//           System.out.println("no ok");
//       }

//        hf.ReadFileConfig();
//        System.out.println(hf.getConfig().isIsBlockchainReady());


        // test create genesisBlock..            ...
        
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
    
       if(hf.WriteBlockToFileTopBlock(a1,"D:\\EPC\\" )){
           System.out.println("ok");
       } else{
           System.out.println("Loio");
       }

       
       
       
       
       
//         System.out.println("đ");
//        if(hf.ReadFileConfig())
//        if(hf.ReadFileBlockChain(hf.getConfig().getLocationSaveBlockchain()+"Blocks.bin")){
//        //    Block[] b= hf.getBlocks();
//    //    System.out.println("đ");
//            System.out.println( hf.getBlocks()[0].getNonce());
//        }


//       if(hf.createNewFolder("d:/blockchain1"))
//            if(hf.createNewFile("d:/blockchain1/mydata.bin")){
//                Block b[]= {new Block("0","",java.time.LocalDateTime.now().toString(),"gdgsjdgs","10","gsgds",null,1,"thang",null)};
//                hf.WriteFileBlockchain(b,"d:/blockchain1/mydata.bin" );
//            }
       
//  try {
//    //Bước 1: Tạo đối tượng luồng và liên kết nguồn dữ liệu
//    FileInputStream fis = new FileInputStream("d:/blockchain1/mydata.bin");
//    ObjectInputStream ois = new ObjectInputStream(fis);
//    //Bước 2: Đọc dữ liệu
//    Block[] b = (Block[]) ois.readObject();
////    for(int i=0;i<b.length;i++){
////        for(int j=0;j<sArr[i].trans.length;j++){
////            System.out.println(sArr[i].trans[j].toString());
////        }
//        System.out.println(b[0].getMiner());
////    }
////    for(Block s : sArr){
////      System.out.println(s.trans);
////    }
//    //Bước 3: Đóng luồng
//    fis.close();
//    ois.close();
//  } catch (Exception ex) {
//    System.out.println("Loi doc file: "+ex);
// }       
//       
//       
       
       
       
       
       
       
    //System.out.println(java.time.LocalDateTime.now()); 
    }
// String //Height, String PreviousHeaderHash, String Timestamp, String MerkleRootHash, String Nonce, String HeaderHash, Transaction[] Trans, int Confirmations, String Miner, Core core   
}
