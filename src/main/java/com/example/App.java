package com.example;

import com.example.crypto.util.ByteGenerator;
import com.example.ui.Printer;
import com.example.ui.UserInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class App {
    
    public static void main(String args[]) throws IOException {
        
        UserInterface ui = new UserInterface(
            new BufferedReader(new InputStreamReader(System.in)),
            new Printer(),
            ByteGenerator.INSTANCE);
        ui.run();
    }
}