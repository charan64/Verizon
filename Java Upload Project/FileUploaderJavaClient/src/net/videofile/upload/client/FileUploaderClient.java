package net.videofile.upload.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.util.EntityUtils;

/**
 * Following Method helps to upload files using POST requests 
 * with encryption type "multipart/form-data".
  */
public class FileUploaderClient {
	
	public static void main(String[] args) {
		
		// the file we want to upload
		File inFile = new File("C:\\Users\\admin\\Desktop\\Samplevideo.jpg");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(inFile);
			DefaultHttpClient httpclient = new DefaultHttpClient(new BasicHttpParams());
			
			// server back-end URL
			HttpPost httppost = new HttpPost("http://localhost:8080/FileUploaderRESTService-1/rest/upload");
			MultipartEntity entity = new MultipartEntity();

			// set the file input stream and file name as arguments
			entity.addPart("file", new InputStreamBody(fis, inFile.getName()));
			httppost.setEntity(entity);

			// execute the request
			HttpResponse response = httpclient.execute(httppost);
			
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String responseString = EntityUtils.toString(responseEntity, "UTF-8");
			
			System.out.println("[" + statusCode + "] " + responseString);
			
		} catch (ClientProtocolException e) {
			System.err.println("Unable to make connection");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Unable to read file");
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) fis.close();
			} catch (IOException e) {}
		}
	}
	
}
