/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author hp
 */
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.spec.DESKeySpec;

/**
 * Clase de encriptacion y desencripracion de claves
 * @author hp
 */
public class Decrypt {

    /**
     * Clave previa
     */
    public final static String KEY = "RUYUKEY1";
    private final static String DES = "DES";
    private final static String IV = "RUYUKEY2";
    private final static String CHARSET = "UTF-8";
    private final static String CBC = "DES/CBC/PKCS5Padding";

    /**
     * La descripción está encriptada según el valor de la clave
     *
     * @param data Matriz de bytes de clave de cifrado de clave @param
     * @param key Clave previa
     * @return Una clave o key encriptada
     */
    public String encodeString(String data, String key) {
        byte[] bt = null;
        try {
            bt = encrypt(data.getBytes(CHARSET), key.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(Base64.getEncoder().encode(bt));
    }

    /**
     * Descripción Descifrar según el valor de la clave
     *
     * @param data Matriz de bytes de clave de cifrado de clave @param
     * @param key Clave previa que sera encriptada
     * @return Clave encriptada
     */
    public String decodeString(String data, String key) {
        if (data == null) {
            return null;
        }
        byte[] bt = null;
        try {
            byte[] buf = Base64.getDecoder().decode(data.getBytes());
            bt = decrypt(buf, key.getBytes());
            return new String(bt, CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * La descripción está encriptada según el valor de la clave
     *
     * @param data Matriz de bytes de clave de cifrado de clave @param
     * @return Clave encriptada
     * @throws Exception Excepcion
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // Crea un objeto DESKeySpec a partir de los datos clave originales
        DESKeySpec dks = new DESKeySpec(key);
        // Cree una fábrica de claves y luego úsela para convertir DESKeySpec en objetos SecretKey
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(CBC);
        IvParameterSpec iv2 = new IvParameterSpec(IV.getBytes(CHARSET));
        cipher.init(Cipher.ENCRYPT_MODE, securekey, iv2);
        return cipher.doFinal(data);
    }

    /**
     * Descripción Descifrar según el valor de la clave
     *
     * @param data Matriz de bytes de clave de cifrado de clave @param
     * @return byte[] arreglo de bytes
     * @throws Exception
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // Crea un objeto DESKeySpec a partir de los datos clave originales
        DESKeySpec dks = new DESKeySpec(key);
        // Cree una fábrica de claves y luego úsela para convertir DESKeySpec en objetos SecretKey
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey securekey = keyFactory.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(CBC);
        IvParameterSpec iv2 = new IvParameterSpec(IV.getBytes(CHARSET));
        cipher.init(Cipher.DECRYPT_MODE, securekey, iv2);
        return cipher.doFinal(data);
    }

//    public static void main(String[] args) {
//        String dec=Decrypt.encodeString("TestPassword123", Decrypt.KEY);
//        System.out.println(dec);
//        System.out.println(Decrypt.decodeString(dec, Decrypt.KEY));
//    }
}
