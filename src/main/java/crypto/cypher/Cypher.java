package crypto.cypher;

public abstract class Cypher {
    public abstract String encrypt(String cleartext, String key, String nonce);
    public abstract String decrypt(String cyphertext, String key, String nonce);
}
