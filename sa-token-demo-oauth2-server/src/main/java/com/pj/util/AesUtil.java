package com.pj.util;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Classname ZzSecurityHelper
 * @Description TODO
 * @Date 2019/6/24 16:50
 * @Created by whd
 */
public class AesUtil {
    /*
     * 加密用的Key 可以用26个字母和数字组成 使用AES-128-CBC加密模式，key需要为16位。
     */
    private static final String key="622542c04edaee86";
    private static final String iv ="11dfe94cc210acec";
    /**
     * @author miracle.qu
     * @Description AES算法加密明文
     * @param data 明文
     * @return 密文
     */
    public static String encryptAES(String data) throws Exception {
        try {
            //构建实例
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = data.getBytes();
            int plaintextLength = dataBytes.length;

            //是否为固定块的整数倍，若不是则拓展数组长度
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            //复制数组数据
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);

            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES"); // 密钥
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());  // CBC模式，需要一个向量iv，可增加加密算法的强度
            //初始化
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);
            //加密
            byte[] encrypted = cipher.doFinal(plaintext);

            return AesUtil.encode(encrypted).trim(); // BASE64做转码。

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @author miracle.qu
     * @Description AES算法解密密文
     * @param data 密文
     * @return 明文
     */
    public static String decryptAES(String data) throws Exception {
        try
        {
            byte[] encrypted1 = AesUtil.decode(data);//先用base64解密

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keyspec = new SecretKeySpec(key.getBytes(), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(iv.getBytes());

            cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

            byte[] original = cipher.doFinal(encrypted1);
            String originalString = new String(original);
            return originalString.trim();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 编码
     * @param byteArray
     * @return
     */
    public static String encode(byte[] byteArray) {
        return new String(Base64.getEncoder().encode(byteArray));
    }

    /**
     * 解码
     * @param base64EncodedString
     * @return
     */
    public static byte[] decode(String base64EncodedString) {
        return Base64.getDecoder().decode(base64EncodedString);
    }

    public static List<String> contain(List<String> words ,String content){
        List<String> res = new ArrayList<>();
        for (String word : words) {
            //TODO 生成正则表达式
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("^");
            for (char c : word.toCharArray()) {
                StringBuilder temp = new StringBuilder("(?=.*" + c + ")");
                stringBuilder.append(temp);
            }
            String pattern = stringBuilder.toString() + ".*$";

            //TODO 匹配
            Pattern compile = Pattern.compile(pattern);
            Matcher matcher = compile.matcher(content);
            if (matcher.find()){
                res.add(matcher.group());
            }
        }
        return res;
    }


    public static void main(String[] args) throws Exception {
//        System.out.println(AesUtil.decryptAES("wgxdvQkHRtSfdk7D1LzB8FvyfXPbIqXZKHC7ZB8UK/U="));
//        String str = AesUtil.encryptAES("ruifox");
//        System.out.println(str);
//        System.out.println(AesUtil.decryptAES(str));
        System.out.println(AesUtil.decryptAES("XTAuDDD8Tk24tiIfChGdvOiojaCiWfEwW5+9FoLee7I="));
    }

}
