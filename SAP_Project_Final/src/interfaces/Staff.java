package interfaces;

import java.sql.Connection;
import java.sql.SQLException;

public interface Staff {
	public boolean staffVerification(Connection myConn) throws SQLException;
	public boolean equals(Object obj);
	public int hashCode();
}
