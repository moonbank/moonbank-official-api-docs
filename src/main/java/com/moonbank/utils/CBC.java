package com.moonbank.utils;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

//AES算法CBC工作模式
public class CBC {
    public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        String message = "是大眼同学呦！";// 原文信息
        System.out.println("原始信息：" + message);
        byte[] secretKey = "1234567890abcdef1234567890abcdef".getBytes();// 256位密钥（32字节）
        // 加密：
        byte[] data = message.getBytes();
        byte[] encrypted = encrypt(secretKey, data);
        System.out.println("加密内容：" + Base64.getEncoder().encodeToString(encrypted));
        // 解密：
        byte[] decrypted = decrypt(secretKey, encrypted);
        System.out.println("解密内容：" + new String(decrypted));
    }

    // 加密：传入参数密钥和原始内容的字节信息
    public static byte[] encrypt(byte[] secretKey, byte[] input)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // 创建密码对象，需要传入算法名称/工作模式/填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 根据secretKey(密钥)的字节内容，"恢复"秘钥对象
        SecretKey keySpec = new SecretKeySpec(secretKey, "AES");
        // CBC模式需要生成一个16 bytes的initialization vector(IV参数 )
        SecureRandom sr = SecureRandom.getInstanceStrong();
        byte[] iv = sr.generateSeed(16);// 生成16个字节的随机数
        System.out.println("IV参数：" + Arrays.toString(iv));// 输出IV参数
        IvParameterSpec ivps = new IvParameterSpec(iv); // 随机数封装成IvParameterSpec参数对象
        // 初始化秘钥:操作模式、秘钥、IV参数
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivps);
        // 根据原始内容(字节),进行加密
        byte[] data = cipher.doFinal(input);
        return join(iv, data);
    }

    // 解密：传入参数密钥和机密后内容的字节信息
    public static byte[] decrypt(byte[] secretKey, byte[] input)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        // 把input分割成IV和密文
        byte[] iv = new byte[16];
        byte[] data = new byte[input.length - 16];
        System.arraycopy(input, 0, iv, 0, 16);// IV参数
        System.arraycopy(input, 16, data, 0, data.length);// 密文
        System.out.println("IV参数：" + Arrays.toString(iv));// 输出IV参数
        // 创建密码对象，需要传入算法名称/工作模式/填充方式
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 根据secretKey(密钥)的字节内容，"恢复"秘钥对象
        SecretKey keySpec = new SecretKeySpec(secretKey, "AES");
        // 回复IV参数
        IvParameterSpec ivps = new IvParameterSpec(iv);
        // 初始化秘钥:操作模式、秘钥、IV参数
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivps);
        return cipher.doFinal(data);
    }

    // 数组合并
    public static byte[] join(byte[] arr1, byte[] arr2) {
        byte[] arr = new byte[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, arr, 0, arr1.length);
        System.arraycopy(arr2, 0, arr, arr1.length, arr2.length);
        return arr;
    }
}