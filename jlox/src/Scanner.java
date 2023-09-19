import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Scanner {
    private final String source; // 原始的源代码
    private final List<Token> tokens = new ArrayList<>(); // 列表用于保存扫描时产生的标记

    // 跟踪 Scanner 在 source 中的位置
    private int start = 0; // 指向被扫描词素的第一个字符
    private int current = 0; // 指向当前正在处理的字符
    private int line = 1; // 跟踪 current 所在的源文件行数

    Scanner(String source){
        this.source = source;
    }

    // 遍历代码，添加标记，知道遍历完所有的字符
    List<Token> scanTokens(){
        while(!isAtEnd()){
            // 处在下一个词素的开头
            start = current;
            scanTokens();
        }

        tokens.add(new Token(EOF, "", null, line)); // 在最后附加上 EOF 标记，非必须
        return tokens;
    }

    private void scanTokens(){
        char c = advance();
        switch (c){
            case '(' :
                addToken(LEFT_PAREN);
                break;
            case ')' :
                addToken(RIGHT_PAREN);
                break;
            case '{' :
                addToken(LEFT_BRACE);
                break;
            case '}' :
                addToken(RIGHT_BRACE);
                break;
            case ',' :
                addToken(COMMA);
                break;
            case '.' :
                addToken(DOT);
                break;
            case '-' :
                addToken(MINUS);
                break;
            case '+' :
                addToken(PLUS);
                break;
            case ';' :
                addToken(SEMICOLON);
                break;
            case '*' :
                addToken(STAR);
                break;
            case '!':
                addToken(match('=') ? BANG_EQUAL : BANG);
                break;
            case '=':
                addToken(match('=') ? EQUAL_EQUAL : EQUAL);
                break;
            case '<':
                addToken(match('=') ? LESS_EQUAL : LESS);
                break;
            case '>':
                addToken(match('=') ? GREATER_EQUAL : GREATER);
                break;
            case '/' :
                if(match('/')){
                    // 该行为注释
                    while(peek() != '\n' && !isAtEnd()) advance(); // 继续消费字符直到行位
                }else{
                    addToken(SLASH);
                }
                break;
            case ' ' :
            case '\r' :
            case '\t' :
                // ignore whitespace
                break;
            case '\n' :
                line++;
                break;

            default:
                Lox.error(line, "Unexpected character.");
                break;

        }
    }

    // 类似有条件的 advance()，只有当前字符是正在寻找的字符时才消费
    private boolean match(char expected){
        if(isAtEnd())   return false;
        if(source.charAt(current) != expected)  return false;

        current++;
        return true;
    }

    // lookahead 前瞻，不会消耗字符，只关注当前未消费的字符
    private char peek(){
        if(isAtEnd())   return '\0';
        return source.charAt(current);
    }

    private boolean isAtEnd(){
        return current >= source.length();
    }

    // 处理输入：获取源文件中的下一个字符并返回它
    private char advance(){
        current++;
        return source.charAt(current - 1);
    }

    // 处理输出：获取当前词素的文本并为其创建一个新的 token
    private void addToken(TokenType type){
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal){
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }


}
