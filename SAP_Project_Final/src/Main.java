import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Main {
	private static final String url = "jdbc:mysql://localhost:33061/sap_library?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static final String root = "alex";
	private static final String password = "prileptsi";

	private static Connection conn = null;
	private static Scanner scan = null;

	public static void main(String[] args) {
		try {
			boolean access = true;// false; ***
			conn = DriverManager.getConnection(url, root, password);
			scan = new Scanner(System.in);

			 StaffMember sm1 = new StaffMember(scan);
			 access = sm1.staffVerification(conn);
			 if(access) {
			 System.out.println("Welcome, " + sm1.getUsername());
			 }else {
			 System.out.println("Access denied!");
			 throw new Exception("No such user...");
			 }

			System.out.println(
					"\nMenu:\n0.Exit\n1.New book\n2.Delete book\n3.Give book\n4.Return book\n5.Search book (title)"
							+ "\n6.Search book (combined)\n7.View all books\n8.New user\n9.Search users\n10.Search taken books\n"
							+ "11.View expired terms\n12.View users with books taken\n");
			Book book;
			User user;
			Entry entry;
			while (access == true) {

				System.out.println("Please enter the number of the desired option:");
				String choice = scan.nextLine();

				switch (choice) {
				case "0":
					System.out.println("See you soon!");
					access = false;
					break;
				case "1":
					try {
						book = new Book(scan);
						book.addNewBook(conn, scan);
						scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "2":
					try {
						book = new Book(scan, 1);
						book.search(conn, scan, 1);
						book.delete(conn, scan);
						scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "3":
					try {
					entry = new Entry(scan, conn);
					entry.giveBook(scan, conn);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "4":
					try {
					entry = new Entry(scan);
					entry.search(scan, conn);
					Entry.returnBook(scan, conn);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "5":
					try {
					book = new Book(scan, 2);
					book.search(conn, scan, 3);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "6":
					try {
					book = new Book(scan, 1);
					book.search(conn, scan, 1);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "7":
					try {
					book = new Book();
					book.search(conn, scan, 2);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "8":
					try {
					user = new User(scan);
					user.addNewUser(conn, scan);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "9":
					try {
					user = new User(scan, 1);
					user.search(conn, scan);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "10":
					try {
					entry = new Entry(scan);
					entry.search(scan, conn);
					scan.nextLine();
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "11":
					try {
					Entry.viewExpired(conn);
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;
				case "12":
					try {
					Entry.viewUsersTaken(conn);
					} catch (Exception e) {
						System.out.println("A Problem Occured... Please check your input");
						System.out.println(e.getMessage());
					}
					break;

				default:
					System.out.println("Please choose a valid option");
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if (scan != null) {
				scan.close();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}

	}

}
