package com.example.amhso.fix_left;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import com.example.amhso.fix_left.otherclass.G;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private String username;
    private String password;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        G.activity=this;





//کد مربوط به یکبار اجرا شدن لوگین
       SharedPreferences sharedpreferences = getSharedPreferences(G.MyPREFERENCES, Context.MODE_PRIVATE);

       String checklogin = sharedpreferences.getString(G.id_user,"-1");


        if(!checklogin.equals("-1") ) {
            Intent myIntent = new Intent(G.activity,MainActivity.class);
            startActivity(myIntent);
            finish();
        }



        Button SendButton = (Button) findViewById(R.id.login);
        SendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {



                EditText usernameE=(EditText) findViewById(R.id.username);
                username=usernameE.getText().toString();


                EditText passwordE=(EditText) findViewById(R.id.password);
                password=passwordE.getText().toString();

                HttpPostAsyncTask task = new HttpPostAsyncTask();
                task.execute(G.urlserver + "auth");

            }
        });






        Button signup=(Button) findViewById(R.id.signup);
        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(G.activity,SignupActivity.class);
                G.activity.startActivity(myIntent);
                finish();
            }
        });

    }







    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        public ProgressDialog progressDialog;






        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);

            try {
                progressDialog.dismiss();

            }
            catch (Exception e){

            }



            if(result.equals("1")){
                Intent i = new Intent(G.activity,MainActivity.class);
                startActivity(i);
                overridePendingTransition(R.anim.animation_activity_start,R.anim.animation_activity_end);
                finish();
            }

            else{
                EditText usernameE=(EditText) findViewById(R.id.username);
                usernameE.setError("اشتباه می باشد.");
                EditText passwordE=(EditText) findViewById(R.id.password);
                passwordE.setError("اشتباه می باشد.");

            }



        }






        @Override
        protected void onPreExecute() {

            try {
                progressDialog = new ProgressDialog(G.activity);
                progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
                progressDialog.setTitle("در حال تایید اطلاعات"); // Setting Title
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                progressDialog.show(); // Display Progress Dialog


                new Thread(new Runnable() {
                    public void run() {
                        try {
                            Thread.sleep(20000);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }).start();


            }
            catch (Exception e){

            }
        }



        // This is a function that we are overriding from AsyncTask. It takes Strings as parameters because that is what we defined for the parameters of our async task
        @Override
        protected String doInBackground(String... params) {

            try {


                Log.i("urluuuuuuuuuuuuuuu", "doInBackground: "+params[0]);

                httpclient=new DefaultHttpClient();
                httppost= new HttpPost(params[0]); // make sure the url is correct.
                //add your data

                Log.i("uuuuuu", "urluuuuuuuuuuuu "+params[0]);
                nameValuePairs = new ArrayList<NameValuePair>(2);
                // Always use the same variable name for posting i.e the android side variable name and php side variable name should be similar,




//
                nameValuePairs.add(new BasicNameValuePair("username",username.trim()));
                nameValuePairs.add(new BasicNameValuePair("password",password.trim()));

                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs,"utf-8"));


                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                String response = httpclient.execute(httppost, responseHandler);
                System.out.println("Response : " + response);
                return response;



            } catch (Exception e) {
                Log.i("error rrrrrrr", e.toString());
            }

            return "0";
        }
    }



}


