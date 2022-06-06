package databaseConnection;


/**
 * Class that stores all necessary database connection properties.
 */
public class ConnectionDataStorage {

    /**
     * Port number for database connection.
     */
    public static String portNumber = "5432";

    /**
     * Name of the databse.
     */
    public static String databaseName = "javadatabase";

    /**
     * Login name for database connection.
     */
    public static String loginName = "postgres";

    /**
     * Password for database connection.
     */
    public static String password = "bazadanych";

    public ConnectionDataStorage() {
    }

    /**
     * Get port number stored in this data storage.
     *
     * @return Port.
     */
    public static String getPortNumber() {
        return portNumber;
    }

    /**
     * Set port number to be stored in this data storage.
     *
     * @param portNumber Port number to be stored.
     */
    public static void setPortNumber(String portNumber) {
        ConnectionDataStorage.portNumber = portNumber;
    }

    /**
     * Get database name stored in this data storage.
     *
     * @return Name of the database.
     */
    public static String getDatabaseName() {
        return databaseName;
    }

    /**
     * Set database name to be stored in this data storage.
     *
     * @param databaseName Name of database to be stored.
     */
    public static void setDatabaseName(String databaseName) {
        ConnectionDataStorage.databaseName = databaseName;
    }

    /**
     * Get login name stored in this data storage.
     *
     * @return Login name.
     */
    public static String getLoginName() {
        return loginName;
    }

    /**
     * Set login name to be stored in this data storage.
     *
     * @param loginName Login name to be stored.
     */
    public static void setLoginName(String loginName) {
        ConnectionDataStorage.loginName = loginName;
    }

    /**
     * Get password stored in this data storage.
     *
     * @return Raw password.
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Set password to be stored in this data storage.
     *
     * @param password Password to be stored.
     */
    public static void setPassword(String password) {
        ConnectionDataStorage.password = password;
    }
}




