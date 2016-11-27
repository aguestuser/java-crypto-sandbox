package support;

import static org.abstractj.kalium.encoders.Encoder.HEX;

public class CryptoFixtures {
    
    public static class SecretBox {
    
        public static final String SECRET_KEY_HEX = "1b27556473e985d462cd51197a9a46c76009549eac6474f206c4ee0844f68389";
        public static final byte[] SECRET_KEY_BYTES =  HEX.decode(SECRET_KEY_HEX); // *must* be 32 bytes long

        
        public static final String CLEARTEXT = "hello";
        public static final String CLEARTEXT_HEX = "68656c6c6f";
        public static final byte[] CLEARTEXT_BYTES = CLEARTEXT.getBytes();
    
        public static final String NONCE_HEX = "69696ee955b62b73cd62bda875fc73d68219e0036b7a0b37";
        public static final byte[] NONCE_BYTES = HEX.decode(NONCE_HEX); // *must be 24 bytes long*
        
        public static final String CYPHERTEXT_HEX = "1b294418500a75e29daebfe2764e569858fb08361b";
        public static final byte[] CYPHERTEXT_BYTES = HEX.decode(CYPHERTEXT_HEX);
    
    }
    
}
