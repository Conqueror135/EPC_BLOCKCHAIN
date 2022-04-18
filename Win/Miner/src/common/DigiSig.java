/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package common;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import org.json.JSONException;
import org.json.JSONObject;
/**
 *
 * @author Admin
 */
public class DigiSig {
    private static final String SPEC = "secp256k1";
    private static final String ALGO = "SHA256withECDSA";
    private PublicKey PublicKey;
    private PrivateKey PrivateKey;
    
    public boolean generateKeys(){
        try {
            ECGenParameterSpec ecSpec = new ECGenParameterSpec(SPEC);
            KeyPairGenerator g = KeyPairGenerator.getInstance("EC");
            g.initialize(ecSpec, new SecureRandom());
            KeyPair keypair = g.generateKeyPair();
            PublicKey = keypair.getPublic();        
            PrivateKey = keypair.getPrivate();
            return true;
        } catch (NoSuchAlgorithmException ex) {
            return false;
        } catch (InvalidAlgorithmParameterException ex) {
            return false;
        }
    }
    static public  JSONObject Sig(String publicKey, String privateKey, String plaintext) throws NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, SignatureException, JSONException, InvalidKeySpecException {
        PKCS8EncodedKeySpec formatted_private = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKey));

        KeyFactory kf = KeyFactory.getInstance("EC");
        PrivateKey priv = kf.generatePrivate(formatted_private);
        //...... sign
        Signature ecdsaSign = Signature.getInstance(ALGO);
        ecdsaSign.initSign(priv);
        ecdsaSign.update(plaintext.getBytes("UTF-8"));
        byte[] signature = ecdsaSign.sign();
        String sig = Base64.getEncoder().encodeToString(signature);

        JSONObject obj = new JSONObject();
        obj.put("publicKey", publicKey);
        obj.put("signature", sig);
        obj.put("algorithm", ALGO);

        return obj;
    }
    static public String getSignature(JSONObject Signature) throws JSONException{
        return Signature.getString("signature");
    }
    static public boolean Verify(JSONObject obj, String plainText) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException, UnsupportedEncodingException, SignatureException, JSONException {

        Signature ecdsaVerify = Signature.getInstance(obj.getString("algorithm"));
        KeyFactory kf = KeyFactory.getInstance("EC");

        EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(obj.getString("publicKey")));

        KeyFactory keyFactory = KeyFactory.getInstance("EC");
        PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);

        ecdsaVerify.initVerify(publicKey);
        ecdsaVerify.update(plainText.getBytes("UTF-8"));
        boolean result = ecdsaVerify.verify(Base64.getDecoder().decode(obj.getString("signature")));

        return result;
    }

    public String getPublicKey() {
        return Base64.getEncoder().encodeToString(PublicKey.getEncoded());
    }

    public String getPrivateKey() {
        return Base64.getEncoder().encodeToString(PrivateKey.getEncoded());
    }
     public static void main(String[] args) throws JSONException{
         DigiSig dig = new DigiSig();
         if(dig.generateKeys()){
             System.out.println("ok");
             System.out.println(dig.getPublicKey());
             System.out.println(dig.getPrivateKey());
         }
//        try {
//            DigiSig digiSig = new DigiSig();
//            if(digiSig.generateKeys()){
//                
//                JSONObject sig = digiSig.Sig(digiSig.getPublicKey(),digiSig.getPrivateKey(), "ThangThanThanh");
//                boolean result = digiSig.Verify(sig,"ThangThanThanh");
//                System.out.println(result);                
//            }
//
//        } catch (NoSuchAlgorithmException ex) {
//            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidAlgorithmParameterException ex) {
//            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidKeyException ex) {
//            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SignatureException ex) {
//            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (InvalidKeySpecException ex) {
//            Logger.getLogger(DigiSig.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
//    
}
