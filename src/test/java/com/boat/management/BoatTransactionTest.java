package com.boat.management;

import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.junit.Test;

public class BoatTransactionTest {

    @Test
    public void testBoatTransactionJavaScript() throws IOException, ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("nashorn");

        // Load and execute JavaScript file
        try (FileReader reader = new FileReader("src/main/resources/static/js/apiRequests.js")) {
            engine.eval(reader);
        } catch (IOException | ScriptException e) {
            e.printStackTrace();
        }
    }
}