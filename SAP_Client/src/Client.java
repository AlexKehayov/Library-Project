import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		@SuppressWarnings("resource")
		Socket socket = new Socket("127.0.0.1", 1211);
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		@SuppressWarnings("resource")
		Scanner socscan = new Scanner(socket.getInputStream());
		PrintStream printout = new PrintStream(socket.getOutputStream());
		
		System.out.println("Input username:");
		String input = scan.nextLine();
		printout.println(input);
		
		while(true) {
			System.out.println(socscan.nextLine());
			input = scan.nextLine();
			printout.println(input);
			printout.flush();
		}
		

	}

}
