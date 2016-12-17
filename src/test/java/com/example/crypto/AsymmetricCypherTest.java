package com.example.crypto;

import org.junit.Test;

import static org.abstractj.kalium.encoders.Encoder.HEX;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static support.CryptoFixtures.SecretBox.CLEARTEXT;
import static support.CryptoFixtures.SecretBox.SECRET_KEY_BYTES;

public class AsymmetricCypherTest {

    private AsymmetricCypher cypher = AsymmetricCypher.INSTANCE;
    private String secretKeyHex = HEX.encode(SECRET_KEY_BYTES);
    private String publicKeyHex = ByteGenerator.generatePublicKeyHex(SECRET_KEY_BYTES);

    @Test
    public void test_encryptDecrypt(){
        assertThat(
            cypher.decrypt(secretKeyHex, cypher.encrypt(publicKeyHex, CLEARTEXT)),
            is(equalTo(CLEARTEXT))
        );
    }

}