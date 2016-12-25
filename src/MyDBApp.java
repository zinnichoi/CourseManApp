import java.sql.*;

/**
 * @overview A database interface program that perform common database interface tasks.
 * An application needs only to invoke methods of this program, passing suitable
 * parameters.
 * @attribute :
 * connecttion : Connection
 * DRIVER_POSTGRESQL : String
 */
public class MyDBApp {
    public Connection connection;
    /**
     * the database driver for PostgreSQL database
     */
    public static final String DRIVER_POSTGRESQL = "jdbc:postgresql:%s";

    /**
     * @effects create a new <tt>DBApp</tt> object for <tt>database driver</tt>
     */
    public MyDBApp() throws InternalError {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("can not load database driver");
        }
    }

    /**
     * @requires database <tt>dbName</tt> must have already been created.
     * @modifies <tt>this</tt>
     * @effects Connect to database <tt>dbName</tt> using the database driver
     * <tt>dbDriver</tt>.
     * <p>
     * <p><pre>If succeeded
     *          return true
     *        else
     *          return false</pre>
     */
    public boolean connect(String dbName) {
        try {
            String connectString = String.format(DRIVER_POSTGRESQL, dbName);
            connection = DriverManager.getConnection(connectString, "postgres", "postgres");
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * @requires database <tt>dbName</tt> must have already been created.
     * @modifies <tt>this</tt>
     * @effects Connect to database <tt>dbName</tt> using the database driver
     * <tt>dbDriver</tt>.
     * <p>
     * <p><pre>If succeeded
     *          return true
     *        else
     *          return false</pre></p>
     */
    public boolean connect(String dbName, String userName, String password) {
        if (userName == null && password == null) {
            return connect(dbName);
        }
        try {
            String connectString = String.format(DRIVER_POSTGRESQL, dbName);
            connection = DriverManager.getConnection(connectString, userName, password);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    /**
     * @effects executes the SELECT statement <tt>sql</tt>.
     * <p>
     * <p><pre>If succeeded
     *    return a <tt>String</tt> containing an <b>Html</b> table representation of the result set; or
     *    "empty" if the result set is empty
     *  else
     *    return null</pre>
     */
    public String selectToHtml(String sql) {
        try (Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery(sql);
            return resultSetToHtml(rs);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @effects executes the SELECT statement <tt>sql</tt>.
     * <pre>If succeeded
     *    return a <tt>String</tt> representation of the result set;
     *  else
     *    return null</pre>
     */
    public String selectToString(String sql) {
        try (Statement s = connection.createStatement()) {
            ResultSet rs = s.executeQuery(sql);
            return resultSetToString(rs);
        } catch (SQLException e) {
            return null;
        }
    }


    /**
     * @effects insert data into a table using INSERT statement <tt>sql</tt>.
     * <p><pre>If succeeded
     *    return true
     *  else
     *    return false</pre>
     */
    public boolean insert(String sql) {
        try (Statement s = connection.createStatement()) {
            s.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @effects update data in a table using UPDATE statement <tt>sql</tt>.
     * <pre>If succeeded
     *    return true
     *  else
     *    display an error message
     *    return false</pre>
     */
    public boolean update(String sql) {
        try (Statement s = connection.createStatement()) {
            s.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @effects delete <b>some</b> data rows from a table using DELETE statement <tt>sql</tt>.
     * <p><pre>If succeeded
     *    return true
     *  else
     *    return false</pre>
     */
    public boolean delete(String sql) {
        try (Statement s = connection.createStatement()) {
            s.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * @effects Return a string containing a sequence of rows of <tt>rs</tt>, one per line,
     * or return <tt>empty</tt> if no rows are found.
     */
    private String resultSetToString(ResultSet resultSet) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        //number of column
        int columnCount = resultSet.getMetaData().getColumnCount();
        int size = 0;
        while (resultSet.next()) {
            size++;
            for (int i = 0; i < columnCount; ) {
                stringBuilder.append(resultSet.getString(i + 1));
                if (++i < columnCount) stringBuilder.append(",");
            }
            stringBuilder.append("\r\n");
        }

        if (size == 0) {
            return "empty";
        }
        return stringBuilder.toString();
    }





    /**
     * @effects Return an Html table string containing a
     * header row and a sequence of rows in <tt>rs</tt> one per line
     */
    private String resultSetToHtml(ResultSet rs) throws SQLException {
        StringBuilder stringBuilder = new StringBuilder();
        ResultSetMetaData md = rs.getMetaData();
        // number of column
        int count = md.getColumnCount();
        stringBuilder.append("<meta content=\"text/html; charset=utf-8\" http-equiv=\"Content-Type\" />");
        stringBuilder.append("\r\n");
        stringBuilder.append("<table border=1>");
        stringBuilder.append("<tr>");

        for (int i = 1; i <= count; i++) {
            stringBuilder.append("<th>");
            stringBuilder.append(md.getColumnLabel(i));
        }
        stringBuilder.append("\r\n");
        stringBuilder.append("</tr>");
        stringBuilder.append("\r\n");

        while (rs.next()) {
            stringBuilder.append("<tr>");
            for (int i = 1; i <= count; i++) {
                stringBuilder.append("<td>");
                stringBuilder.append(rs.getString(i));
            }
            stringBuilder.append("</tr> ");
            stringBuilder.append("\r\n");
        }

        stringBuilder.append("</table>");
        stringBuilder.append("\r\n");

        return stringBuilder.toString();
    }

    /**
     * @effects close connection to database
     */
    public void close() {
        try {
            this.connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}



