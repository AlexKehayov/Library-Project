package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import functionalities.AccessExc;
import functionalities.DeleteBook;
import functionalities.GiveBook;
import functionalities.Login;
import functionalities.NewBook;
import functionalities.NewUser;
import functionalities.OverallSearch;
import functionalities.OverallSearchUser;
import functionalities.ReturnBook;
import functionalities.SearchTaken;
import functionalities.TitleSearch;
import functionalities.ViewAllBooks;
import functionalities.ViewExpired;
import functionalities.ViewUsersTaken;

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

	public static void main(String[] args) {
		
		try {
			Object lock = new Object();
			boolean access= true;
			conn = DriverManager.getConnection(url, root, password);
			try {
				Login.login(conn, prstmt, scan, rs, lock);
			} catch (AccessExc e) {
				access=false;
			}
			
			System.out.println("\nMenu:\n0.Exit\n1.New book\n2.Delete book\n3.Give book\n4.Return book\n5.Search book (title)"
					+ "\n6.Search book (combined)\n7.View all books\n8.New user\n9.Search users\n10.Search taken books\n"
					+ "11.View expired terms\n12.View users with books taken\n");
			
			while(access==true) {
				
				System.out.print("Please enter the number of the desired option:");
				String choice=scan.nextLine();
				
				switch (choice) {
				case "0": System.out.println("See you soon!"); access=false; break;
				case "1": NewBook.newBook(scan, conn, prstmt, lock); break;
				case "2": DeleteBook.deleteBook(scan, conn, prstmt, prstmt2, rs, lock); break;
				case "3": GiveBook.giveBook(scan, conn, prstmt, stmt, rs, rs2, rs3, lock); break;
				case "4": ReturnBook.returnBook(scan, conn, stmt, rs, rs2, rs3, lock); break;
				case "5": TitleSearch.titleSearch(scan, conn, prstmt, rs, lock); break;
				case "6": OverallSearch.overallSearch(scan, conn, prstmt, rs, lock); break;
				case "7": ViewAllBooks.viewAllBooks(scan, conn, prstmt, rs, lock); break;
				case "8": NewUser.newUser(scan, conn, prstmt, lock); break;
				case "9": OverallSearchUser.overallSearchUser(scan, conn, prstmt, rs, lock); break;
				case "10": SearchTaken.searchTaken(scan, conn, prstmt, rs, lock); break;
				case "11": ViewExpired.viewExpired(scan, conn, prstmt, rs, lock); break;
				case "12": ViewUsersTaken.viewUsersTaken(scan, conn, stmt, rs, lock);
				
				default: System.out.println("Please choose a valid option\n");
				}
				
			}
			
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}catch (Exception e) {
			System.out.println(e.getMessage());
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
