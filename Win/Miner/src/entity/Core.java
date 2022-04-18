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
public class Core implements Serializable  {
    private String SystemAddress = "MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAET2qhZqxcyFT3nFJ162oWx9W8NHu9xmBRrafdzOeUCLDrGqbxkWJJ+1ZKgFd24wLsQXyEthbWXeE1CKzL5KBUcw=="; // Địa chỉ ví của hệ thống
    private double TotalCoin; // so luong coin co trong he thong con co the thuong cho tho dao// tong so luonng coin co trong he thong la  
    private int Difficult;// so luong so 0 yeu cau cua ma bam
    private double Reward; // phan thuong quy dinh thuong khi mot block dc tao ra
    private double FloorFeeReward; // phi giao dich toi thieu
    private int halving; // so nam de giam reward di 1/2
 
    public Core(double TotalCoin, int Difficult, double Reward, int halving) {
        this.TotalCoin = TotalCoin;
        this.Difficult = Difficult;
        this.Reward = Reward;
        this.halving = halving;
    }  

    public String getSystemAddress() {
        return SystemAddress;
    }

    public double getTotalCoin() {
        return TotalCoin;
    }

    public int getDifficult() {
        return Difficult;
    }

    public double getReward() {
        return Reward;
    }

    public double getFloorFeeReward() {
        return FloorFeeReward;
    }

    public int getHalving() {
        return halving;
    }
    public String toString(){
        return SystemAddress+"_"+String.valueOf(TotalCoin)+"_"+String.valueOf(Difficult)+"_"+String.valueOf(Reward)+"_"+String.valueOf(FloorFeeReward)+"_"+String.valueOf(halving);
    }
}
