package httpserver;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class Base64DecoderTest {

    @Test
    public void Base64DecoderTest() {
        String encodedString = "Basic QWxhZGRpbjpvcGVuIHNlc2FtZQ==";
        Base64Decoder decoder = new Base64Decoder();
        String decodedString = decoder.decode(encodedString);

        assertThat(decodedString, is("Aladdin:open sesame"));

    }
}
