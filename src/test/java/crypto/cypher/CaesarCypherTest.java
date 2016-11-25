package crypto.cypher;

import crypto.cypher.CaesarCypher;
import crypto.key.CaesarKey;
import org.junit.Test;

import static org.junit.Assert.*;

public class CaesarCypherTest {

    @Test
    public void test_encrypt() throws Exception {

        CaesarCypher cypher = new CaesarCypher(new CaesarKey(2));
        
        assertEquals("it increments each character by N code points, where N is the key",
            "jgnnq",
            cypher.encrypt("hello")
        );
            
    }
    
    @Test
    public void test_decrypt() throws Exception {

        CaesarCypher cypher = new CaesarCypher(new CaesarKey(2));
        
        assertEquals("it decrements each character by N code points, where N is the key",
            "hello",
            cypher.decrypt("jgnnq")
        );
            
    }
    
}