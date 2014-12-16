package com.example.testeandroidcomphp;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {
	EditText edtNome, edtSobrenome, edtEmail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}
	
	public void enviarDados(View view) {
		
		new Thread(){
			public void run() {
				edtNome = (EditText)findViewById(R.id.edtNome);
				edtSobrenome = (EditText)findViewById(R.id.edtSobrenome);
				edtEmail = (EditText)findViewById(R.id.edtEmail);
				
				postHttp(edtNome.getText().toString(), edtSobrenome.getText().toString(), edtEmail.getText().toString());
								
			}
		}.start();
		
	}
	
	public void  postHttp(String nome, String sobrenome, String email) {
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost("http://www.tairoroberto.kinghost.net/teste");
		
		try {
			ArrayList<NameValuePair> valores = new ArrayList<NameValuePair>();
			valores.add(new BasicNameValuePair("nome", nome));
			valores.add(new BasicNameValuePair("sobrenome", sobrenome));
			valores.add(new BasicNameValuePair("email", email));
			
			//prepara dados para envias ao webservice
			post.setEntity(new UrlEncodedFormEntity(valores));
			
			//envia para o webservice
			final HttpResponse response = client.execute(post);
			
			//roda a resposta na thread principal
			runOnUiThread(new Runnable() {				
				@Override
				public void run() {
					try {
						Toast.makeText(getBaseContext(), EntityUtils.toString(response.getEntity()), Toast.LENGTH_SHORT).show();
						edtNome.setText("");
						edtSobrenome.setText("");
						edtEmail.setText("");
						
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (ClientProtocolException e) {}
		  catch (IOException e) {}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
