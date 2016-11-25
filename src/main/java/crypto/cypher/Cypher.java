package crypto.cypher;

public abstract class Cypher {
    public abstract String encrypt(String cleartext);
    public abstract String decrypt(String cyphertext);
}
