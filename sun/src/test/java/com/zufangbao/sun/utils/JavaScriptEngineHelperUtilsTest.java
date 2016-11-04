/**
 * 
 */
package com.zufangbao.sun.utils;

import java.io.File;
import java.io.IOException;

import javax.script.Bindings;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author wukai
 *
 */
public class JavaScriptEngineHelperUtilsTest {
	
	private int ERR_CODE = -1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testScriptVars() throws ScriptException{
		
		ScriptEngineManager manager = new ScriptEngineManager();
		
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		
		File file = new File("scriptVars.txt");
		
		engine.put("file", file);
		
		engine.eval("print(file.getAbsolutePath())");
	}
	@Test
	public void testInvokeScriptMethod() throws ScriptException, NoSuchMethodException{
		
		ScriptEngineManager manager = new ScriptEngineManager();
		
		ScriptEngine engine = manager.getEngineByName("javascript");
		
		String script = "var obj = new Object();obj.hello=function(name){print('hi '+name);};";
		
		engine.eval(script);
		
		Invocable invocable = (Invocable) engine;
		
		Object obj = engine.get("obj");
		
		invocable.invokeMethod(obj,"hello","wukai");
	}
	@Test
	public void testRunnableImpl() throws ScriptException{
		
		ScriptEngineManager manager = new ScriptEngineManager();
		
		String script = "function run(){print('i am running');}"	;
		
		ScriptEngine engine = manager.getEngineByName("javascript");
		
		engine.eval(script);
		
		Invocable invocable = (Invocable) engine;
		
		Runnable runnable = invocable.getInterface(Runnable.class);
		
		Thread thread = new Thread(runnable);
		
		thread.start();
		
		System.out.println("hi i am main thread");
		
	}
	@Test
	public void testMultiScopes() throws ScriptException{
		
		ScriptEngineManager manager = new ScriptEngineManager();
		
		ScriptEngine scriptEngine = manager.getEngineByName("javascript");
		
		scriptEngine.put("x", "y");
		
		scriptEngine.eval("print(x)");
		
		ScriptContext newContext = new SimpleScriptContext();
		
		Bindings newScriptScope = newContext.getBindings(ScriptContext.ENGINE_SCOPE)
				;
		newScriptScope.put("x", "yy");
		
		scriptEngine.eval("print(x)",newContext);
	}
	
	/**
	 * Test method for {@link com.zufangbao.sun.utils.JavaScriptEngineHelperUtils#getScriptEngine()}.
	 * @throws ScriptException 
	 * @throws NoSuchMethodException 
	 * @throws IOException 
	 */
	@Test
	public void testGetScriptEngine() throws ScriptException, NoSuchMethodException, IOException {
		
		Invocable invocable = JavaScriptEngineHelperUtils.getInvocableViaClassPath("test/quantum/testScriptEngine.js");
		
//		invocable.invokeFunction("", args)
		
	}
	
	
	
	
	
}
