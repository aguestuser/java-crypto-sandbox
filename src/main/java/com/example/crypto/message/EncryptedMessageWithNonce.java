package com.example.crypto.message;


public class EncryptedMessageWithNonce extends EncryptedMessage {

    public final String nonce;

    public EncryptedMessageWithNonce(String cyphertext, String nonce){
        super(cyphertext);
        this.nonce = nonce;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedMessageWithNonce msg = (EncryptedMessageWithNonce) o;
        return cyphertext.equals(msg.cyphertext) && nonce.equals(msg.nonce);
    }

    @Override
    public int hashCode() {
        int result = cyphertext.hashCode();
        result = 31 * result + nonce.hashCode();
        return result;
    }
}
