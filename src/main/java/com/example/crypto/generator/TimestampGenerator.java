package com.example.crypto.generator;

import java.time.Instant;

public class TimestampGenerator {

    // singleton
    final static TimestampGenerator INSTANCE = new TimestampGenerator();
    private TimestampGenerator(){}

    public String getTimestamp(){
        return Instant.now().toString();
    }

}
