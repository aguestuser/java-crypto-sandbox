package crypto.cypher;

import crypto.key.CaesarKey;

import java.util.stream.Collectors;

public class CaesarCypher extends Cypher {
    
    private CaesarKey key;
    
    public CaesarCypher(CaesarKey key) {
        this.key = key;
    }
    
    @Override
    public String encrypt (String cleartext){
        return cleartext.chars()
            .mapToObj(n -> String.valueOf((char) (n + this.key.get())))
            .collect(Collectors.joining());
    }
    
    @Override
    public String decrypt (String cleartext){
        return cleartext.chars()
            .mapToObj(n -> String.valueOf((char) (n - this.key.get())))
            .collect(Collectors.joining());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CaesarCypher)) return false;
        
        CaesarCypher that = (CaesarCypher) o;
    
        return key.equals(that.key);
    
    }
    
    @Override
    public int hashCode() {
        return key.hashCode();
    }
}
