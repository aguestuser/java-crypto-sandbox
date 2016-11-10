package ui;

import crypto.CaesarCypher;
import crypto.CaesarKey;
import crypto.Key;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.IOException;

import static crypto.CypherType.CAESAR;
import static crypto.CypherType.NACL_SYMMETRIC;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.junit.Assume.assumeThat;
import static org.mockito.Mockito.*;
import static ui.UserInterfaceMessages.*;

@RunWith(Enclosed.class)

public class UserInterfaceTest {
    
    public static class MainLoop {
    
    
        @Test
        public void test_encryptingWithCaeasar() throws IOException {
        
            BufferedReader reader = mock(BufferedReader.class);
            Printer printer = mock(Printer.class);
            UserInterface userInterface = new UserInterface(reader, printer);
            
            when(reader.readLine())
                .thenReturn("1")
                .thenReturn("2")
                .thenReturn("hello");
        
            userInterface.run();
        
            verify(printer).println(CYPHER_TYPE_PROMPT);
            verify(printer).println(cypherTypeNotificationOf(CAESAR.str));
        
            verify(printer).println(CAESAR_KEY_PROMPT);
            verify(printer).println(caesarKeyNotificationOf(2));
        
            verify(printer).println(CYPHER_TEXT_PROMPT);
            verify(printer).println(cyphertextNotificationOf("jgnnq"));
        
            verify(printer).println(DONE_MESSAGE);
        }
        
        @Test
        public void test_encryptingWithCaeasarErrorHandling() throws IOException {
            
            BufferedReader reader = mock(BufferedReader.class);
            Printer printer = mock(Printer.class);
            UserInterface userInterface = new UserInterface(reader, printer);
        
            when(reader.readLine())
                .thenReturn("a")
                .thenReturn("10")
                .thenReturn("1")
                .thenReturn("a")
                .thenReturn("2")
                .thenReturn("hello");
        
            userInterface.run();
        
            verify(printer, times(2)).println(CYPHER_TYPE_REPROMPT);
            verify(printer).println(CYPHER_TYPE_PROMPT);
            verify(printer).println(cypherTypeNotificationOf(CAESAR.str));
            
            verify(printer).println(CAESAR_KEY_PROMPT);
            verify(printer).println(CAESAR_KEY_REPROMPT);
            verify(printer).println(caesarKeyNotificationOf(2));
        
            verify(printer).println(CYPHER_TEXT_PROMPT);
            verify(printer).println(cyphertextNotificationOf("jgnnq"));
        
            verify(printer).println(DONE_MESSAGE);
        }
    }
        
    
    
    public static class HelperFunctions {
    
        private static BufferedReader reader = mock(BufferedReader.class);
        private static Printer printer = mock(Printer.class);
        private static UserInterface userInterface = new UserInterface(reader, printer);
        
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
        public void test_getCypherTypeErrorHandling() throws IOException {
        
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
        public void test_getCaesarKeyErrorHandling() throws IOException {
        
            when(reader.readLine())
                .thenReturn("a")
                .thenReturn("1");
            
            userInterface.getCaesarKey("");
            verify(printer).println(CAESAR_KEY_REPROMPT);
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
        
            doReturn("a").when(reader).readLine();
        
            assumeThat("it prompts for an int if user enters a non-numeric character",
                true,
                is(equalTo(true))
            );
        }
        
        @Test
        public void test_getCypherText() throws IOException {
            
            when(reader.readLine())
                .thenReturn("hello");
            
            userInterface.cypher = new CaesarCypher(new CaesarKey(2));
        
            assumeThat("it encrypts a message",
                userInterface.getCypherText(),
                is(equalTo("jgnnq"))
            );
        }
    }
}