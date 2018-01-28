package logger;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLDecoder;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 The purpose of Logger is to...

 @author kasper
 */
public class Logger implements HttpHandler {

    private static final int NO_RESPONSE_BODY = -1;
    private LogFile logFile;

    public Logger() {
        logFile = new LogFile();
    }

    @Override
    public void handle( HttpExchange t ) throws IOException {
        try {
            URI uri = t.getRequestURI();
            System.out.println( "URI query: " + uri.getRawQuery() );

            Map<String, String> params = splitQuery( t.getRequestURI() );
            if ( params.containsKey( "log" ) ) {
                log( params.get( "log" ) );
            }
            t.sendResponseHeaders( 204, NO_RESPONSE_BODY );
        } catch ( IOException ex ) {
            try {
                System.out.println( "Exception: " + ex.getMessage() );
            } finally {
                t.sendResponseHeaders( 500, NO_RESPONSE_BODY );
            }
        }
    }

    private void log( String logMsg ) throws IOException {
        String date = String.valueOf( ZonedDateTime.now( ZoneOffset.UTC ) );
        logFile.log( date + " - " + logMsg );
    }

    public static Map<String, String> splitQuery( URI uri ) throws UnsupportedEncodingException {
        Map<String, String> query_pairs = new HashMap<String, String>();
        String query = uri.getRawQuery();
        String[] pairs = query.split( "&" );
        for ( String pair : pairs ) {
            int idx = pair.indexOf( "=" );
            query_pairs.put(
                    URLDecoder.decode( pair.substring( 0, idx ), "UTF-8" ),
                    URLDecoder.decode( pair.substring( idx + 1 ), "UTF-8" ) );
        }
        return query_pairs;
    }

}
