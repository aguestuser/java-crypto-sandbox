package ui.enums;

import org.junit.Test;

import static ui.enums.CypherType.ASYMMETRIC;
import static ui.enums.CypherType.SYMMETRIC;
import static org.junit.Assert.*;

public class CypherTypeTest {
    
    @Test
    public void test_SYMMETRIC_enum(){
    
        assertEquals("it wraps a string",
            "Symmetric Cypher",
            SYMMETRIC.str
        );
    
        assertEquals("it wraps a number",
            1,
            SYMMETRIC.num
        );
    }

    @Test
    public void test_ASYMMETRIC_enum(){

        assertEquals("it wraps a string",
            "Asymmetric Cypher",
            ASYMMETRIC.str
        );

        assertEquals("it wraps a number",
            2,
            ASYMMETRIC.num
        );
    }
}