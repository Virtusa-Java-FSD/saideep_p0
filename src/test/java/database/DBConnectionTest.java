package database;

import org.example.database.DBConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    void testConnectionNotNull() {
        Connection conn = DBConnection.getInstance();
        assertNotNull(conn, "Database connection should NOT be null");
    }

    @Test
    void testConnectionIsOpen() throws Exception {
        Connection conn = DBConnection.getInstance();
        assertFalse(conn.isClosed(), "Database connection should be OPEN");
    }

    @Test
    void testSingletonConnection() {
        Connection conn1 = DBConnection.getInstance();
        Connection conn2 = DBConnection.getInstance();

        assertSame(conn1, conn2, "DBConnection.getInstance() must return SAME instance");
    }
}
