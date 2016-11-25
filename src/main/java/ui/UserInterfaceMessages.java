package ui;

import crypto.CypherMode;
import crypto.CypherType;
import crypto.Key;

import static crypto.CypherType.CAESAR;
import static crypto.CypherType.NACL_SYMMETRIC;

class UserInterfaceMessages {

    static final String CYPHER_TYPE_PROMPT = "\nHello! Please choose a cypher:\n\n" +
        String.format("[1] %s\n", CAESAR.str) +
        String.format("[2] %s\n", NACL_SYMMETRIC.str);
    
    static final String CYPHER_TYPE_REPROMPT = "\nSorry! Valid options are 1, and 2. Try again...\n";
    
    static String cypherTypeNotificationOf(String cypherType) {
        return String.format("\nYou chose %s.", cypherType);
    }
    
    static final String CAESAR_KEY_PROMPT = "\nPlease enter an integer to use as a secret key...\n";
    
    static final String CAESAR_KEY_REPROMPT = "\nSorry! You have to use an int as a key. Try again...";
    
    static String keyNotificationOf(CypherType cypherType, Key key){
        switch(cypherType){
            case CAESAR:
                return caesarKeyNotificationOf((int) key.get());
            default:
                return caesarKeyNotificationOf((int) key.get());
        }
    }
    
    static String caesarKeyNotificationOf(int key) {
        return String.format("\nInitialized %s with key <%s>.", CAESAR.str, key);
    }
    
    static final String CYPHER_MODE_PROMPT = "\nWould you like to encrypt or decrypt a message?\n" +
        "[e] encrypt\n" +
        "[d] decrypt\n";
    
    static final String CYPHER_MODE_REPROMPT = "\nSorry, that's not a valid option! Please choose either:\n" +
        "[e] encrypt\n" +
        "[d] decrypt\n";
    
    static final String ENCRYPTION_PROMPT = "\nInput text to encrypt...\n";
    
    static String encryptionNotificationOf(String cypherText) {
        return String.format("\nYour encrypted message is: \n\n%s\n", cypherText);
    }
    
    static final String DECRYPTION_PROMPT = "Input text to decrypt...\n";
    
    static String decryptionNotificationOf(String clearText) {
        return String.format("\nYour encrypted message is: \n\n%s\n", clearText);
    }
    
    static final String CONTINUATION_PROMPT = "\nWould you like to continue or quit?\n" +
        "[c] continue\n" +
        "[q] quit";
    
    static final String CONTINUATION_REPROMPT = "\nSorry, that's not a valid option! Please choose either:\n" +
        "[c] continue\n" +
        "[q] quit";
    
    static final String DONE_MESSAGE = "\n----Thanks for playing!----\n";

}
