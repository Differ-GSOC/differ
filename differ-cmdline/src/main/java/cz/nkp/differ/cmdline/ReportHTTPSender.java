package cz.nkp.differ.cmdline;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.auth.params.AuthPNames;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Jan Stavel <stavel.jan@gmail.com>
 * Date: 30.1.13
 * Time: 6:51
 */
public class ReportHTTPSender {
    private String url;
    private String user;
    private String password;
    private static Logger logger = LogManager.getLogger(ReportHTTPSender.class);

    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return this.url;
    }
    public void setUser(String user){
         this.user = user;
    }
    public String getUser(){
         return this.user;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }

    public HttpResponse sendReport(File report){
        HttpResponse response = null;
        HttpPost httpPost = new HttpPost(this.url);
        HttpClient client = new DefaultHttpClient();

        String basic_auth = new String(Base64.encodeBase64((user + ":" + password).getBytes()));
        httpPost.addHeader("Authorization", "Basic " + basic_auth);

        FileEntity input = new FileEntity(report);
	    input.setContentType("application/xml");
		httpPost.setEntity(input);
		System.out.println("sending output to differ web");
        try {
        	logger.debug("try to connect differ web");
        	System.out.println("sending output to differ web - before");
            response = client.execute(httpPost);
            System.out.println(response.getStatusLine());
    		System.out.println("sending output to differ web - done");
        } catch (IOException e) {
            e.printStackTrace();
        }
        client.getConnectionManager().shutdown();
        return response;
    }
}
