/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 *
 * @author Admin
 */
public class TestFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SocketException {
        // TODO code application logic here
        Enumeration e = NetworkInterface.getNetworkInterfaces();
        while(e.hasMoreElements())
        {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements())
            {
                InetAddress i = (InetAddress) ee.nextElement();
                if(!i.getHostAddress().contains(":")){
                    System.out.println(i.getHostAddress());
                }
                
            }
        }
    } 
}
