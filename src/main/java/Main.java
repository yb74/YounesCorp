import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Establish database connection
        Connection connection = DatabaseConnector.connect();
        if (connection != null) {
            System.out.println("Connected to the PostgreSQL database!");

            // getFilmById(500);
            // createFilm();
            // showNotPreparedQueryResult("SELECT * FROM film");
            showPreparedQueryResult();
        } else {
            System.out.println("Failed to connect to the database.");
        }
    }

    public static void getFilmById(int id) throws SQLException {
        // connection to db
        DatabaseConnector app = new DatabaseConnector();
        Connection connection = app.connect();

        // Create a prepared statement with a query param denoted by "?"
        PreparedStatement stmt = connection.prepareStatement("select * from film where id>?");
        // Set the value of the param. Param indexes begin from 1
        // If we want to set the value as a string, we use `setString`
        // If we want to set the value as an integer, we use `setInt`
        // with the param index and param value as arguments
        stmt.setInt(1, id);

        // Execute the query and read the results same as before
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.print("Column 1 returned ");
            System.out.println(rs.getString(1)); // affiche la 1ère colonne de la table = id

            System.out.printf("id:%d titre:%s annee:%s%n", rs.getLong("id"),
                    rs.getString("titre"), rs.getString("annee"));
        }

        rs.close();
        stmt.close();
    }

    public static void createFilm() throws SQLException {
        DatabaseConnector app = new DatabaseConnector();
        Connection connection = app.connect();

        // Create a new insert statement with the bird and description values as query params
        PreparedStatement insertStmt =
                connection.prepareStatement("INSERT INTO film(titre, annee) VALUES (?, ?)");

        // Set the query params
        insertStmt.setString(1, "rooster");
        insertStmt.setString(2, "wakes you up in the morning");

        // Run the insert query using the `executeUpdate` method.
        // This returns the number of inserted rows
        int insertedRows = insertStmt.executeUpdate();
        // Print out the number of inserted rows
        System.out.printf("inserted %s bird(s)%n", insertedRows);
    }

    public static void showNotPreparedQueryResult(String query) throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.connect();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = resultSet.getString(i);
                String columnName = metaData.getColumnName(i);
                System.out.println(columnName + ": " + columnValue);
            }
            System.out.println();
        }

        resultSet.close();
        statement.close();
    }

    public static void showPreparedQueryResult() throws SQLException {
        DatabaseConnector databaseConnector = new DatabaseConnector();
        Connection connection = databaseConnector.connect();

        String query = "SELECT * FROM film WHERE annee = ? AND titre = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, 2022);
        statement.setString(2, "X");

        ResultSet resultSet = statement.executeQuery();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            for (int i = 1; i <= columnCount; i++) {
                String columnValue = resultSet.getString(i);
                String columnName = metaData.getColumnName(i);
                System.out.println(columnName + ": " + columnValue);
            }
            System.out.println();
        }

        resultSet.close();
        statement.close();
    }


    /*
    import java.sql.Connection;
    import java.sql.DriverManager;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.ResultSetMetaData;
    import java.sql.SQLException;

    public class SQLQueryExecutor {
        public static void executeQuery(String url, String user, String password, String sql, Object... params) {
            try (Connection connection = DriverManager.getConnection(url, user, password)) {
                PreparedStatement statement = connection.prepareStatement(sql);

                // Set parameters for the prepared statement
                for (int i = 0; i < params.length; i++) {
                    statement.setObject(i + 1, params[i]);
                }

                ResultSet resultSet = statement.executeQuery();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();

                while (resultSet.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        String columnValue = resultSet.getString(i);
                        String columnName = metaData.getColumnName(i);
                        System.out.println(columnName + ": " + columnValue);
                    }
                    System.out.println();
                }

                resultSet.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public static void main(String[] args) {
            String url = "jdbc:postgresql://localhost:5432/database_name";
            String user = "your_username";
            String password = "your_password";
            String sql = "SELECT * FROM your_table WHERE column1 = ? AND column2 = ?";

            // Set the parameters for the query
            Object[] params = {"value1", "value2"};

            executeQuery(url, user, password, sql, params);
        }
    }
 */
}