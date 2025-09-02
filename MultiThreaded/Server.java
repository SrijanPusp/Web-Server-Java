import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class Server {

  public Consumer<Socket> getConsumer(){

    return (clientSocket)->{
      try {
        PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream());
        toClient.println("Hello From The Server");
        toClient.close();
        clientSocket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    };
  }
  public static void main(String[] args){
    int port = 8010;
    Server server = new Server(); 
    try {
      ServerSocket serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(10000);
      System.out.println("Server is Running on port: "+ port);
      while(true){
        Socket clientSocket = serverSocket.accept();
        Thread thread = new Thread(() -> server.getConsumer().accept(clientSocket));
        thread.start();
        //if I am closing the server here, naa aah, it's not behaving like multithreaded bro
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
