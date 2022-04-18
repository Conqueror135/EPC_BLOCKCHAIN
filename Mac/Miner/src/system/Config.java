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
    private String LocationSaveBlockchain; // vi tri luu file chua blockchain
    private String RewardAddressWallet; // Dia chi vi nhan thuong
    private String Password; //  Mat khau de truy cap vao phan mem neu can
    private boolean IsBlockchainReady; // trang thai cua blockchain da co du lieu hay chua

    public Config(String LocationSaveBlockchain, String RewardAddressWallet, String Password, boolean IsBlockchainReady) {
        this.LocationSaveBlockchain = LocationSaveBlockchain;
        this.RewardAddressWallet = RewardAddressWallet;
        this.Password = Password;
        this.IsBlockchainReady = IsBlockchainReady;
    }

    public String getLocationSaveBlockchain() {
        return LocationSaveBlockchain;
    }

    public String getRewardAddressWallet() {
        return RewardAddressWallet;
    }

    public String getPassword() {
        return Password;
    }

    public boolean isIsBlockchainReady() {
        return IsBlockchainReady;
    }

    public void setIsBlockchainReady(boolean IsBlockchainReady) {
        this.IsBlockchainReady = IsBlockchainReady;
    }
    
}
