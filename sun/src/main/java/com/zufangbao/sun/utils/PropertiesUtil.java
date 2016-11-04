package com.zufangbao.sun.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class PropertiesUtil {
   
    private static PropertiesUtil properties = null;
   
    public static PropertiesUtil getInstance(){
        if(properties == null){
            properties = new PropertiesUtil();
        }
        return properties;
    }

    public static String getPropertiesValue(String path, String key){
        Properties properties = new Properties();
        InputStreamReader inputStream = null;
        String value = null;
        try {
            inputStream = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(path), "UTF-8");
            properties.load(inputStream);
            if(properties != null && properties.containsKey(key)){
                value = properties.getProperty(key);
            }else{
                value = null;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            properties.clear();
        }
        return value;
    }
}