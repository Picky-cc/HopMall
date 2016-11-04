/**
 * 
 */
package com.suidifu.hathaway.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.util.Assert;

/**
 * @author wukai
 *
 */
public class MockRpcServer {
	
	/**
     * @param args
     *  在eclipse的jre中设置    -DANT_HOME_BIN=/Users/wukai/Software/Ant/apache-ant-1.9.4/bin/
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
    	
    	String arg = "start";
    	
    	if(args != null && args.length != 0){
    		
    		arg = args[0];
    	}
    	
    	String antHomeBin = System.getProperty("ANT_HOME_BIN");
    	
    	Assert.notNull(antHomeBin,"ant home should not empty !");
    	
//    	System.out.println("ant home is : ["+antHomeBin+"]");
    	
    	String cmdArray[] = new String[]
    			{antHomeBin+"ant",arg+"-rpc-server"};
    	
		Process process = Runtime.getRuntime().exec(cmdArray,new String[]{},new File("../berkshire/"));
		
		Thread.sleep(10000);
		
//		String result = waitForProcessEnd(process);
//		
//		System.out.println(result);
    	
    }
    public static void start() throws Exception {
    	
    	long start  = System.currentTimeMillis();
    	
    	System.out.println("begin to start rpc server");
    	
    	main(new String[]{"stop"});
    	main(new String[]{"start"});
    	
    	System.out.println("end to start rpc server,time is "+(System.currentTimeMillis()-start)+" mills");
    }
    public static void stop() throws Exception {
    	
    	long start  = System.currentTimeMillis();
    	
    	System.out.println("begin to stop rpc server");
    	
    	main(new String[]{"stop"});
    	
    	System.out.println("end to stop rpc server,time is "+(System.currentTimeMillis()-start)+" mills");
    }
	public static String waitForProcessEnd(Process process) throws IOException {
		return "result: "+consumeInputStream(process.getInputStream())+",error: "+consumeInputStream(process.getErrorStream());
	}
	private static String consumeInputStream(InputStream input)
			throws IOException {
		
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));  
		
		StringBuffer result = new StringBuffer();
		
		String content = null;  
		
		while ( (content = bufferedReader.readLine()) != null) {  
			
		      try {  
		    	  
		        Thread.sleep(100);  
		        
		        result.append(content);
		      } 
		      catch(Exception e)
		      {
		    	  e.printStackTrace();
		      }
		 }
		return result.toString();
	}
    

}
