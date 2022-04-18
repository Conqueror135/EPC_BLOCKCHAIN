/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Admin
 */
public class HandlerLocation {
    public static String getCurrentLocation(){
        Path CRP = Paths.get("");
        return CRP.toAbsolutePath().toString();
    }
}
