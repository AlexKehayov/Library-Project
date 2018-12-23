package functionalities;
import java.io.PrintStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	private static final String url = "jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String root = "alex";
	private static final String password = "prileptsi";
	
	private static Connection conn = null;
	private static ResultSet rs = null;
	private static ResultSet rs2 = null;
	private static ResultSet rs3 = null;
	private static Statement stmt = null;
	private static PreparedStatement prstmt = null;
	private static PreparedStatement prstmt2 = null;
	private static Scanner scan = new Scanner(System.in);
	
	public static void mainx(Socket soc) {
		
		Scanner socscan = null;
		
		try {
			
			socscan = new Scanner(soc.getInputStream());
			PrintStream outprint = new PrintStream(soc.getOutputStream());
			
			Object lock = new Object();
			boolean access= true;
			conn = DriverManager.getConnection(url, root, password);
			try {
				Login.login(conn, prstmt, socscan, outprint, rs, lock);
			} catch (AccessExc e) {
				access=false;
			}
			
			outprint.println("Menu:\n0.Exit\t1.New book\t2.Delete book\t3.Give book\t4.Return book\t5.Search book (title)"
					+ "\n6.Search book (combined)\t7.View all books\t8.New user\t9.Search users\t10.Search taken books\n"
					+ "11.View expired terms\t12.View users with books taken");
			
			for(int i = 0; i<4; i++) {
				socscan.nextLine();
			}
			
			while(access==true) {
				
				outprint.println("Please enter the number of the desired option:");
				String choice=socscan.nextLine();
				
				switch (choice) {
				case "0": outprint.println("See you soon!"); socscan.nextLine(); access=false; break;
				case "1": NewBook.newBook(socscan, outprint, conn, prstmt, lock); break;
				case "2": DeleteBook.deleteBook(socscan, outprint, conn, prstmt, prstmt2, rs, lock); break;
				case "3": GiveBook.giveBook(socscan, outprint, conn, prstmt, stmt, rs, rs2, rs3, lock); break;
				case "4": ReturnBook.returnBook(socscan, outprint, conn, stmt, rs, rs2, rs3, lock); break; // stignah do tuk, da opravq redovete 
				case "5": TitleSearch.titleSearch(socscan, outprint, conn, prstmt, rs, lock); break;//pri povtorno polzvane na f-cii(socscan.nextline)
				case "6": OverallSearch.overallSearch(socscan, outprint, conn, prstmt, rs, lock); break;
				case "7": ViewAllBooks.viewAllBooks(socscan, outprint, conn, prstmt, rs, lock); break;
				case "8": NewUser.newUser(socscan, outprint, conn, prstmt, lock); break;
				case "9": OverallSearchUser.overallSearchUser(socscan, outprint, conn, prstmt, rs, lock); break;
				case "10": SearchTaken.searchTaken(socscan, outprint, conn, prstmt, rs, lock); break;
				case "11": ViewExpired.viewExpired(socscan, outprint, conn, prstmt, rs, lock); break;
				case "12": ViewUsersTaken.viewUsersTaken(socscan, outprint, conn, stmt, rs, lock);
				
				default: outprint.println("Please choose a valid option"); socscan.nextLine(); 
				}
				
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}finally {
			scan.close();
			
				try {
					if(conn!=null) {
					conn.close();
					}
					if(rs!=null) {
						rs.close();
					}
					if(rs2!=null) {
						rs2.close();
					}
					if(rs3!=null) {
						rs3.close();
					}
					if(stmt!=null) {
						stmt.close();
					}
					if(prstmt!=null) {
						prstmt.close();
					}
					if(prstmt2!=null) {
						prstmt.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		
	}

}
