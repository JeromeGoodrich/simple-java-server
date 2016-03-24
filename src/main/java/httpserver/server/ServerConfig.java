package httpserver.server;


public class ServerConfig {

    private String[] args;
    private int port;
    private String rootDir;

    public ServerConfig(String[] args) {
        this.args = args;
        setPort();
        setRootDir();
    }

    private void setPort() {
        if (args[0].equals("-p")){
            port = Integer.parseInt(args[1]);
        } else {
            port = Integer.parseInt(System.getenv("PORT"));
        }
    }

    private void setRootDir() {
        if (args[2].equals("-d")) {
            rootDir = args[3];
        } else if (!(System.getenv("PUBLIC_DIR").equals(null))) {
            rootDir = System.getenv("PUBLIC_DIR");
        } else {
            System.getProperty("user.dir");
        }
    }

    public int getPort() {
        return port;
    }

    public String getRootDir() {
        return rootDir;
    }
}
