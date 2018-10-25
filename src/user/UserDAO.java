package user;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class UserDAO {



	private Connection conn;
	private ResultSet rs;

	public UserDAO() {

		try {

			String dbURL = "jdbc:mysql://localhost:3306/LectureEvaluation";

			String dbID = "root";

			String dbPassword = "root";

			Class.forName("com.mysql.jdbc.Driver");

			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	

	public int login(String userID, String userPassword) {

		String SQL = "SELECT userPassword FROM user WHERE userID = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, userID);

			rs = pstmt.executeQuery();

			if(rs.next()) {

				if(rs.getString(1).equals(userPassword))

					return 1; // success login

				else

					return 0; // wrong password

			}

			return -1; // non-existed id

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return -2; // error on DB

	}

	

	public int join(UserDTO user) {

		String SQL = "INSERT INTO user VALUES (?, ?, ?, ?, false)";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, user.getUserID());

			pstmt.setString(2, user.getUserPassword());

			pstmt.setString(3, user.getUserEmail());

			pstmt.setString(4, user.getUserEmailHash());

			return pstmt.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return -1; // fail to sign up

	}

	

	public String getUserEmail(String userID) {

		String SQL = "SELECT userEmail FROM user WHERE userID = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, userID);

			rs = pstmt.executeQuery();

			while(rs.next()) {

				return rs.getString(1); // return email 

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return null; // DB error 

	}

	

	public boolean getUserEmailChecked(String userID) {

		String SQL = "SELECT userEmailChecked FROM user WHERE userID = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, userID);

			rs = pstmt.executeQuery();

			while(rs.next()) {

				return rs.getBoolean(1); // Return the registration results

			}

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return false; // DB error

	}

	

	public boolean setUserEmailChecked(String userID) {

		String SQL = "UPDATE USER SET userEmailChecked = true WHERE userID = ?";

		try {

			PreparedStatement pstmt = conn.prepareStatement(SQL);

			pstmt.setString(1, userID);

			pstmt.executeUpdate();

			return true; // Success email registration

		} catch (SQLException e) {

			e.printStackTrace();

		}

		return false; // Fail on email registration

	}

}

