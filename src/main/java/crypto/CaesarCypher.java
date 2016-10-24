package crypto;

import java.util.stream.Collectors;

public class CaesarCypher {
    
    private int key;
    
    public CaesarCypher(int key) {
        this.key = key;
    }
    
    public String encrypt (String cleartext){
        return cleartext.chars()
            .mapToObj(n -> String.valueOf((char) (n + this.key)))
            .collect(Collectors.joining());
    }
    
    
}
