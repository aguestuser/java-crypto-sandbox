package crypto;

import org.junit.Test;

import static crypto.CypherMode.DECRYPT;
import static crypto.CypherMode.ENCRYPT;
import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class CypherModeTest {
    
    @Test
    public void test_ENCRYPT(){
        assertThat("it wraps a name",
            ENCRYPT.name,
            is(equalTo("encrypt")));
        
        assertThat("it wraps a flag",
            ENCRYPT.flag,
            is(equalTo("e")));
    }
    
    @Test
    public void test_DECRYPT(){
        assertThat("it wraps a name",
            DECRYPT.name,
            is(equalTo("decrypt")));
    
        assertThat("it wraps a flag",
            DECRYPT.flag,
            is(equalTo("d")));
    }
}