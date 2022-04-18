/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import entity.Block;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import system.Config;

/**
 *
 * @author Admin
 */
public class HandlerFile {

    private Config config;
    private Block[] blocks;
    private Block TopBlock;
    public HandlerFile() {
    }
    
    public boolean createNewFolder(String path){
        File dir = new File(path);
        return dir.mkdirs();
    }
    public boolean createNewFile(String path){
        try {
 
            File file = new File(path);
 
            if (file.createNewFile()) {
                System.out.println("File is created!");
                return true;
            } else {
                System.out.println("File already exists.");
                return false;
            }
 
        } catch (IOException e) {
            return false;
        }
    }
    
    public boolean  ReadFileConfig(){
        try {
            ObjectInputStream ois;
            try (FileInputStream fis = new FileInputStream("C:\\ExpectedCoinMiner\\config.bin")) {
                ois = new ObjectInputStream(fis);
                //Bước 2: Đọc dữ liệu
                config = (Config) ois.readObject();
            }
          ois.close();
          return true;
        } catch (IOException | ClassNotFoundException ex) {
          return false;
       } 
    }
    
    public boolean WriteFileConfig(Config config){
        try {
            ObjectOutputStream oos;
            //out = Files.newOutputStream(path, CREATE, APPEND);
            try (FileOutputStream fos = new FileOutputStream("C:\\ExpectedCoinMiner\\config.bin")) {
                //out = Files.newOutputStream(path, CREATE, APPEND);
                oos = new ObjectOutputStream(fos);
                // Ghi dối tượng config vào file
                oos.writeObject(config);
                // Đóng luồng
            }
            oos.close();
            return true;
        } catch (IOException ex) {
            return false;
        }        
    }
    public boolean WriteFileBlockchain(Block[] blocks, String place){
        try {
            ObjectOutputStream oos;
            //out = Files.newOutputStream(path, CREATE, APPEND);
            try (FileOutputStream fos = new FileOutputStream(place+"Blocks.bin")) {
                //out = Files.newOutputStream(path, CREATE, APPEND);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(blocks);
                //Bước 3: Đóng luồng
            }
            oos.close();
            return true;
       } catch (IOException ex) {
            System.out.println(ex);
         return false;
       }        
    }
    public boolean WriteBlockToFileTopBlock(Block block, String place){
        try {
            ObjectOutputStream oos;
            //out = Files.newOutputStream(path, CREATE, APPEND);
            try (FileOutputStream fos = new FileOutputStream(place+"TopBlock.bin")) {
                //out = Files.newOutputStream(path, CREATE, APPEND);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(block);
                //Bước 3: Đóng luồng
            }
            oos.close();
            return true;
       } catch (IOException ex) {
         return false;
       }        
    }    
    public boolean ReadBlockFromFileTopBlock(String place){
        try {
            ObjectInputStream ois;
            try (FileInputStream fis = new FileInputStream(place+"TopBlock.bin")) {
                ois = new ObjectInputStream(fis);
                TopBlock =  (Block) ois.readObject();
            }
            ois.close();
            return true;
        } catch (IOException | ClassNotFoundException ex) {
            return false;
        }          
    }
            
    public boolean ReadFileBlockChain(String place){
        try {
            ObjectInputStream ois;
            try (FileInputStream fis = new FileInputStream(place+"Blocks.bin")) {
                ois = new ObjectInputStream(fis);
                blocks = (Block[]) ois.readObject();
            }
            ois.close();
            return true;
        } catch (IOException | ClassNotFoundException ex) {
            return false;
        }               
    }
    public String readFile(String path){
        String re="";
        FileReader fr;
        try {
            fr = new FileReader(path);
            int i;
            while ((i = fr.read()) != -1) {
                System.out.print((char) i);
                re+=String.valueOf((char) i);
            }
            fr.close();       
            return re;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HandlerFile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HandlerFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return re;
    }    
    public Block[] getBlocks() {
        return blocks;
    }
    public Config getConfig() {
        return config;
    }

    public Block getTopBlock() {
        return TopBlock;
    }
    
}
