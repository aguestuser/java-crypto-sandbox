package com.example.crypto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class EncryptedMessageTest {

    @Test
    public void test_fields(){
        EncryptedMessage message = new EncryptedMessage("foo", "bar");

        assertThat(message.cyphertext, is(equalTo("foo")));
        assertThat(message.nonce, is(equalTo("bar")));
    }

}