
import jdk.nashorn.internal.parser.Token;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

public class Lox {

    static boolean hadError = false;
    public static void main(String[] args) throws IOException{
        if(args.length > 1){
            System.out.println("Usage: jlox [script]");
            System.exit(64);
        } else if (args.length == 1) {
            runFile(args[0]);
        }else {
            runPrompt();
        }
    }

    private static void runFile(String path) throws IOException{
        byte[] bytes = File.readAllBytes(Paths.get(path));
        run(new String(bytes, Charset.defaultCharset()));
    }

    // REPL:Read-Evaluate-Print-Loop 交互式解释器
    private static void runPrompt() throws IOException{
        InputStreamReader input = new InputStreamReader();
        BufferedReader reader = new BufferedReader();

        for (;;){
            System.out.println("> ");
            String line = reader.readLine();
            if(line == null)    break;
            run(line);
            hadError = false;
        }
    }

    public static void run(String source){
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scanTokens();
        // token: 标识（词法单元）

        // print the tokens
        for (Token token: tokens){
            System.out.println(token);
        }
    }

    // 错误处理
    static void error(int line, String message){
        report(line, "", message);
    }

    public static void report(int line, String where, String message){
        System.err.println("[line " + line + "] Error " + where + ": " + message);
        hadError = true;
    }



}
