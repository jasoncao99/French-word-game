import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.*;
import com.google.api.services.sheets.v4.Sheets;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Spreadsheets {
    /** Application name. */
    private static final String APPLICATION_NAME =
        "Google Sheets API Java Quickstart";

    //.replaceFirst("/test.jar", "")   PUT AFTER GET PATH
    /** Directory to store user credentials for this application. */
    private static final java.io.File DATA_STORE_DIR = new java.io.File(
    		Spreadsheets.class.getProtectionDomain().getCodeSource().getLocation().getPath().replaceFirst("/test.jar", "") , ".credentials/sheets.googleapis.com-java-quickstart" 
    );

    /** Global instance of the {@link FileDataStoreFactory}. */
    private static FileDataStoreFactory DATA_STORE_FACTORY;

    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY =
        JacksonFactory.getDefaultInstance();

    /** Global instance of the HTTP transport. */
    private static HttpTransport HTTP_TRANSPORT;

    /** Global instance of the scopes required by this quickstart.
     *
     * If modifying these scopes, delete your previously saved credentials
     * at ~/.credentials/sheets.googleapis.com-java-quickstart
     */
    private static final List<String> SCOPES =
        Arrays.asList(SheetsScopes.SPREADSHEETS);

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
        } catch (Throwable t) {            
            System.exit(1);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException {
        // Load client secrets.
        InputStream in =
            Spreadsheets.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
            GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(DATA_STORE_FACTORY)
                .setAccessType("offline")
                .build();
        Credential credential = new AuthorizationCodeInstalledApp(
            flow, new LocalServerReceiver()).authorize("user");
        return credential;
    }

    /**
     * Build and return an authorized Sheets API client service.
     * @return an authorized Sheets API client service
     * @throws IOException
     */
    public static Sheets getSheetsService() throws IOException {
        Credential credential = authorize();
        return new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }
    private static int size;
    
    //A:C is max range, returns a list from a row
    public static List<Object> read(String range,int num) {
    	
    	List<Object> name = null;
    	
    	try {
    	Sheets service = getSheetsService();
      
        String spreadsheetId = "1obGAphDnpr4pEQGWzwGt455zPQnnZijFlp3OhKNekww";
        ValueRange response = service.spreadsheets().values()
            .get(spreadsheetId, range)
            .execute();
        
        List<List<Object>> values = response.getValues();
        
        if (values == null || values.size() == 0) {
            System.out.println("No data found.");
        } else {
        }
        
        setSize(values.size());
        name = values.get(num);
        
    	} catch (IOException e) {
    		System.out.println("Sheets read failure");
    		System.exit(0);
    	}
		return name;
    }
    
    public static void setSize(int x){
    	size = x;
    }
    
    public static int getSize(){
		return size;
    }
    
    public static void append(List<Object> data1) {
    	try {
        // Build a new authorized API client service.
        Sheets service = getSheetsService();
        
        String spreadsheetId = "1obGAphDnpr4pEQGWzwGt455zPQnnZijFlp3OhKNekww";
        String range = "Sheet1!A1:B";

        List<List<Object>> data = new ArrayList<>();
        data.add (data1);
        
        ValueRange vr = new ValueRange();
        vr.setValues(data);
        
        service.spreadsheets().values().append(
        	    spreadsheetId, range,
        	    vr).setValueInputOption("RAW").execute();
    	} catch (IOException e) {
    		e.printStackTrace();
    		System.exit(0);
    	}
    }
}