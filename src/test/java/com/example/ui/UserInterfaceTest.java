package com.example.ui;

import com.example.crypto.generator.ByteGenerator;
import com.example.crypto.message.EncryptedMessageWithNonce;
import com.example.crypto.cypher.SymmetricCypher;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.mockito.InOrder;

import java.io.BufferedReader;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;
import static support.CryptoFixtures.SecretBox.*;
import static com.example.ui.UserInterface.State.*;
import static com.example.ui.UserInterfaceMessages.*;
import static com.example.ui.enums.CypherMode.DECRYPT;
import static com.example.ui.enums.CypherMode.ENCRYPT;
import static com.example.ui.enums.CypherType.SYMMETRIC;

@RunWith(Enclosed.class)

public class UserInterfaceTest {

    private static class TestEnvironment{

        BufferedReader reader;
        Printer printer;
        ByteGenerator byteGenerator;
        UserInterface userInterface;

        TestEnvironment(){

            this.reader = mock(BufferedReader.class);
            this.printer = mock(Printer.class);
            this.byteGenerator = mock(ByteGenerator.class);

            when(byteGenerator.generateSecretKeyBytes()).thenReturn(SECRET_KEY_BYTES);
            when(byteGenerator.generateNonceBytes()).thenReturn(NONCE_BYTES);

            this.userInterface = new UserInterface(reader, printer, byteGenerator);
        }
    }

    public static class MainLoop {

        TestEnvironment env;

        @Before
        public void setup(){
            env = new TestEnvironment();
        }

        @Test
        public void test_symmetricEncryption() throws IOException {

            when(env.reader.readLine())
                .thenReturn("y") // key generator
                .thenReturn("e") // encrypt choice
                .thenReturn(SECRET_KEY_HEX) // secret key input
                .thenReturn(CLEARTEXT) // message input
                .thenReturn("q"); // continuation input

            env.userInterface.run();

            InOrder inOrder = inOrder(env.printer);

            inOrder.verify(env.printer).println(MAKE_NEW_KEY_PROMPT);
            inOrder.verify(env.printer).println(secretKeyNotificationOf(SECRET_KEY_HEX));

            inOrder.verify(env.printer).println(CYPHER_MODE_PROMPT);
            inOrder.verify(env.printer).println(cypherTypeNotificationOf(SYMMETRIC.str));

            inOrder.verify(env.printer).println(ENCRYPT_TO_SECRET_KEY_PROMPT);
            inOrder.verify(env.printer).println(encryptionNotificationOf(CYPHERTEXT_HEX, NONCE_HEX));

            inOrder.verify(env.printer).println(CONTINUATION_PROMPT);
        }


        @Test
        public void test_symmetricDecryption() throws IOException {

            when(env.reader.readLine())
                .thenReturn("y") // key generator
                .thenReturn("d") // encrypt choice
                .thenReturn(SECRET_KEY_HEX) // secret key input
                .thenReturn(CYPHERTEXT_HEX) // message input
                .thenReturn(NONCE_HEX) // nonce input
                .thenReturn("q"); // continuation input

            env.userInterface.run();

            InOrder inOrder = inOrder(env.printer);

            inOrder.verify(env.printer).println(MAKE_NEW_KEY_PROMPT);
            inOrder.verify(env.printer).println(secretKeyNotificationOf(SECRET_KEY_HEX));

            inOrder.verify(env.printer).println(CYPHER_MODE_PROMPT);
            inOrder.verify(env.printer).println(cypherTypeNotificationOf(SYMMETRIC.str));

            inOrder.verify(env.printer).println(DECRYPT_WITH_SECRET_KEY_PROMPT);
            inOrder.verify(env.printer).println(DECRYPT_CYPHERTEXT_PROMPT);
            inOrder.verify(env.printer).println(DECRYPT_NONCE_PROMPT);
            inOrder.verify(env.printer).println(decryptionNotificationOf(CLEARTEXT));

            inOrder.verify(env.printer).println(CONTINUATION_PROMPT);

        }

        @Test
        public void test_symmetricEncryptionAndDecrpytion() throws IOException {

            when(env.reader.readLine())
                .thenReturn("y") // key generator
                .thenReturn("e") // mode choice: encrypt
                .thenReturn(SECRET_KEY_HEX) // secret key input
                .thenReturn(CLEARTEXT) // message input
                .thenReturn("d") // continuation choice: decrypt
                .thenReturn(SECRET_KEY_HEX) // secret key input
                .thenReturn(CYPHERTEXT_HEX) // cyphertext input
                .thenReturn(NONCE_HEX) // nonce input
                .thenReturn("q"); // continuation choice: quit

            env.userInterface.run();

            InOrder inOrder = inOrder(env.printer);

            inOrder.verify(env.printer).println(MAKE_NEW_KEY_PROMPT);
            inOrder.verify(env.printer).println(secretKeyNotificationOf(SECRET_KEY_HEX));

            inOrder.verify(env.printer).println(CYPHER_MODE_PROMPT);
            inOrder.verify(env.printer).println(cypherTypeNotificationOf(SYMMETRIC.str));

            inOrder.verify(env.printer).println(ENCRYPT_TO_SECRET_KEY_PROMPT);
            inOrder.verify(env.printer).println(encryptionNotificationOf(CYPHERTEXT_HEX, NONCE_HEX));

            inOrder.verify(env.printer).println(CONTINUATION_PROMPT);


        }
    }

    public static class HelperFunctions {

        TestEnvironment env;

        @Before
        public void setup(){
            env = new TestEnvironment();
        }

        @Test
        public void test_getCyperType() throws IOException {

            doReturn("1").when(env.reader).readLine();

            assertThat("it sets the cyperType to SYMMETRIC if user enters `1`",
                env.userInterface.getCypherType(""),
                is(equalTo(SYMMETRIC)));

        }

        @Test
        public void test_getCypherType_errorHandling() throws IOException {

            when(env.reader.readLine())
                .thenReturn("a")
                .thenReturn("1");

            env.userInterface.getCypherType("");
            verify(env.printer).println(CYPHER_TYPE_REPROMPT);
        }

        @Test
        public void test_getCypherMode()throws IOException {

            when(env.reader.readLine())
                .thenReturn("e");

            assertThat("it sets the crypto mode to encrypt",
                env.userInterface.getCypherMode(""),
                is(equalTo(ENCRYPT))
                );

            when(env.reader.readLine())
                .thenReturn("d");

            assertThat("it sets the crypto mode to decrypt",
                env.userInterface.getCypherMode(""),
                is(equalTo(DECRYPT))
                );
        }

        @Test
        public void test_getCypherMode_errorHandling()throws IOException {

            when(env.reader.readLine())
                .thenReturn("foo")
                .thenReturn("e");

            env.userInterface.getCypherMode("");
            verify(env.printer).println(CYPHER_MODE_REPROMPT);
        }

        @Test
        public void test_encryptClearText() throws IOException {

            env.userInterface.cypher = new SymmetricCypher(env.byteGenerator);

            when(env.reader.readLine())
                .thenReturn(SECRET_KEY_HEX)
                .thenReturn(CLEARTEXT);

            assertThat(
                env.userInterface.encryptClearText(),
                is(equalTo(new EncryptedMessageWithNonce(CYPHERTEXT_HEX, NONCE_HEX)))
            );
        }

        @Test
        public void test_decryptCypherText() throws IOException {

            env.userInterface.cypher = new SymmetricCypher(env.byteGenerator);

            when(env.reader.readLine())
                .thenReturn(SECRET_KEY_HEX)
                .thenReturn(CYPHERTEXT_HEX)
                .thenReturn(NONCE_HEX);

            assertThat(
                env.userInterface.decryptCypherText(),
                is(equalTo(CLEARTEXT))
            );
        }

        @Test
        public void test_getContinuation() throws IOException {

            when(env.reader.readLine()).thenReturn("k");

            assertThat("it allows user to make new key",
                env.userInterface.getContinuation(""),
                is(equalTo(GETTING_KEY))
            );

            when(env.reader.readLine()).thenReturn("e");

            assertThat("it allows user to encrypt new message",
                env.userInterface.getContinuation(""),
                is(equalTo(ENCRYPTING))
            );

            when(env.reader.readLine()).thenReturn("d");

            assertThat("it allows user to decrypt new message",
                env.userInterface.getContinuation(""),
                is(equalTo(DECRYPTING))
            );

            when(env.reader.readLine()).thenReturn("q");

            assertThat("it allows user to decrypt new message",
                env.userInterface.getContinuation(""),
                is(equalTo(DONE))
            );
        }

        @Test
        public void test_getContinuation_errorHandling() throws IOException {

            when(env.reader.readLine())
                .thenReturn("MANIAC!")
                .thenReturn("q");

            env.userInterface.getContinuation("");

            verify(env.printer).println(CONTINUATION_REPROMPT);
        }
    }
}