package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

import credentials.Credentials;
import entries.BookEntryServicesImpl;
import interfaces.EntryServices;
import interfaces.LibItemServices;
import interfaces.UserServices;
import lib_items.BookLibItemServicesImpl;
import staffMembers.StaffMemberStaffServicesImpl;
import users.UserUserServiceImpl;

public class Main {

	private static Connection conn = null;
	private static Scanner scan = null;

	public static void main(String[] args) {

		try {
			Credentials cred = new Credentials();
			boolean access = true;
			conn = DriverManager.getConnection(cred.getUrl(), cred.getRoot(), cred.getPassword());
			scan = new Scanner(System.in);

			access = new StaffMemberStaffServicesImpl().staffVerification(conn, scan);
			if (!access) {
				System.out.println("Access denied!");
				throw new Exception("No such user...");
			}
			
			EntryServices entryServices = new BookEntryServicesImpl();
			LibItemServices bookServices = new BookLibItemServicesImpl();
			UserServices userServices = new UserUserServiceImpl();
			
			System.out.println(
					"\nMenu:\n0.Exit\n1.New book\n2.Delete book\n3.Give book\n4.Return book\n5.Search book (title)"
							+ "\n6.Search book (combined)\n7.View all books\n8.New user\n9.Search users\n10.Search taken books\n"
							+ "11.View expired terms\n12.View users with books taken\n");

			while (access == true) {

				System.out.println("Please enter the number of the desired option:");
				String choice = scan.nextLine();
				try {
					switch (choice) {
					case "0":
						System.out.println("See you soon!");
						access = false;
						break;
					case "1":
						bookServices.addNew(conn, scan);
						break;
					case "2":
						bookServices.delete(conn, scan);
						break;
					case "3":
						entryServices.give(scan, conn);
						break;
					case "4":
						entryServices.getBack(scan, conn);
						break;
					case "5":
						bookServices.search(conn, scan, 3);
						break;
					case "6":
						bookServices.search(conn, scan, 1);
						break;
					case "7":
						bookServices.search(conn, scan, 2);
						break;
					case "8":
						userServices.addNewUser(conn, scan);
						break;
					case "9":
						userServices.search(conn, scan);
						break;
					case "10":
						entryServices.search(scan, conn);
						break;
					case "11":
						entryServices.viewExpired(conn);
						break;
					case "12":
						entryServices.viewUsersTaken(conn);
						break;

					default:
						System.out.println("Please choose a valid option");
					}
					scan.nextLine();
				} catch (Exception e) {
					System.out.println("A Problem Occured... Please check your input");
					System.out.println(e.getMessage());
					e.printStackTrace();
				}

			}

		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println(e.getMessage());
		} finally {
			if (scan != null) {
				scan.close();
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// e.printStackTrace();
					System.out.println(e.getMessage());
				}
			}
		}

	}

}
