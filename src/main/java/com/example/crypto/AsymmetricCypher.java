package com.example.crypto;

import org.abstractj.kalium.crypto.SealedBox;

import static org.abstractj.kalium.encoders.Encoder.HEX;

public class AsymmetricCypher implements Cypher {

    // singleton
    final static AsymmetricCypher INSTANCE = new AsymmetricCypher();
    private AsymmetricCypher(){}

    @Override
    public EncryptedMessage encrypt(String publicKeyHex, String cleartext) {
        final SealedBox box = new SealedBox(HEX.decode(publicKeyHex));
        final byte[] cypherTextBytes = box.encrypt(cleartext.getBytes());

        return new EncryptedMessage(HEX.encode(cypherTextBytes),"");
    }

    @Override
    public String decrypt(String privateKeyHex, EncryptedMessage message) {
        final byte[] privateKeyBytes = HEX.decode(privateKeyHex);
        final byte[] publicKeyBytes = ByteGenerator.generatePublicKeyBytes(privateKeyBytes);
        final SealedBox box = new SealedBox(publicKeyBytes, privateKeyBytes);

        return new String(box.decrypt(HEX.decode(message.cyphertext)));
    }
}
