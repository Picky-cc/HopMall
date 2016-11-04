/**
 * 
 */
package com.suidifu.hathaway.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;

import org.apache.commons.codec.binary.Base64;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;

/**
 * @author wukai
 *
 */
public class KryoUtils {
	
	private static Kryo buildKryo(){
		
		Kryo kryo = new Kryo();
		
        kryo.setReferences(false);
        
        return kryo;
	}

	public static Object readClassAndObject(String kryoStr,Type[] types){
		
		Kryo kryo = buildKryo();
        
        for(Type type:types)
        {
        	kryo.register(type.getClass(), new JavaSerializer());
        }
 
        ByteArrayInputStream bais = new ByteArrayInputStream(new Base64().decode(kryoStr));
       
        Input input = new Input(bais);
        
        return  kryo.readClassAndObject(input);
	  
	}
	public static Object readClassAndObject(String kryoStr,Class<?> clazz){
		
		return readClassAndObject(kryoStr, new Type[]{clazz});
	}
	
	public static Object readClassAndObject(String kryoStr,Type type){
		
		return readClassAndObject(kryoStr, new Type[]{type});
		
	}
	public static String writeClassAndObject(Object object){
		
		if(null == object){
			return null;
		}
		
		Kryo kryo = buildKryo();
		
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Output output = new Output(baos);
        kryo.writeClassAndObject(output, object);
        output.flush();
        output.close();
 
        byte[] buffer = baos.toByteArray();
        try {
            baos.flush();
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(new Base64().encode(buffer));
	}
}
