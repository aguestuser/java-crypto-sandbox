package com.example.crypto.message;

public class EncryptedMessageWithRoutingInfo extends EncryptedMessage {

    public final String timestamp;
    public final String hmac;

    public EncryptedMessageWithRoutingInfo (
        String cyphertext,
        String timestamp,
        String hmac){

        super(cyphertext);
        this.timestamp = timestamp;
        this.hmac = hmac;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EncryptedMessageWithRoutingInfo)) return false;
        if (!super.equals(o)) return false;

        EncryptedMessageWithRoutingInfo that = (EncryptedMessageWithRoutingInfo) o;

        if (!timestamp.equals(that.timestamp)) return false;
        return hmac.equals(that.hmac);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + timestamp.hashCode();
        result = 31 * result + hmac.hashCode();
        return result;
    }
}

