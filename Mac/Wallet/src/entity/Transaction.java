/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Arrays;
import mncoin.TransactionInput;
import mncoin.TransactionOutput;

/**
 *
 * @author Admin
 */
public class Transaction implements Serializable{
    private String Sender;// Dia chi vi cua nguoi gui
    private String Receiver;// Dia chi vi cua nguoi nhan
    private String CreateTime;
    private String Signature;
    private float value;// So luong coin gui di
    private TransactionOutput[] Outputs;

    public Transaction(String Sender, String Receiver, String CreateTime, String Signature, float value, TransactionOutput[] Outputs) {
        this.Sender = Sender;
        this.Receiver = Receiver;
        this.CreateTime = CreateTime;
        this.Signature = Signature;
        this.value = value;
        this.Outputs = Outputs;
    }

    public String getSender() {
        return Sender;
    }

    public String getReceiver() {
        return Receiver;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public String getSignature() {
        return Signature;
    }

    public float getValue() {
        return value;
    }

    public TransactionOutput[] getOutputs() {
        return Outputs;
    }

    @Override
    public String toString(){
        String out="";
        for(TransactionOutput output: Outputs){
            out+= output.toString();
        }
        return Sender+"_"+Receiver+"_"+CreateTime+"_"+Signature+"_"+value+"_"+out;
    }
}