package logger;

import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.InetSocketAddress;

/**
 This server is based on:
 https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/package-summary.html.

 The server assumes the environment variable LOG_PATH is set to a file
where it has the permissions to create and append to that file.

 @author kasper
 */
public class Server {

    private static int DEFAULT_BACKLOG = -1;
    private static int PORT = 8765;

    public static void main( String[] args ) throws IOException {
        HttpServer server = HttpServer.create( new InetSocketAddress( PORT ), DEFAULT_BACKLOG );
        server.createContext( "/logger", new Logger() );
        server.setExecutor( null ); // creates a default executor
        System.out.println( "Starting sever on: " + PORT );
        server.start();
    }
}
