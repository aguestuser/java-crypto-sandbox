package com.example.crypto.message;

public class EncryptedMessage {
    public final String cyphertext;

    protected EncryptedMessage(String cyphertext){
        this.cyphertext = cyphertext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncryptedMessage)) return false;

        EncryptedMessage that = (EncryptedMessage) o;

        return cyphertext.equals(that.cyphertext);
    }

    @Override
    public int hashCode() {
        return cyphertext.hashCode();
    }
}
