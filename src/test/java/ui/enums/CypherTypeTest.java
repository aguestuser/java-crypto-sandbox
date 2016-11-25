package ui.enums;

import org.junit.Test;

import static ui.enums.CypherType.CAESAR;
import static ui.enums.CypherType.NACL_SYMMETRIC;
import static org.junit.Assert.*;

public class CypherTypeTest {
    
    @Test
    public void test_CAESAR_enum(){
    
        assertEquals("it wraps a string",
            "Caesar Cypher",
            CAESAR.str
        );
    
        assertEquals("it wraps a string for CAESAR type",
            1,
            CAESAR.num
        );
    }
    
    @Test
    public void test_NACL_SYMMETRIC_enum(){
    
        assertEquals("it wraps a string",
            "NaCl Symmetric",
            NACL_SYMMETRIC.str
        );
    
        assertEquals("it wraps a string for CAESAR type",
            2,
            NACL_SYMMETRIC.num
        );
    }
}