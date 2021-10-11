package com.ruoyi.YDOnlineTaxi.utils;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public final class RSAEncrypt {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥
    public static void main(String[] args) throws Exception {
        //生成公钥和私钥
//        genKeyPair();
        //加密字符串
        String message = "170976fa8a46ce9a6e8";
        String messageEn = encrypt(message);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = decrypt("aJS1NGnhsVPobILiJhsF2FBhazsL65t/Mnz2mARvF+Q/X+1qai2WPMMibt4AGBMoUoaM3FFrOiP90UaUTY8ks+yacxpVfXp4s+WKJA0aS7+ZHqMOxE+6kIajUK/ji9CIswVGLCJik5SKRrs5HBo25KrcsGmX2j5KxNcrlz464zg=");
        System.out.println("还原后的字符串为:" + messageDe);
        System.out.println(messageEn.length());;
    }

    /**
     * 随机生成密钥对
     * @throws NoSuchAlgorithmException
     */
//    public static void genKeyPair() throws NoSuchAlgorithmException {
//        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
//        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//        // 初始化密钥对生成器，密钥大小为96-1024位
//        keyPairGen.initialize(1024,new SecureRandom());
//        // 生成一个密钥对，保存在keyPair中
//        KeyPair keyPair = keyPairGen.generateKeyPair();
//        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
//        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
//        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
//        // 得到私钥字符串
//        String privateKeyString = new String(Base64.encodeBase64((privateKey.getEncoded())));
//        // 将公钥和私钥保存到Map
//        keyMap.put(0,publicKeyString);  //0表示公钥
//        keyMap.put(1,privateKeyString);  //1表示私钥
//    }

    /**
     * RSA公钥加密
     *
     * @param str 加密字符串
     *            公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64.decodeBase64("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCC54m5jUbsqnNPxZ63SU6f8+JCue0LfkbVbgV4JRWNO1K/6FuOZQ7QG/vwKlx0y8KY+/oqLnIOjrOE6sIrK9gBnSxP6430n8Ia2R9GaDEmyMMcGRLJlDVLARGyFRo9I04+9XUHTSlrBUJ5U4RQA6dXc6Dhy9Ia+++GjluPnPGqewIDAQAB");
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str 加密字符串
     *            私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        //base64编码的私钥
        byte[] decoded = Base64.decodeBase64("MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAILnibmNRuyqc0/FnrdJTp/z4kK57Qt+RtVuBXglFY07Ur/oW45lDtAb+/AqXHTLwpj7+ioucg6Os4Tqwisr2AGdLE/rjfSfwhrZH0ZoMSbIwxwZEsmUNUsBEbIVGj0jTj71dQdNKWsFQnlThFADp1dzoOHL0hr774aOW4+c8ap7AgMBAAECgYA8ozCDCVRvUM21iB9rW8PWCE0pB6MsjcQWHa7e5p7DG1XLm8C9h0aDVFMx/w1w9Oohn6dUXstqvuOdkHl9MgQOoU2jwP+yDR78QddGRrbGpE4SHWdPPFn9I0+CyryXVUF+9n2MnxSMYWyOjhJZhRKLNcXeUQJsXCvaLznXTuTPgQJBAMSj6wDrdKkI9WaW49vFixix/0ob/LLWnMxqjEQDDdM0zelfOvK+udLM1aG7rmLm5KizPrDjbhQgOIz1uQGFVRsCQQCqa6WKFb/K3UtJUVGDuc02XC1KVuCMW52Dy/sciOQLOx613WhnSsVAFDD4fiPrBsZXW39ky+vO9xqkJhUjSjYhAkEArQbba3TDs1VpsfrFxwVc7r75NvnHcon7cLe0csgJKzaElXwxP5Xppv6MYILn4KjwvSzz1exZWSY8/HPfdeIfzwJATy2+dr6OIoHYhdL7TXlZB+WhwUVGBNBYCbEPFX3gfftU1GhvJnaN2KzI59srcvnt0w6WzM0V3U3fmd57ua60IQJBALIs9hgeGCqXuGGzrBYdh2sSqM3Bcd5CXQjz/zsUuvfxKxRKMSXqAbvWV2YO5tLTRg7d/PfJ8jSy/9jRsJ4Zew4=");
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }

    public static Map<Integer, String> getKeyMap() {
        return keyMap;
    }

    public static void setKeyMap(Map<Integer, String> keyMap) {
        RSAEncrypt.keyMap = keyMap;
    }
}

