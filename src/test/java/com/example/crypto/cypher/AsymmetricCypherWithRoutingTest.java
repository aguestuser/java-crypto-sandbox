package com.example.crypto.cypher;

import com.example.crypto.generator.ByteGenerator;
import com.example.crypto.generator.TimestampGenerator;
import org.junit.Before;
import org.junit.Test;

import static org.abstractj.kalium.encoders.Encoder.HEX;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static support.CryptoFixtures.SecretBox.*;

public class AsymmetricCypherWithRoutingTest {

    private String secretKeyHex = SECRET_KEY_HEX;
    private AsymmetricCypherWithRouting cypher;
    private String publicKeyHex;
    private String otherPublicKeyHex;


    @Before
    public void setup(){

        publicKeyHex = ByteGenerator.generatePublicKeyHex(SECRET_KEY_BYTES);
        byte[] otherSecretKeyBytes = ByteGenerator.INSTANCE.generateSecretKeyBytes();
        otherPublicKeyHex = ByteGenerator.generatePublicKeyHex(otherSecretKeyBytes);

        TimestampGenerator mockTsGen = mock(TimestampGenerator.class);
        when(mockTsGen.getTimestamp()).thenReturn(TIMESTAMP);
        cypher = new AsymmetricCypherWithRouting(mockTsGen);
    }

    @Test
    public void test_decrypt_ownMessage(){
        assertEquals(
            CLEARTEXT,
            cypher.decrypt(secretKeyHex, cypher.encrypt(publicKeyHex, CLEARTEXT))
        );
    }

    @Test
    public void test_decrypt_anotherPersonsMessage(){
        assertNull(
            cypher.decrypt(secretKeyHex, cypher.encrypt(otherPublicKeyHex, CLEARTEXT))
        );
    }

}