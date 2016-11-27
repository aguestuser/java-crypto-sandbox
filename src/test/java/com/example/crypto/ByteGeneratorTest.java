package com.example.crypto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class ByteGeneratorTest {

    private ByteGenerator bg = ByteGenerator.INSTANCE;

    @Test
    public void test_generateSecretKeyBytes(){
        assertThat(
            bg.generateSecretKeyBytes().length,
            is(equalTo(32))
        );
    }
    @Test
    public void test_generateSecretNonceBytes(){
        assertThat(
            bg.generateNonceBytes().length,
            is(equalTo(24))
        );
    }

}