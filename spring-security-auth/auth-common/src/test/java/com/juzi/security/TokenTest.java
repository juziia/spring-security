package com.juzi.security;


import com.juzi.security.utils.RsaUtils;

public class TokenTest {

    public static void main(String[] args) throws Exception {
        //RsaUtils.generateKey("D:/auth-key/rsa_key.pub","D:/auth-key/rsa_pri_key","jzuiia",2048);
        String format = new String(RsaUtils.getPublicKey("D:/auth-key/rsa_key.pub").getEncoded(),"ISO8859-1");
        String format1 = new String(RsaUtils.getPrivateKey("D:/auth-key/rsa_pri_key").getEncoded(),"ISO8859-1");
        System.out.println(format);
        System.out.println(format1);


    }
}
