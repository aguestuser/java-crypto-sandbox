package com.example.ui.enums;

public enum Continuation {
    MAKE_KEY("make new key", "k"),
    ENCRYPT_MESSAGE("encrypt message", "e"),
    DECRYPT_MESSAGE("decrypt message", "d"),
    QUIT("quit", "q");
    
    public final String name;
    public final String flag;
    
    Continuation(String name, String flag) {
        this.name = name;
        this.flag = flag;
    }
}
