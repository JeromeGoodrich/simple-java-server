package httpserver.server;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServerConfigTest {

    @Test
    public void testConfigWithCLArgs() {
        String[] args = "-p 5000 -d /Users/admin/Documents/apprenticeship/java_server/cob_spec/public/".split(" ");
        ServerConfig config = new ServerConfig(args);

        assertThat(config.getPort(), is(5000));
        assertThat(config.getRootDir(), is("/Users/admin/Documents/apprenticeship/java_server/cob_spec/public/"));
    }
}
