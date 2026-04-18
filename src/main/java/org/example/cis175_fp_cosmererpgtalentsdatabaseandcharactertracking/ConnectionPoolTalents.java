package org.example.cis175_fp_cosmererpgtalentsdatabaseandcharactertracking;

import java.sql.*;
import javax.sql.DataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ConnectionPoolTalents {

    private static ConnectionPoolTalents pool = null;
    private static DataSource dataSource = null;

    private ConnectionPoolTalents() {
        try {
            InitialContext ic = new InitialContext();
            dataSource = (DataSource) ic.lookup("java:/comp/env/jdbc/reader");
        } catch (NamingException e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized ConnectionPoolTalents getInstance() {
        if (pool == null) {
            pool = new ConnectionPoolTalents();
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