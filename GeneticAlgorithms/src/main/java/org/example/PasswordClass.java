package org.example;

public class PasswordClass {
    private char[] passwordChars;

    public PasswordClass(String password){
        passwordChars = password.toCharArray();
    }

    public int fitnessFunction(char[] password){
        if(password.length != passwordChars.length) return -1;

        int nonMatchedCharCount = 0;

        for(int x = 0 ; x < passwordChars.length ; x++){
            if(passwordChars[x] != password[x]) nonMatchedCharCount++;
        }

        return nonMatchedCharCount;
    }
}