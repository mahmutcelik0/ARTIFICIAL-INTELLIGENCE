package org.example;

import org.example.Constant.PasswordEnum;

/*
* PasswordEnum içerisinde verdiğimiz string password u char array ine dönüştürür ve fitness function içerir
* */
public class PasswordClass {
    private static final char[] passwordChars = PasswordEnum.PASSWORD.getValue().toCharArray();

    public PasswordClass(){}

    /*
    * Fitness function içerisine gene char array i yollanır ve bizim verdiğimiz sonrasında da char array ine dönüşmüş
    * password ile eşleşme sayıları karşılaştırılır. Eşleşmeyen karakter sayısı geri döndürülür
    * Password in PasswordEnum -> ChatGPT
    * password parameter       -> CqwtGPT
    * Returns -> 2
    * */
    public int fitnessFunction(char[] password){
        if(password.length != passwordChars.length) return -1;

        int nonMatchedCharCount = 0;

        for(int x = 0 ; x < passwordChars.length ; x++){
            if(passwordChars[x] != password[x]) nonMatchedCharCount++;
        }

        return nonMatchedCharCount;
    }
}