package ui;

import ui.enums.CypherType;

import static ui.enums.CypherType.ASYMMETRIC;
import static ui.enums.CypherType.SYMMETRIC;

class UserInterfaceMessages {

    private static final String REPROMPT = "\n Sorry. That's not a valid  choice. Please enter one of the following: \n";

    // GETTING_KEY messages

    static private final String MAKE_NEW_KEY_CHOICES = "[y] yes\n[n] no\n";

    static final String MAKE_NEW_KEY_PROMPT = "\nDo you want to generate a new key?\n" + MAKE_NEW_KEY_CHOICES;

    static final String MAKE_NEW_KEY_REPROMPT = REPROMPT + MAKE_NEW_KEY_CHOICES;

    static String keyNotificationOf(CypherType cypherType, String key){
        switch(cypherType){
            case SYMMETRIC:
                return secretKeyNotificationOf(key);
            default:
                return "no key generated";
        }
    }

    static String secretKeyNotificationOf(String key) {
        return String.format("\nInitialized %s with secret key <%s>.", SYMMETRIC.str, key);
    }

    // GETTING_MODE messages

    static private final String CYPHER_MODE_CHOICES = "[e] encrypt\n[d] decrypt\n";

    static final String CYPHER_MODE_PROMPT = "\nWould you like to encrypt or decrypt a message?\n" + CYPHER_MODE_CHOICES;

    static final String CYPHER_MODE_REPROMPT = "\nSorry, that's not a valid option! Please choose either:\n" + CYPHER_MODE_CHOICES;

    // GETTING_CYPHER messages

    static final String CYPHER_TYPE_PROMPT = "\nHello! Please choose a cypher:\n\n" +
        String.format("[1] %s\n", SYMMETRIC.str) +
        String.format("[2] %s\n", ASYMMETRIC.str);
    
    static final String CYPHER_TYPE_REPROMPT = "\nSorry! Valid options are 1, and 2. Try again...\n";
    
    static String cypherTypeNotificationOf(String cypherType) {
        return String.format("\nYou are using %s Encryption Mode.", cypherType);
    }

    // ENCRYPTION PROMPTS

    static final String ENCRYPT_CLEARTEXT_PROMPT = "\nInput text to encrypt...\n";

    static final String ENCRYPT_TO_SECRET_KEY_PROMPT = "\nInput secret key to encrypt message to...\n";

    static String encryptionNotificationOf(String cypherText, String nonce) {
        return String.format("\nEncrypted message:\n<%s>\nNonce:\n<%s>\n", cypherText, nonce);
    }

    // DECRYPTION PROMPTS

    static final String DECRYPT_CYPHERTEXT_PROMPT = "\nInput message to decrypt...\n";

    static final String DECRYPT_NONCE_PROMPT = "\nInput nonce generated with message...\n";

    static final String DECRYPT_WITH_SECRET_KEY_PROMPT = "\nInput secret key to decrypt message with...\n";

    static String decryptionNotificationOf(String clearText) {
        return String.format("\nYour decrypted message is: \n\n%s\n\n", clearText);
    }

    // CONTINUATION messages

    private static final String CONTINUATION_CHOICES = "" +
        "[k] make new key\n" +
        "[e] encrypt message\n" +
        "[d] decrypt message\n" +
        "[q] quit\n\n";

    static final String CONTINUATION_PROMPT = "\nWhat would you like to do next?\n" +
        CONTINUATION_CHOICES;

    static final String CONTINUATION_REPROMPT = REPROMPT + CONTINUATION_CHOICES;
    
    static final String DONE_MESSAGE = "\n----Thanks for playing!----\n";

}
