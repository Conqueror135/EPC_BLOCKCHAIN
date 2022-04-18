/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package common;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author duythang
 */

public class HandlerLocation {
    public static String getCurrentLocation(){
        Path CRP = Paths.get("");
        return CRP.toAbsolutePath().toString();
    }
}
