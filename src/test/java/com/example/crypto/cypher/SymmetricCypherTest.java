package com.example.crypto.cypher;

import com.example.crypto.message.EncryptedMessageWithNonce;
import com.example.crypto.generator.ByteGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.abstractj.kalium.encoders.Encoder.HEX;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static support.CryptoFixtures.SecretBox.*;

public class SymmetricCypherTest {

    private SymmetricCypher cypher;

    @Before
    public void setup(){
        ByteGenerator byteGenerator = mock(ByteGenerator.class);
        when(byteGenerator.generateNonceBytes()).thenReturn(NONCE_BYTES);
        when(byteGenerator.generateSecretKeyBytes()).thenReturn(SECRET_KEY_BYTES);

        cypher = new SymmetricCypher(byteGenerator);
    }

    @Test
    public void test_encrypt(){

        assertThat(
            cypher.encrypt(SECRET_KEY_HEX, CLEARTEXT),
            is(equalTo(new EncryptedMessageWithNonce(CYPHERTEXT_HEX, NONCE_HEX)))
        );
    }

    @Test
    public void test_decrypt(){

        assertThat(
            cypher.decrypt(SECRET_KEY_HEX, new EncryptedMessageWithNonce(CYPHERTEXT_HEX, NONCE_HEX)),
            is(equalTo(CLEARTEXT))
        );
    }
    
    @Test
    public void test_encryptDecrypt(){

        assertThat(
            cypher.decrypt(SECRET_KEY_HEX, cypher.encrypt(SECRET_KEY_HEX, CLEARTEXT)),
            is(equalTo(CLEARTEXT))
        );
    }

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
}
