package com.newproject.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Login_Activity extends AppCompatActivity {
    EditText ed1, ed2;
    Button login;
    TextView register_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        ed1=findViewById(R.id.username);
        ed2=findViewById(R.id.password);
        login=findViewById(R.id.login);
        register_view=findViewById(R.id.register_button);
        Prefmanager prefmanager=new Prefmanager(getApplicationContext());
        if (prefmanager.UserLogin()){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            return;
        }
        register_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             startActivity(new Intent(getApplicationContext(),Signup_Activity.class));
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=ed1.getText().toString();
                String password=ed2.getText().toString();
                new Login().execute(username,password);
            }
        });
    }

    private class Login extends AsyncTask<String,String,String>{
        URL url=null;
        HttpURLConnection connection;
        ProgressDialog progressDialog=new ProgressDialog(Login_Activity.this);
        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("LOADING");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://192.168.0.106/Android/login.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection=(HttpURLConnection)url.openConnection();
                connection.setDoOutput(true);
                connection.setDoInput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("username",strings[0])
                        .appendQueryParameter("password",strings[1]);
                OutputStream outputStream=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String query=builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int response=connection.getResponseCode();
                if (response==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String line;
                   while((line=reader.readLine())!=null) {
                      builder.append(line);
                    }
                    return (builder.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                connection.disconnect();
            }
            return String.valueOf(connection);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            if (s.equals("wrong again")){
                Toast.makeText(Login_Activity.this, s, Toast.LENGTH_SHORT).show();
            }else {
                try {
                    JSONObject object = new JSONObject(s);
                    String firstname = object.getString("firstname");
                    String lastname = object.getString("lastname");
                    String email = object.getString("email");
                    String username = object.getString("username");
                    String phone = object.getString("phone");
                    Prefmanager prefmanager=new Prefmanager(getApplicationContext());
                    prefmanager.saveUserDetail(firstname,lastname,email,phone,username);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                } catch (JSONException e) {

                    e.printStackTrace();
                }
            }
        }
    }
}