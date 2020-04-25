package com.newproject.project;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class Signup_Activity extends AppCompatActivity {
EditText firstname,lastname,email,phonenumber,username,password;
Button Register;
CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);
        firstname=findViewById(R.id.firstname);
        lastname=findViewById(R.id.lastname);
        email=findViewById(R.id.email);
        phonenumber=findViewById(R.id.phone);
        username=findViewById(R.id.username1);
        password=findViewById(R.id.new_password);
        Register=findViewById(R.id.register);
        checkBox=findViewById(R.id.checkbox);

            Register.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        String first = firstname.getText().toString();
                        String last = lastname.getText().toString();
                        String Email = email.getText().toString();
                        String phone = phonenumber.getText().toString();
                        String user = username.getText().toString();
                        String pass = password.getText().toString();
                        new SignUp().execute(first, last, Email, phone, user, pass);
                    }else{
                        Toast.makeText(Signup_Activity.this, "Check the Agreement box first", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }

    private class SignUp extends AsyncTask<String,String,String> {
        ProgressDialog pg=new ProgressDialog(Signup_Activity.this);
        URL url=null;
        HttpURLConnection httpURLConnection;

        @Override
        protected void onPreExecute() {
            pg.setMessage("SIGNING UP");
            pg.show();
            pg.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://192.168.0.106/Android/signup.php");
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("First name",strings[0])
                        .appendQueryParameter("Last name",strings[1])
                        .appendQueryParameter("Email",strings[2])
                        .appendQueryParameter("PhoneNumber",strings[3])
                        .appendQueryParameter("username",strings[4])
                        .appendQueryParameter("password",strings[5]);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                String query=builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
                httpURLConnection.connect();
                int response_code=httpURLConnection.getResponseCode();
                if(response_code==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder stringBuilder=new StringBuilder();
                    String line;
                    while(null!=(line=reader.readLine())){
                        stringBuilder.append(line);
                    }
                    return (stringBuilder.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
            }
            return String.valueOf(httpURLConnection);
        }

        @Override
        protected void onPostExecute(String s) {
               pg.dismiss();
            Toast.makeText(Signup_Activity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
