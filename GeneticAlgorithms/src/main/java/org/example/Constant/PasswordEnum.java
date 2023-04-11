package org.example.Constant;

public enum PasswordEnum {
    PASSWORD("ChatGPT and GPT-4");

    private final String value;

    PasswordEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
