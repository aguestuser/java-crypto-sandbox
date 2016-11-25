package ui;

import crypto.CaesarCypher;
import crypto.CaesarKey;
import crypto.Key;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;

import static crypto.CypherMode.DECRYPT;
import static crypto.CypherMode.ENCRYPT;
import static crypto.CypherType.CAESAR;
import static crypto.CypherType.NACL_SYMMETRIC;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static ui.Continuation.CONTINUE;
import static ui.UserInterfaceMessages.*;

@RunWith(Enclosed.class)

public class UserInterfaceTest {
    
    public static class MainLoop {
    
        // TODO: use Mockito's `inOrder` interface for cleaner func test mocks
    
        private BufferedReader reader;
        private Printer printer;
        private UserInterface userInterface;
    
        @Before
        public void setup(){
            reader = mock(BufferedReader.class);
            printer = mock(Printer.class);
            userInterface = new UserInterface(reader, printer);
        }
        
        @Test
        public void test_encryptingWithCaesar() throws IOException {
                                
            when(reader.readLine())
                .thenReturn("1")
                .thenReturn("2")
                .thenReturn(("e"))
                .thenReturn("hello")
                .thenReturn(("q"));
        
            userInterface.run();
        
            verify(printer).println(CYPHER_TYPE_PROMPT);
            verify(printer).println(cypherTypeNotificationOf(CAESAR.str));
        
            verify(printer).println(CAESAR_KEY_PROMPT);
            verify(printer).println(caesarKeyNotificationOf(2));
    
            verify(printer).println(CYPHER_MODE_PROMPT);
            verify(printer).println(ENCRYPTION_PROMPT);
            verify(printer).println(encryptionNotificationOf("jgnnq"));
        
            verify(printer).println(DONE_MESSAGE);
        }
        
        @Test
        public void test_decryptingWithCaesar() throws IOException {
                            
            when(reader.readLine())
                .thenReturn("1") // cypher
                .thenReturn("2") // key
                .thenReturn(("d")) // mode
                .thenReturn("jgnnq") // message
                .thenReturn("q"); // continuation
        
            userInterface.run();
        
            verify(printer).println(CYPHER_TYPE_PROMPT);
            verify(printer).println(cypherTypeNotificationOf(CAESAR.str));
        
            verify(printer).println(CAESAR_KEY_PROMPT);
            verify(printer).println(caesarKeyNotificationOf(2));
        
            verify(printer).println(CYPHER_MODE_PROMPT);
            verify(printer).println(DECRYPTION_PROMPT);
            verify(printer).println(decryptionNotificationOf("hello"));
        
            verify(printer).println(DONE_MESSAGE);
        }
    
        @Test
        public void test_encryptingAndDecryptingWithCaesar() throws IOException {
                            
            when(reader.readLine())
                .thenReturn("1") // cypher
                .thenReturn("2") // key
                .thenReturn(("d")) // mode
                .thenReturn("jgnnq") // message
                .thenReturn("c") // continuation
                .thenReturn(("e")) // mode
                .thenReturn("hello") // message
                .thenReturn("q"); // continuation
        
            userInterface.run();
        
            verify(printer).println(CYPHER_TYPE_PROMPT);
            verify(printer).println(cypherTypeNotificationOf(CAESAR.str));
        
            verify(printer).println(CAESAR_KEY_PROMPT);
            verify(printer).println(caesarKeyNotificationOf(2));
        
            verify(printer, times(2)).println(CYPHER_MODE_PROMPT);

            verify(printer).println(DECRYPTION_PROMPT);
            verify(printer).println(decryptionNotificationOf("hello"));
    
            verify(printer, times(2)).println(CONTINUATION_PROMPT);

            // (second invocation of CYPHER_MODE_PROMPT here)
            
            verify(printer).println(ENCRYPTION_PROMPT);
            verify(printer).println(encryptionNotificationOf("jgnnq"));
        
            // (second invocation of CONTINUATION_PROMPT here)
            
            verify(printer).println(DONE_MESSAGE);
        }
    
        @Test
        public void test_ErrorHandling() throws IOException {
                            
            when(reader.readLine())
                .thenReturn("a") // incorrect cypher choice
                .thenReturn("10") // incorrect cypher choice
                .thenReturn("1") // correct cypher choice
                .thenReturn("a") // incorrect key choice
                .thenReturn("2") // correct key choice
                .thenReturn("e") // encrypt choice
                .thenReturn("hello") // message
                .thenReturn("foo") // incorrect continuation choice
                .thenReturn("q");
        
            userInterface.run();
        
            verify(printer, times(2)).println(CYPHER_TYPE_REPROMPT);
            verify(printer).println(CYPHER_TYPE_PROMPT);
            verify(printer).println(cypherTypeNotificationOf(CAESAR.str));
        
            verify(printer).println(CAESAR_KEY_PROMPT);
            verify(printer).println(CAESAR_KEY_REPROMPT);
            verify(printer).println(caesarKeyNotificationOf(2));
        
            verify(printer).println(ENCRYPTION_PROMPT);
            verify(printer).println(encryptionNotificationOf("jgnnq"));
    
            verify(printer).println(CONTINUATION_PROMPT);
            verify(printer).println(CONTINUATION_REPROMPT);
            
            verify(printer).println(DONE_MESSAGE);
        }
    }
    
    public static class HelperFunctions {
    
        private BufferedReader reader;
        private Printer printer;
        private UserInterface userInterface;
        
        @Before
        public void setup(){
            reader = mock(BufferedReader.class);
            printer = mock(Printer.class);
            userInterface = new UserInterface(reader, printer);
        }
        
        @Test
        public void test_getCyperType() throws IOException {
        
            doReturn("1").when(reader).readLine();
        
            assertThat("it sets the cyperType to CAESAR if user enters `1`",
                userInterface.getCypherType(""),
                is(equalTo(CAESAR)));
        
            doReturn("2").when(reader).readLine();
        
            assertThat("it sets the cyperType to NACL_SYMMETRIC if user enters `2`",
                userInterface.getCypherType(""),
                is(equalTo(NACL_SYMMETRIC))
            );
        }
        

        @Test
        public void test_getCypherType_errorHandling() throws IOException {
        
            when(reader.readLine())
                .thenReturn("a")
                .thenReturn("1");
            
            userInterface.getCypherType("");
            verify(printer).println(CYPHER_TYPE_REPROMPT);
        }
        
    
        @Test
        public void test_getCaesarKey() throws IOException {
            
            when(reader.readLine())
                .thenReturn("1");
        
            assertThat("it sets the key to inputed integer",
                userInterface.getCaesarKey(""),
                is(equalTo(new CaesarKey(1)))
            );
        }
        
        @Test
        public void test_getCaesarKey_errorHandling() throws IOException {
        
            when(reader.readLine())
                .thenReturn("a")
                .thenReturn("1"); // to avoid infinite loop
            
            userInterface.getCaesarKey("");
            verify(printer).println(CAESAR_KEY_REPROMPT);
        }
        
        @Test
        public void test_getCypherMode()throws IOException {
    
            when(reader.readLine())
                .thenReturn("e");
            
            assertThat("it sets the crypto mode to encrypt",
                userInterface.getCypherMode(""),
                is(equalTo(ENCRYPT))
                );
    
            when(reader.readLine())
                .thenReturn("d");

            assertThat("it sets the crypto mode to decrypt",
                userInterface.getCypherMode(""),
                is(equalTo(DECRYPT))
                );
        }
    
        @Test
        public void test_getCypherMode_errorHandling()throws IOException {
        
            when(reader.readLine())
                .thenReturn("foo")
                .thenReturn("e");
        
            userInterface.getCypherMode("");
            verify(printer).println(CYPHER_MODE_REPROMPT);
        }
        
        @Test
        public void test_getCypher() throws IOException {
            
            Key key = new CaesarKey(1);
            userInterface.cypherType = CAESAR;
            userInterface.key = key;
        
            assertThat("it generates a cypher of the type specified by user",
                userInterface.getCypher(),
                is(equalTo(new CaesarCypher((CaesarKey) key)))
            );

        }
    
        @Test
        public void test_getCypherText() throws IOException {
            
            when(reader.readLine())
                .thenReturn("hello");
            
            userInterface.cypher = new CaesarCypher(new CaesarKey(2));
        
            assertThat("it encrypts a message",
                userInterface.getCypherText(),
                is(equalTo("jgnnq"))
            );
        }
    
        @Test
        public void test_getClearText() throws IOException {
        
            when(reader.readLine())
                .thenReturn("jgnnq");
        
            userInterface.cypher = new CaesarCypher(new CaesarKey(2));
        
            assertThat("it decrypts a message",
                userInterface.getClearText(),
                is(equalTo("hello"))
            );
        }
            
        @Test
        public void test_getContinuation() throws IOException {
            
            when(reader.readLine())
                .thenReturn("c");
            
            userInterface.cypher = new CaesarCypher(new CaesarKey(2));
        
            assertThat("it encrypts a message",
                userInterface.getContinuation(""),
                is(equalTo(CONTINUE))
            );
        }
    }
}