/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.relpro.javascript;

import java.io.InputStream;
import java.io.InputStreamReader;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import jdk.nashorn.api.scripting.ScriptObjectMirror;

public class JS {
    
    public void execute() throws ScriptException{
        ScriptEngine js = new ScriptEngineManager().getEngineByName("javascript");
        Bindings bindings = js.getBindings(ScriptContext.ENGINE_SCOPE);
        bindings.put("stdout", System.out);
        
        js.eval(new InputStreamReader(JS.class.getResourceAsStream("/utils.js")));
        js.eval(new InputStreamReader(JS.class.getResourceAsStream("/functions.js")));
        
        System.out.println(((ScriptObjectMirror) js.get("sumOnUtils")).call(null, 1, 1));
        System.out.println(((ScriptObjectMirror) js.get("sumOnFunctions")).call(null, 2, 2));
    }

    public static void main(String args[]) throws Exception {
        JS js = new JS();
        js.execute();
    }
}   
