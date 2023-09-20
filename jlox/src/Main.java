import java.util.List;

public class Main {
    public static void main(String[] args) {
        String sourceCode = "var x = 10; print(x);";
        Scanner scanner = new Scanner(sourceCode);
        List<Token> tokens = scanner.scanTokens();

        for (Token token : tokens) {
            System.out.println(token);
        }
    }
}
