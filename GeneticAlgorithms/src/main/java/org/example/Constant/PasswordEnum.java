package org.example.Constant;

/*
 * Proje genelinde kullanılan şifre değerinin tek bir yerden erişilebilmesi
 * ve kolaylıkla tutarlı bir şekilde değiştirilebilmesi için enum içerisinde saklandı
 * Şifrenin değiştirilmesi sonucunda kromozomdaki gen sayısı da Constants içerisindeki enum içerisinde
 * otomatik olarak güncelleniyor
 * */
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
