package com.example.crypto.cypher;

import com.example.crypto.message.EncryptedMessage;
import com.example.crypto.util.ByteGenerator;
import com.example.crypto.message.EncryptedMessageWithNonce;
import org.abstractj.kalium.crypto.SecretBox;

import static org.abstractj.kalium.encoders.Encoder.HEX;

public class SymmetricCypher implements Cypher {

    private ByteGenerator byteGenerator;

    public SymmetricCypher(ByteGenerator byteGenerator){
        this.byteGenerator = byteGenerator;
    }

    @Override
    public EncryptedMessageWithNonce encrypt(String keyHex, String cleartext) {

        final SecretBox box = new SecretBox(HEX.decode(keyHex));
        final byte [] nonce = byteGenerator.generateNonceBytes();
        final byte [] cyphertext = box.encrypt(nonce, cleartext.getBytes());

        return new EncryptedMessageWithNonce(HEX.encode(cyphertext), HEX.encode(nonce));
    }

    @Override
    public String decrypt(String keyHex, EncryptedMessage encryptedMessage) {

        EncryptedMessageWithNonce message = (EncryptedMessageWithNonce)encryptedMessage;

        final SecretBox box = new SecretBox(HEX.decode(keyHex));
        final byte[] cyphertext = HEX.decode(message.cyphertext);
        final byte[] nonce = HEX.decode(message.nonce);

        return new String (box.decrypt(nonce, cyphertext));
    }
}
