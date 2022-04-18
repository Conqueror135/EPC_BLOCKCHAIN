/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package system;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class Config implements Serializable {
    private String AddressWallet; // Dia chi vi nhan thuong
    private String Password; //  Mat khau de truy cap vao phan mem neu can
    private boolean IsWalletReady; // trang thai cua wallet da co dia chi vi hay chua

    public Config(String AddressWallet, String Password, boolean IsBlockchainReady) {
        this.AddressWallet = AddressWallet;
        this.Password = Password;
        this.IsWalletReady = IsWalletReady;
    }

    public String getAddressWallet() {
        return AddressWallet;
    }

    public String getPassword() {
        return Password;
    }

    public boolean getIsWalletReady() {
        return IsWalletReady;
    }

    public void setIsWalletReady(boolean IsWalletReady) {
        this.IsWalletReady = IsWalletReady;
    }
    
}
