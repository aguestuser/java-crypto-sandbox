package crypto;

public interface Cypher {
    String encrypt(String key, String cleartext, String nonce);
    String decrypt(String key, String cyphertext, String nonce);
}
