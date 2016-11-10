package ui;

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
    
    static final String CAESAR_KEY_PROMPT = "Please enter an integer to use as a secret key...\n";
    
    static final String CAESAR_KEY_REPROMPT = "Sorry! You have to use an int as a key. Try again...";
    
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
    
    static final String CYPHER_TEXT_PROMPT = "Input text to encrypt...\n";
    
    static String cyphertextNotificationOf(String cypherText) {
        return String.format("\nYour encrypted message is: \n\n%s\n", cypherText);
    }
    
    static final String DONE_MESSAGE = "----Thanks for playing!----\n";

}
