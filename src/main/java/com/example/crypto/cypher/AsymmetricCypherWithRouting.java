package com.example.crypto.cypher;

import com.example.crypto.generator.ByteGenerator;
import com.example.crypto.generator.TimestampGenerator;
import com.example.crypto.message.EncryptedMessage;
import com.example.crypto.message.EncryptedMessageWithRoutingInfo;
import com.google.common.collect.ObjectArrays;
import org.abstractj.kalium.crypto.Hash;

import static org.abstractj.kalium.encoders.Encoder.HEX;

public class AsymmetricCypherWithRouting implements Cypher {

    private TimestampGenerator timestampGenerator;
    private Hash hash;

    AsymmetricCypherWithRouting(TimestampGenerator timestampGenerator){
        this.timestampGenerator = timestampGenerator;
        this.hash = new Hash();
    }

    @Override
    public EncryptedMessage encrypt(String publicKeyHex, String cleartext) {

        String cyphertext = AsymmetricCypher.INSTANCE.encrypt(publicKeyHex, cleartext).cyphertext;
        String timestamp = timestampGenerator.getTimestamp();
        String hmac = HEX.encode(hash.sha512((publicKeyHex + timestamp).getBytes()));

        return new EncryptedMessageWithRoutingInfo(cyphertext, timestamp, hmac);
    }

    @Override
    public String decrypt(String privateKeyHex, EncryptedMessage message) {

        EncryptedMessageWithRoutingInfo _message = (EncryptedMessageWithRoutingInfo)message;
        // TODO: make `ownKeyPair` a private field on asym cyphers & `ownSecretKey` a priv field on sym cypher? (would remove an argument from `decrypt` and make this (expensive-ish) line unnecessary
        String myPublicKeyHex = ByteGenerator.generatePublicKeyHex(HEX.decode(privateKeyHex));
        String hmacForMe = HEX.encode(hash.sha512((myPublicKeyHex + _message.timestamp).getBytes()));

        if (_message.hmac.equals(hmacForMe)){
            return AsymmetricCypher.INSTANCE.decrypt(privateKeyHex, message);
        } else {
            return null;
        }

    }
}
