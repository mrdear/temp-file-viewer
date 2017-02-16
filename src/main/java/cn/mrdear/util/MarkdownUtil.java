package cn.mrdear.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;

/**
 * @author Niu Li
 * @since 2017/2/16
 */
public class MarkdownUtil {

    private static StringBuilder md2html = new StringBuilder();

    private static ScriptEngineManager engineMananger = new ScriptEngineManager();

    static {
        try {
        InputStream in = MarkdownUtil.class.getClassLoader().getResourceAsStream("static/js/md2htmlJava.js");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = reader.readLine()) != null){
            md2html.append(line);
        }
        in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws ScriptException, NoSuchMethodException, FileNotFoundException {
        String path = MarkdownUtil.class.getClassLoader().getResource("").getPath();
        ScriptEngine engine = engineMananger.getEngineByName("nashorn");
        engine.eval(new FileReader(path+"/static/js/md2htmlJava.js"));
        Invocable inv = (Invocable) engine;
        Object object = engine.get("Markdown.Converter");
//        String result = (String) inv.invokeFunction("md2Html","[toc]  hello, **leanote**");
        String result = (String) inv.invokeMethod(object,"makeHtml","[toc]  hello, **leanote**");
        System.out.println(result);
    }

}
