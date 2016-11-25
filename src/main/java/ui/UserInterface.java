package ui;

import crypto.*;

import java.io.BufferedReader;
import java.io.IOException;

import static crypto.CypherMode.DECRYPT;
import static crypto.CypherMode.ENCRYPT;
import static crypto.CypherType.CAESAR;
import static crypto.CypherType.NACL_SYMMETRIC;
import static ui.Continuation.CONTINUE;
import static ui.Continuation.QUIT;
import static ui.UserInterfaceMessages.*;

public class UserInterface {
    
    private static final String EXIT = "x";
    
    private enum State {
        GETTING_CYPHER,
        GETTING_KEY,
        GETTING_MODE,
        GETTING_MESSAGE,
        GETTING_CONTINUATION,
        DONE
    }
    
    private BufferedReader reader;
    private Printer printer;
    private State state;
    private CypherMode cypherMode;
    
    // package-private
    CypherType cypherType;
    Key key;
    Cypher cypher;
    
    public UserInterface(BufferedReader reader, Printer printer){
        this.reader = reader;
        this.printer = printer;
        this.state = State.GETTING_CYPHER;
    }
    
    public UserInterface run() throws IOException {
        
        switch (this.state) {
            
            case GETTING_CYPHER:
                
                this.cypherType = getCypherType(CYPHER_TYPE_PROMPT);
                printer.println(cypherTypeNotificationOf(cypherType.str));
    
                this.state = State.GETTING_KEY;
                return this.run();

            case GETTING_KEY:

                this.key = getCaesarKey(CAESAR_KEY_PROMPT);
                printer.println(keyNotificationOf(cypherType, key));
                this.cypher = getCypher();
    
                this.state = State.GETTING_MODE;
                return this.run();
            
            case GETTING_MODE:
                
                this.cypherMode = getCypherMode(CYPHER_MODE_PROMPT);
                
                this.state = State.GETTING_MESSAGE;
                return this.run();
            
            case GETTING_MESSAGE:
                
                switch(this.cypherMode) {
                    case ENCRYPT:
                        String cypherText = getCypherText();
                        printer.println(encryptionNotificationOf(cypherText));
                        break;
                    case DECRYPT:
                        String clearText = getClearText();
                        printer.println(decryptionNotificationOf(clearText));
                        break;
                }
                
                this.state = State.GETTING_CONTINUATION;
                return this.run();
                
            case GETTING_CONTINUATION:

                Continuation continuation = getContinuation(CONTINUATION_PROMPT);

                switch (continuation){
                    case CONTINUE:
                        this.state = State.GETTING_MODE;
                        break;
                    case QUIT:
                        this.state = State.DONE;
                        break;
                }
                return this.run();
                
            default:
                printer.println(DONE_MESSAGE);
                return this;
        }
    }
    
    
    CypherType getCypherType(String prompt) throws NumberFormatException, IOException {

        String input = promptAndGet(prompt);
        int cypherNum;
        
        try {
            cypherNum = Integer.parseInt(input);
        } catch (NumberFormatException nfe) {
            return getCypherType(CYPHER_TYPE_REPROMPT);
        }
        
        if (cypherNum == CAESAR.num) { return CAESAR; }
        else if (cypherNum == NACL_SYMMETRIC.num) { return NACL_SYMMETRIC; }
        else { return getCypherType(CYPHER_TYPE_REPROMPT); }
    }
    
    Key getCaesarKey(String prompt) throws NumberFormatException, IOException {

        String input = promptAndGet(prompt);
        
        try {
            return new CaesarKey(Integer.parseInt(input));
        } catch (NumberFormatException nfe) {
            return getCaesarKey(CAESAR_KEY_REPROMPT);
        }
    }
    
    Cypher getCypher(){

        switch(this.cypherType) {
            case CAESAR:
                return new CaesarCypher((CaesarKey) this.key);
            default:
                return new CaesarCypher((CaesarKey) this.key);
        }
    }
    
    CypherMode getCypherMode(String prompt) throws IOException {

        String input = promptAndGet(prompt);

        if (input.equals(ENCRYPT.flag)) {
            return ENCRYPT;
        } else if (input.equals(DECRYPT.flag)) {
            return DECRYPT;
        } else {
            return getCypherMode(CYPHER_MODE_REPROMPT);
        }
    }

    String getCypherText() throws IOException {
        String input = promptAndGet(ENCRYPTION_PROMPT);
        return this.cypher.encrypt(input);
    }
    
    String getClearText() throws IOException {
        String input = promptAndGet(DECRYPTION_PROMPT);
        return this.cypher.decrypt(input);
    }
    
    Continuation getContinuation(String prompt) throws IOException {

        String input = promptAndGet(prompt);
        
        if (input.equals(CONTINUE.flag)){
            return CONTINUE;
        } else if (input.equals(QUIT.flag)) {
            return QUIT;
        } else {
            return getContinuation(CONTINUATION_REPROMPT);
        }
    }
    
    
    private String promptAndGet(String prompt) throws IOException {
        printer.println(prompt);
        return this.reader.readLine();
    }
}

