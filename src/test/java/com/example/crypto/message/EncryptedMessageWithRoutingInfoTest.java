package com.example.crypto.message;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptedMessageWithRoutingInfoTest {

    @Test
    public void test_fields(){
        EncryptedMessageWithRoutingInfo message =
            new EncryptedMessageWithRoutingInfo("fake cyphertext", "fake timestamp", "fake hmac");

        assertEquals("fake cyphertext", message.cyphertext);
        assertEquals("fake timestamp", message.timestamp);
        assertEquals("fake hmac", message.hmac);
    }

    @Test
    public void test_equalityComparison(){
        EncryptedMessageWithRoutingInfo message1 =
            new EncryptedMessageWithRoutingInfo("fake cyphertext", "fake timestamp", "fake hmac");

        EncryptedMessageWithRoutingInfo message2 =
            new EncryptedMessageWithRoutingInfo("fake cyphertext", "fake timestamp", "fake hmac");

        EncryptedMessageWithRoutingInfo message3 =
            new EncryptedMessageWithRoutingInfo("fake cyphertext", "new fake timestamp", "new fake hmac");

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
    }

}

/*
*
*     @Test
    public void test_fields(){
        EncryptedMessageWithNonce message = new EncryptedMessageWithNonce("foo", "bar");

        assertThat(message.cyphertext, is(equalTo("foo")));
        assertThat(message.nonce, is(equalTo("bar")));
    }

    @Test
    public void test_equalityComparison(){
        EncryptedMessageWithNonce message1 = new EncryptedMessageWithNonce("foo", "bar");

        EncryptedMessageWithNonce message2 = new EncryptedMessageWithNonce("foo", "bar");

        assertEquals(message1, message2);
    }

*
* **/