package logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 The purpose of Logger is to...

 @author kasper
 */
public class Logger implements HttpHandler {

    @Override
    public void handle( HttpExchange t ) throws IOException  {
        try {
            System.out.println( "We got:" );
            t.getRequestHeaders().forEach(
                    (key,value) -> {System.out.println("Key: " + key + " value: " + value);}
            );
            readQuery(t);
            t.sendResponseHeaders( 204, -1 );
        } catch ( IOException ex ) {
            System.out.println( "Shit happened: " + ex.getMessage() );
            t.sendResponseHeaders( 500, -1);
        }
    }

    private void readQuery( HttpExchange t ) throws IOException {
        URI uri = t.getRequestURI();
        System.out.println( "URI query: " + uri.getRawQuery() );
        Map<String, String> params = splitQuery( t.getRequestURI() );
        System.out.println( "--- params: " );
        params.forEach( 
                (key,value) -> {System.out.println("Key: " + key + " value: " + value);}
        );
        System.out.println( "---- end params ----" );

    }
    
    public static Map<String, String> splitQuery(URI uri) throws UnsupportedEncodingException {
    Map<String, String> query_pairs = new HashMap<String, String>();
    String query = uri.getRawQuery();
    String[] pairs = query.split("&");
    for (String pair : pairs) {
        int idx = pair.indexOf("=");
        query_pairs.put(
                URLDecoder.decode(pair.substring(0, idx), "UTF-8"), 
                URLDecoder.decode(pair.substring(idx + 1), "UTF-8"));
    }
    return query_pairs;
}
}
