package crypto;


public class EncryptedMessage {

    public final String cyphertext;
    public final String nonce;

    public EncryptedMessage(String cyphertext, String nonce){
        this.cyphertext = cyphertext;
        this.nonce = nonce;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedMessage msg = (EncryptedMessage) o;
        return cyphertext.equals(msg.cyphertext) && nonce.equals(msg.nonce);
    }

    @Override
    public int hashCode() {
        int result = cyphertext.hashCode();
        result = 31 * result + nonce.hashCode();
        return result;
    }
}
