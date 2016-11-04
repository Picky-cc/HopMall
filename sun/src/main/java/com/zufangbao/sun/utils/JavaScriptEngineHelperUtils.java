package com.zufangbao.sun.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.springframework.core.io.ClassPathResource;

/**
 * java脚本工具
 * @author wukai
 *
 */
public class JavaScriptEngineHelperUtils {
	
	private static final String DEFAULT_SCRIPT_NAME = "JavaScript";
	
	public static ScriptEngine getScriptEngine(){
		
		return getScriptEngine(DEFAULT_SCRIPT_NAME);
	}
	public static Invocable getInvocable(String script) throws ScriptException{
		
		return getInvocables(script);
	}
	public static Invocable getInvocableViaClassPath(String fileName) throws ScriptException, IOException{
		
		return getInvocableViaClassPaths(fileName);
	}
	public static Invocable getInvocableViaClassPaths(String... fileNames) throws ScriptException, IOException{
		
		ScriptEngine scriptEngine = getScriptEngine();
		
		for(String item : fileNames){
			
			scriptEngine.eval(getReader(item));
			
		}
		return (Invocable) scriptEngine;
	}
	private static Reader getReader(String classpathFileName) throws IOException{
		
		return new InputStreamReader(new ClassPathResource(classpathFileName).getInputStream());
	}
	public static Invocable getInvocables(String... scripts) throws ScriptException{
		
		ScriptEngine scriptEngine = getScriptEngine();
		
		for(String item : scripts){
			
			scriptEngine.eval(item);
			
		}
		return (Invocable) scriptEngine;
	}
	private static ScriptEngine getScriptEngine(String scriptName){
		
		ScriptEngineManager scriptEngineFactory = new ScriptEngineManager();
		
		return scriptEngineFactory.getEngineByName(scriptName);
	}
}
