package com.example.crypto.message;

import org.junit.Test;

import static org.junit.Assert.*;

public class EncryptedMessageTest {

    @Test
    public void test_fields(){
        EncryptedMessage message = new EncryptedMessage("foo");
        assertEquals("foo", message.cyphertext);
    }

    @Test
    public void test_equalityComparison(){
        EncryptedMessage message1 = new EncryptedMessage("foo");
        EncryptedMessage message2 = new EncryptedMessage("foo");

        assertEquals(message1, message2);
    }

}