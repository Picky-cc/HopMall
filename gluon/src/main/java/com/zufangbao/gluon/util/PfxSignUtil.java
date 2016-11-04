package com.zufangbao.gluon.util;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;
import java.util.Base64;
import java.util.Enumeration;

import com.gnete.bc.jce.provider.BouncyCastleProvider;

/**
 * pfx公私钥，工具类
 * @author zhanghongbing
 *
 */
public class PfxSignUtil {

	/**
	 * pfx签名
	 * @param pfxFile pfx文件路径
	 * @param pfxPwd pfx密码
	 * @param data 待签名字符串
	 * @param signature 签名算法
	 * @param coding 编码
	 * @return
	 */
	public static String sign(String pfxFile, String pfxPwd, String data, String signature, String coding) {
	    try {
	        //获取pfx文件的prikey
	        Security.addProvider(new BouncyCastleProvider());
	        KeyStore e = KeyStore.getInstance("PKCS12");
	        FileInputStream fis = new FileInputStream(pfxFile);
	        e.load(fis, pfxPwd.toCharArray());
	        Enumeration<String> aliases = e.aliases();
	        String keyAlias = null;
	        PrivateKey priKey = null;
	        if (aliases != null) {
	            while (aliases.hasMoreElements()) {
	                keyAlias = (String) aliases.nextElement();
	                priKey = (PrivateKey) e.getKey(keyAlias, pfxPwd.toCharArray());
	                if (priKey != null) {
	                    break;
	                }
	            }
	        }
	        //使用Signature加密
	        Signature signet = Signature.getInstance(signature);
	        signet.initSign(priKey);
	        signet.update(data.getBytes(coding));
	        byte[] signed = signet.sign();
	        String strSign = new String(Base64.getEncoder().encode(signed));
	        return strSign;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

}
