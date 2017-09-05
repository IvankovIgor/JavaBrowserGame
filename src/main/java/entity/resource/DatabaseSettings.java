package entity.resource;

public class DatabaseSettings implements Resource {
    private String dialect;
    private String driver;
    private String url;
    private String type;
    private String host;
    private int port;
    private String schema;
    private String user;
    private String password;
    private String showSql;
    private String mode;

    public String getDialect() {
        return dialect;
    }

    public String getDriver() {
        return driver;
    }

    public String getUrl() {
        return url;
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getSchema() {
        return schema;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getShowSql() {
        return showSql;
    }

    public String getMode() {
        return mode;
    }
}
