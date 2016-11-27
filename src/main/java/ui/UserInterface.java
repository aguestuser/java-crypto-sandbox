package ui;

import crypto.ByteGenerator;
import crypto.Cypher;
import crypto.EncryptedMessage;
import crypto.SymmetricCypher;
import ui.enums.CypherMode;
import ui.enums.CypherType;

import java.io.BufferedReader;
import java.io.IOException;

import static org.abstractj.kalium.encoders.Encoder.HEX;
import static ui.UserInterface.State.DECRYPTING;
import static ui.UserInterface.State.DONE;
import static ui.UserInterface.State.ENCRYPTING;
import static ui.enums.Continuation.*;
import static ui.enums.Continuation.DECRYPT_MESSAGE;
import static ui.enums.CypherMode.DECRYPT;
import static ui.enums.CypherMode.ENCRYPT;
import static ui.enums.CypherType.SYMMETRIC;
import static ui.UserInterfaceMessages.*;

public class UserInterface {
    
    private static final String EXIT = "x";
    
    enum State {
        GETTING_KEY,
        GETTING_MODE,
        GETTING_CYPHER,
        ENCRYPTING,
        DECRYPTING,
        GETTING_CONTINUATION,
        DONE
    }
    
    private BufferedReader reader;
    private Printer printer;
    private ByteGenerator byteGenerator;
    private State state;
    private CypherMode cypherMode;
    Cypher cypher; // "package-private"

    public UserInterface(
        BufferedReader reader,
        Printer printer,
        ByteGenerator byteGenerator){

        this.reader = reader;
        this.printer = printer;
        this.byteGenerator = byteGenerator;
        this.state = State.GETTING_KEY;
    }
    
    public UserInterface run() throws IOException {
        
        switch (this.state) {

            case GETTING_KEY:

                if (shouldGenerateKey(MAKE_NEW_KEY_PROMPT)){
                    String key = HEX.encode(this.byteGenerator.generateSecretKeyBytes());
                    printer.println(secretKeyNotificationOf(key));
                }

                this.state = State.GETTING_MODE;
                return this.run();

            case GETTING_MODE:

                this.cypherMode = getCypherMode(CYPHER_MODE_PROMPT);

                this.state = State.GETTING_CYPHER;
                return this.run();

            case GETTING_CYPHER:

                this.cypher = new SymmetricCypher(this.byteGenerator); // hard code for now
                printer.println(cypherTypeNotificationOf(SYMMETRIC.str));

                this.state = this.cypherMode == ENCRYPT ? ENCRYPTING : DECRYPTING;
                return this.run();

            case ENCRYPTING:

                EncryptedMessage message = encryptClearText();
                printer.println(encryptionNotificationOf(message.cyphertext, message.nonce));

                this.state = State.GETTING_CONTINUATION;
                return this.run();

            case DECRYPTING:

                String cleartext = decryptCypherText();
                printer.println(decryptionNotificationOf(cleartext));

                this.state = State.GETTING_CONTINUATION;
                return this.run();

            case GETTING_CONTINUATION:

                this.state = getContinuation(CONTINUATION_PROMPT);
                return this.run();
                
            default:

                printer.println(DONE_MESSAGE);
                return this;
        }
    }

    private boolean shouldGenerateKey(String prompt) throws IOException {

        String input = promptAndGet(prompt);

        switch(input){
            case "y":
                return true;
            case "n":
                return false;
            default:
                return shouldGenerateKey(MAKE_NEW_KEY_REPROMPT);
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
        
        if (cypherNum == SYMMETRIC.num) {
            return SYMMETRIC;
        } else {
            return getCypherType(CYPHER_TYPE_REPROMPT);
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

    EncryptedMessage encryptClearText() throws IOException {

        String key = promptAndGet(ENCRYPT_TO_SECRET_KEY_PROMPT);
        String cleartext = promptAndGet(ENCRYPT_CLEARTEXT_PROMPT);

        return cypher.encrypt(key, cleartext);
    }
    
    String decryptCypherText() throws IOException {

        String key = promptAndGet(DECRYPT_WITH_SECRET_KEY_PROMPT);
        String cyphertext = promptAndGet(DECRYPT_CYPHERTEXT_PROMPT);
        String nonce = promptAndGet(DECRYPT_NONCE_PROMPT);

        return cypher.decrypt(key, new EncryptedMessage(cyphertext, nonce));
    }
    
    State getContinuation(String prompt) throws IOException {

        String input = promptAndGet(prompt);

        if (input.equals(MAKE_KEY.flag)){
            return State.GETTING_KEY;
        } else if (input.equals(ENCRYPT_MESSAGE.flag)) {
            return ENCRYPTING;
        } else if (input.equals(DECRYPT_MESSAGE.flag)) {
            return DECRYPTING;
        } else if (input.equals(QUIT.flag)) {
            return DONE;
        } else {
            return getContinuation(CONTINUATION_REPROMPT);
        }
    }

    private String promptAndGet(String prompt) throws IOException {
        printer.println(prompt);
        return this.reader.readLine();
    }
}

