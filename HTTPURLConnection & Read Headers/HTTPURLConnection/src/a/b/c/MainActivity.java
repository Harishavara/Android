package a.b.c;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;

public class MainActivity extends Activity implements OnClickListener {
	
	TextView outputText;
	Button btnPost;
	EditText etxtPost;

	String username = "tomcat";
	String password = "tomcat";
	
	String urlAddress = "http://192.168.0.36:8100/midp/hits";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
    	btnPost = (Button) findViewById(R.id.btnPost);
    	etxtPost = (EditText) findViewById(R.id.etxtPost);
    	
    	btnPost.setOnClickListener(this);
		
        String str = DownloadText(urlAddress);
        TextView outputText = (TextView) findViewById(R.id.txtOutput);
        outputText.setText(str);     
	 }
	
	private String DownloadText(String URL)
    {
        int BUFFER_SIZE = 2000;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return "";
        }
        
        InputStreamReader isr = new InputStreamReader(in);
        int charRead;
        String str = "";
        char[] inputBuffer = new char[BUFFER_SIZE];          
        try {
            while ((charRead = isr.read(inputBuffer))>0)
            {                    
                //---convert the chars to a String---
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                str += readString;
                inputBuffer = new char[BUFFER_SIZE];
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }    
        return str;        
    }
  
    private InputStream OpenHttpConnection(String urlString) 
    throws IOException
    {
        InputStream in = null;
        int response = -1;
               
        URL url = new URL(urlString); 
        
        Authenticator.setDefault(new Authenticator () {
            protected PasswordAuthentication getPasswordAuthentication() {
                return (new PasswordAuthentication(username, password.toCharArray()));
            }
        });
        
        URLConnection conn = url.openConnection();
          
        if (!(conn instanceof HttpURLConnection))                     
            throw new IOException("Not an HTTP connection");
        
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect(); 

            response = httpConn.getResponseCode();                 
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();                                 
            }                     
        }
        catch (Exception ex)
        {
            throw new IOException("Error connecting");            
        }
        return in;     
    }

	@Override
	public void onClick(View arg0) {
		
		String paraValue = etxtPost.getText().toString();
		
		String message = executePost(paraValue);
		Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
	}
	
	public String executePost(String paraValue) {
	     
    	URL url;
    	HttpURLConnection connection = null; 
    	
    	StringBuilder sb = new StringBuilder();
	     
    	try 
	    {
    		String param = "Post=" +URLEncoder.encode(paraValue,"UTF-8");
	    	 
	    	//Create connection
	        url = new URL(urlAddress);
	        connection = (HttpURLConnection)url.openConnection();
	        connection.setDoInput(true);
	        connection.setDoOutput(true);
	         
	        connection.setRequestMethod("POST");
	        connection.setFixedLengthStreamingMode(param.getBytes().length);
	        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        connection.setRequestProperty("Content-Length", ""+ param.length());
				 
			//Send request
			OutputStreamWriter wr = new OutputStreamWriter(connection.getOutputStream());
			wr.write(param);
			wr.flush();
			wr.close();

	    	sb.append(param);
	    }
	    catch (Exception e) 
	    {
	        e.printStackTrace();
	    }
	    finally
	    {
			if(connection != null) {
				connection.disconnect(); 
			}
        }
    	return sb.toString();
	}
}
