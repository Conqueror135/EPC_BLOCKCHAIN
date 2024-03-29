/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miner.GUI;

import PeerToPeer.client.ImpClient;
import common.HandlerFile;
import entity.Block;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import system.Config;

/**
 *
 * @author Admin
 */
public class Process extends javax.swing.JFrame {

    /**
     * Creates new form Process
     */
    private ArrayList<ImpClient> OtherPeers;
    private Block[] blocks;
    private int currentIndex; // vi tri cua block cuoi da tai dc
    private String LocationSaveBlockchain;
    HandlerFile hf = new HandlerFile();
    
    public Process( ArrayList<ImpClient> OtherPeers, String LocationSaveBlockchain) {
        this.OtherPeers = OtherPeers;
        this.LocationSaveBlockchain = LocationSaveBlockchain;
        this.currentIndex =0;
        initComponents();
    //    dowloadBlockchain();
    }
    public void dowloadBlockchain(){
        for(int i= 0; i<OtherPeers.size();i++){
            try {
                int re = OtherPeers.get(i).checkStatusReadyToDowloadBlockchain();
                if(re > -2){
                    if(re == -1){
                        if(OtherPeers.get(i).requestOtherPeerGetBlockFromFile()){
                           int numBlocks = OtherPeers.get(i).getNumberOfBlocksFromOtherPeer();
                           blocks = new Block[numBlocks];
                           if(numBlocks > 0){
                               for(int j=currentIndex; j<numBlocks;j++){
                                blocks[currentIndex]=  OtherPeers.get(i).getBlockFromOtherPeers(j);  
                                double value = ((j+1)*1.0*100)/numBlocks;
                                int k = (int) value;
                                processDownloadBlockChain.setValue(k);
                                currentIndex++;
                               }
                               break;
                           }                           
                        }
                    }else if(re == 1){
                        int numBlocks = OtherPeers.get(i).getNumberOfBlocksFromOtherPeer();
                        if(numBlocks > 0){
                            blocks = new Block[numBlocks];
                            for(int j=currentIndex; j<numBlocks;j++){
                                blocks[currentIndex]=  OtherPeers.get(i).getBlockFromOtherPeers(j);
                                double value = ((j+1)*1.0*100)/numBlocks;
                                int k = (int) value;
                                processDownloadBlockChain.setValue(k);
                                currentIndex++;
                            }
                            break;
                        }                        
                        break;
                    }
                }
            } catch (RemoteException ex) {
                
            }
        }
        if(hf.WriteFileBlockchain(blocks, LocationSaveBlockchain) && hf.WriteBlockToFileTopBlock(blocks[blocks.length-1], LocationSaveBlockchain)){
            if(hf.ReadFileConfig()){
                Config newConfig = hf.getConfig();
                newConfig.setIsBlockchainReady(true);
                hf.WriteFileConfig(newConfig);
            }
            btnNext.setEnabled(true);  
            JOptionPane.showMessageDialog(null,"Cài đặt thành công, hãy khởi động lại chương trình!","Success!",JOptionPane.INFORMATION_MESSAGE);
                  
        }else{
            JOptionPane.showMessageDialog(null,"Đã xãy ra lỗi lưu trữ!","Lỗi!",JOptionPane.ERROR_MESSAGE);
        }
            
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        processDownloadBlockChain = new javax.swing.JProgressBar();
        jLabel1 = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ExpectedCoin Miner");
        setBackground(new java.awt.Color(255, 0, 51));

        processDownloadBlockChain.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 204));
        jLabel1.setText("Đang tải bản sao của Blockchain");

        btnNext.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnNext.setText("Reset");
        btnNext.setEnabled(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton2.setText("‹ Back");
        jButton2.setToolTipText("");

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButton3.setText("Cancel ›");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 167, Short.MAX_VALUE)
                .addComponent(jButton2)
                .addGap(86, 86, 86)
                .addComponent(btnNext)
                .addGap(84, 84, 84)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(processDownloadBlockChain, javax.swing.GroupLayout.PREFERRED_SIZE, 684, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(processDownloadBlockChain, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 109, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNext)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(48, 48, 48))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
            this.setVisible(false);
            this.dispose();
    }//GEN-LAST:event_btnNextActionPerformed

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Process.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Process.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Process.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Process.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Process().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JProgressBar processDownloadBlockChain;
    // End of variables declaration//GEN-END:variables
}
