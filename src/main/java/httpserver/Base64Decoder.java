package httpserver;

import java.util.Base64;

public class Base64Decoder {

    public String decode(String encodedCredentials) {
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] bytes = decoder.decode(encodedCredentials);
        String decodedCredentials = new String(bytes);
        return decodedCredentials;
    }
}
