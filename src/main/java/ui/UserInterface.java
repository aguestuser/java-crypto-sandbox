package ui;

import crypto.*;

import java.io.BufferedReader;
import java.io.IOException;

import static crypto.CypherType.CAESAR;
import static crypto.CypherType.NACL_SYMMETRIC;
import static ui.UserInterfaceMessages.*;

public class UserInterface {
    
    private enum State {
        GETTING_CYPHER,
        GETTING_KEY,
        GETTING_MESSAGE,
        DONE
    }
    
    private BufferedReader reader;
    private Printer printer;
    private State state;
    
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
    
                this.state = State.GETTING_MESSAGE;
                return this.run();
            
            case GETTING_MESSAGE:

                String cypherText = getCypherText();
                printer.println(cyphertextNotificationOf(cypherText));
                
                this.state = State.DONE;
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
    
        switch(cypherNum){
            case 1:
                return CAESAR;
            case 2:
                return NACL_SYMMETRIC;
            default:
                return getCypherType(CYPHER_TYPE_REPROMPT);
        }
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

    String getCypherText() throws IOException {
        String input = promptAndGet(CYPHER_TEXT_PROMPT);
        
        return this.cypher.encrypt(input);
    }
    
    private String promptAndGet(String prompt) throws IOException {
        printer.println(prompt);
        return this.reader.readLine();
    }
}

