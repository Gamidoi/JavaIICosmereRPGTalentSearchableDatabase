package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPoolCharacters {

    private static ConnectionPoolCharacters pool = null;
    private static DataSource dataSource = null;

    private ConnectionPoolCharacters() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/character");
        } catch (NamingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized ConnectionPoolCharacters getInstance() {
        if (pool == null) {
            pool = new ConnectionPoolCharacters();
        }
        return pool;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void freeConnection(Connection c) {
        try {
            c.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}