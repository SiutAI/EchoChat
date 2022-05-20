import java.io.InputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

class Client implements Runnable {
    Socket socket;

    Scanner in;
    PrintStream out;
    ChatServer server;
    String name;

    public Client(Socket socket, ChatServer server){

        this.socket = socket;
        this.server = server;
        // запускаем клиент
        new Thread(this).start();
    }

    void receive(String message) {
        out.println(message);
    }

    public void run() {
        try {
            // получаем потоки ввода и вывода
            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            // создаем удобные средства ввода и вывода
            in = new Scanner(is);
            out = new PrintStream(os);

            // читаем из сети и пишем в сеть
            out.println("Welcome to chat!");
            out.println("How we can name you?");
            name = in.nextLine();

            String input = in.nextLine();
            while (!input.equals("bye")) {
                server.sendAll(name + ": " + input);
                input = in.nextLine();
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}