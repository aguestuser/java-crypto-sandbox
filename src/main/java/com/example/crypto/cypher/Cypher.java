package com.example.crypto.cypher;

import com.example.crypto.message.EncryptedMessage;

public interface Cypher {
    EncryptedMessage encrypt(String key, String cleartext);
    String decrypt(String key, EncryptedMessage message);
}
