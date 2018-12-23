package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import functionalities.Main;

public class Server {

	public static void main(String[] args) throws IOException {
		@SuppressWarnings("resource")
		ServerSocket sersoc = new ServerSocket(1211);
		ExecutorService executor = Executors.newCachedThreadPool();

		while (true) {
			Socket soc = sersoc.accept();
			executor.submit(new Runnable() {
				public void run() {
					Main.mainx(soc);
				}
			});

		}

	}

}
