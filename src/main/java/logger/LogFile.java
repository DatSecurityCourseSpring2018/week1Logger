package logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static java.nio.file.StandardOpenOption.*;

/**
 The purpose of LogFile is to...

 @author kasper
 */
public class LogFile {
    private final static Charset ENCODING = StandardCharsets.UTF_8;   
    private final static String LOG_PATH= "LOG_PATH";
    private final Path filePath;
    
    public LogFile() {
        String fp = System.getenv( LOG_PATH );
        if (fp == null ) {
            System.err.println( "LOG_PATH not defined" );
            System.exit( 1 );
        }
        System.out.println( "Logging to file: " + fp );
        filePath = Paths.get( fp );
    }

    void log( String line ) throws IOException {
        if ( !Files.exists( filePath )){
            Files.createFile( filePath );
        }
        try ( BufferedWriter writer = Files.newBufferedWriter( filePath, ENCODING, WRITE, APPEND ) ) {
            writer.write( line );
            writer.newLine();
        } 
    }
}
