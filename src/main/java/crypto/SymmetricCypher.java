package crypto;

import org.abstractj.kalium.crypto.SecretBox;

import static org.abstractj.kalium.encoders.Encoder.HEX;

public class SymmetricCypher implements Cypher {

    private ByteGenerator byteGenerator;

    public SymmetricCypher(ByteGenerator byteGenerator){
        this.byteGenerator = byteGenerator;
    }

    @Override
    public EncryptedMessage encrypt(String keyHex, String cleartext) {

        final SecretBox box = new SecretBox(HEX.decode(keyHex));
        final byte [] nonce = byteGenerator.generateNonceBytes();
        final byte [] cyphertext = box.encrypt(nonce, cleartext.getBytes());

        return new EncryptedMessage(HEX.encode(cyphertext), HEX.encode(nonce));
    }

    @Override
    public String decrypt(String key, EncryptedMessage message) {

        final SecretBox box = new SecretBox(HEX.decode(key));
        final byte[] cyphertext = HEX.decode(message.cyphertext);
        final byte[] nonce = HEX.decode(message.nonce);

        return new String (box.decrypt(nonce, cyphertext));
    }
}
