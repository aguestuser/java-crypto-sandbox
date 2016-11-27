package com.example.crypto;

public interface Cypher {
    EncryptedMessage encrypt(String key, String cleartext);
    String decrypt(String key, EncryptedMessage message);
}
