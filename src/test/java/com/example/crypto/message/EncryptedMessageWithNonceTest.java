package com.example.crypto.message;

import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EncryptedMessageWithNonceTest {

    @Test
    public void test_fields(){
        EncryptedMessageWithNonce message = new EncryptedMessageWithNonce("foo", "bar");

        assertThat(message.cyphertext, is(equalTo("foo")));
        assertThat(message.nonce, is(equalTo("bar")));
    }

    @Test
    public void test_equalityComparison(){
        EncryptedMessageWithNonce message1 = new EncryptedMessageWithNonce("foo", "bar");
        EncryptedMessageWithNonce message2 = new EncryptedMessageWithNonce("foo", "bar");
        EncryptedMessageWithNonce message3 = new EncryptedMessageWithNonce("foo", "baz");

        assertEquals(message1, message2);
        assertNotEquals(message1, message3);
    }

}