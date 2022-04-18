/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

/**
 *
 * @author Admin
 */
public class TestFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        HandlerFile  hf = new HandlerFile();
//        hf.setUrlOut("d:/mydata.bin");
//        hf.initWriter();
    
//        hf.setUrlIn("d:/mydata.bin");
//        hf.Reader();
          hf.createNewFile("d:/mydata3.bin");
    }
    
}
