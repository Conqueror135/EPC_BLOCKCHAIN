/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mncoin;

import common.Calculator;
import entity.Block;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Admin
 */
public class MnCoin {
 
    public static boolean isValidBlock(Block oldBlock, Block newBlock) throws NoSuchAlgorithmException{
        Calculator cal=new Calculator();
        if(Integer.parseInt(newBlock.getIndex()) != Integer.parseInt(oldBlock.getIndex())+1){// kiem tra xem chieu dai cau block moi cos hop le khong
            return false;
        }else if(!newBlock.getPreviousHeaderHash().equals(oldBlock.getHeaderHash())){// kiem tra xem getPreviousHeaderHash co hop le khong
            return false;
        }else if(!cal.calculateMerkleRootHash(newBlock.getTrans()).equals(newBlock.getMerkleRootHash()) ){ // tra xem du lieu giao dich co bi thay doi khong
            return false;
        }
        return true;
    }
    
}
