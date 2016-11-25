package crypto.cypher;

import org.abstractj.kalium.crypto.SecretBox;
import org.junit.Test;

import static org.abstractj.kalium.encoders.Encoder.HEX;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class SecretBoxCypherTest {
    
    private static final String SECRET_KEY_HEX = "1b27556473e985d462cd51197a9a46c76009549eac6474f206c4ee0844f68389";
    
    private static final String CLEARTEXT = "hello";
    private static final String CLEARTEXT_HEX = "68656c6c6f";
    private static final byte[] CLEARTEXT_BYTES = CLEARTEXT.getBytes();
    
    private static final String NONCE_HEX = "69696ee955b62b73cd62bda875fc73d68219e0036b7a0b37";
    private static final byte[] NONCE_BYTES = HEX.decode(NONCE_HEX);
    
    private static final String CYPHERTEXT_HEX = "1b294418500a75e29daebfe2764e569858fb08361b";
    private static final byte[] CYPHERTEXT_BYTES = HEX.decode(CYPHERTEXT_HEX);
    
    @Test
    public void test_hexEncode(){
        assertThat(
            HEX.encode(CLEARTEXT_BYTES),
            is(equalTo(CLEARTEXT_HEX))
        );
    }
    
    @Test
    public void test_hexDecode(){
        assertThat(
            HEX.decode(CLEARTEXT_HEX),
            is(equalTo(CLEARTEXT_BYTES))
        );
    }
    
    @Test
    public void test_encrypt(){

        SecretBox box = new SecretBox(SECRET_KEY_HEX, HEX);
    
        assertThat(
            box.encrypt(NONCE_BYTES, CLEARTEXT_BYTES),
            is(equalTo(CYPHERTEXT_BYTES))
        );
    }
    
    @Test
    public void test_decrypt(){
        
        SecretBox box = new SecretBox(SECRET_KEY_HEX, HEX);
        
        assertThat(
            box.decrypt(NONCE_BYTES, CYPHERTEXT_BYTES),
            is(equalTo(CLEARTEXT_BYTES))
        );
    }
    
    @Test
    public void test_encryptDecrypt(){
        SecretBox box = new SecretBox(SECRET_KEY_HEX, HEX);
    
        assertThat(
            box.decrypt(NONCE_BYTES, box.encrypt(NONCE_BYTES, CLEARTEXT_BYTES)),
            is(equalTo(CLEARTEXT_BYTES))
        );
        
    }
}
