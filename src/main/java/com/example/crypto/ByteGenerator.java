package com.example.crypto;

import org.abstractj.kalium.crypto.Random;

import static org.abstractj.kalium.NaCl.Sodium.CRYPTO_SECRETBOX_XSALSA20POLY1305_KEYBYTES;
import static org.abstractj.kalium.NaCl.Sodium.CRYPTO_SECRETBOX_XSALSA20POLY1305_NONCEBYTES;

public class ByteGenerator {

    // singleton
    public final static ByteGenerator INSTANCE = new ByteGenerator();
    private ByteGenerator(){}

    public byte[] generateSecretKeyBytes(){
        return new Random().randomBytes(CRYPTO_SECRETBOX_XSALSA20POLY1305_KEYBYTES);
    }

    public byte[] generateNonceBytes(){
        return new Random().randomBytes(CRYPTO_SECRETBOX_XSALSA20POLY1305_NONCEBYTES);
    }
}
