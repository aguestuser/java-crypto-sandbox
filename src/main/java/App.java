import crypto.CaesarCypher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    
    public static void main(String args[]) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int key = getKey(br, "Hello! Please enter an integer to use as a secret key!");
        CaesarCypher cypher = new CaesarCypher(key);
        
        System.out.println("\nGreat! Please enter a message to encrypt...");
        String cyphertext = cypher.encrypt(br.readLine());
            
        System.out.println(
            String.format("\nYour secret message is: \n%s", cyphertext)
        );
    }
    
    static private int getKey(BufferedReader br, String prompt) throws NumberFormatException, IOException {

        System.out.println(prompt);
        
        String input = br.readLine();
        int key;
        
        try {
            key = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return getKey(br, "Sorry! You have to use an int as a key. Try again...");
        }
        
        return key;
    }
        
}