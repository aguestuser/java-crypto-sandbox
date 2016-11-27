package crypto;

import org.abstractj.kalium.crypto.SecretBox;
import org.junit.Test;

import static org.abstractj.kalium.encoders.Encoder.HEX;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static support.CryptoFixtures.*;
import static support.CryptoFixtures.SecretBox.*;

public class SymmetricCypherTest {

    /**************  sanity checks *******************/

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

    /************* end sanity checks *******************/
    
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
