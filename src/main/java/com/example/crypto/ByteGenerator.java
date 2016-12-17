package com.example.crypto;

import org.abstractj.kalium.crypto.Random;
import org.abstractj.kalium.keys.KeyPair;

import static org.abstractj.kalium.NaCl.Sodium.CRYPTO_SECRETBOX_XSALSA20POLY1305_KEYBYTES;
import static org.abstractj.kalium.NaCl.Sodium.CRYPTO_SECRETBOX_XSALSA20POLY1305_NONCEBYTES;
import static org.abstractj.kalium.encoders.Encoder.HEX;

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

    static byte[] generatePublicKeyBytes(byte[] privateKeyBytes){
        return new KeyPair(privateKeyBytes).getPublicKey().toBytes();
    }

    static String generatePublicKeyHex(byte[] privateKeyBytes){
        return HEX.encode(generatePublicKeyBytes(privateKeyBytes));
    }
}
