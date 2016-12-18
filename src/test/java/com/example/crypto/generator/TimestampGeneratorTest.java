package com.example.crypto.generator;

import org.junit.Test;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class TimestampGeneratorTest {

    private TimestampGenerator gen = TimestampGenerator.INSTANCE;

    @Test
    public void test_formatting(){
        Pattern isUtcString = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z?$");
        String timestamp = gen.getTimestamp();

        assertTrue(isUtcString.matcher(timestamp).matches());
    }
}