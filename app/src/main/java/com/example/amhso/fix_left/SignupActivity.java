package com.example.amhso.fix_left;

import android.annotation.SuppressLint;
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

@SuppressLint("SetJavaScriptEnabled")
public class SignupActivity extends AppCompatActivity {
 public String user;
 public String password;
 public String mobail;
 public String kodmeli;
 public EditText et;
 public EditText et1;
 public EditText et2;
 public EditText et3;
 public Boolean valid=true;


        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_signup);

            G.activity = this;


            et = (EditText) findViewById(R.id.editText2);

            et1 = (EditText) findViewById(R.id.editText3);

            et2 = (EditText) findViewById(R.id.editText5);

            et3 = (EditText) findViewById(R.id.editText6);








            Button Save = (Button) findViewById(R.id.button2);

            // Capture button clicks
            Save.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {


                     user = et.getText().toString();
                     password = et1.getText().toString();
                     mobail = et2.getText().toString();
                     kodmeli = et3.getText().toString();

                     valid=true;


                    if (user.isEmpty() || user.length() <3) {

                        et.setError("نام معتبر نمی باشد");

                        valid=false;

                    } else {
                        et.setError(null);
                    }
                    if (password.isEmpty() || password.length() < 4 || password.length() > 10) {

                        et1.setError("رمز عبور نامعتبر می باشد");

                        valid=false;

                    } else {
                        et1.setError(null);
                    }

                    if (mobail.isEmpty()) {

                        et2.setError("شماره موبایل صحیح نمی باشد");
                        valid=false;


                    } else {
                        et2.setError(null);
                    }
                    if (kodmeli.isEmpty()) {

                        et3.setError("کدملی صحیح نمی باشد");

                        valid=false;


                    } else {
                        et3.setError(null);
                    }

                    if (valid) {
                        HttpPostAsyncTask task = new HttpPostAsyncTask();
                        task.execute(G.urlserver + "signup");
                    }
                }
            });


        }







    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        public ProgressDialog progressDialog;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        ProgressDialog dialog = null;





        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);
//
            progressDialog.dismiss();

            String Result[]= result.split(":");
            if(Result[0].equals("1")){


                SharedPreferences shpref_login1 = G.activity.getSharedPreferences(G.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor sh_edit = shpref_login1.edit();
                 sh_edit.putString(G.id_user,Result[1]);
                sh_edit.apply();


                Intent i = new Intent(G.activity,MainActivity.class);
                startActivity(i);
                finish();

            }




            else if (Result[0].equals("0")  && Result[1].equals("0")){

                et.setError("نام نا معتبر است");


            }


            else if (Result[0].equals("0") && Result[1].equals("1")){

                et1.setError("رمز عبور نا معتبر می باشد");



            }
            else if (Result[0].equals("0") && Result[1].equals("2")){

                et2.setError("شماره همراه نامعتبر می باشد");



            }
            else if (Result[0].equals("0") && Result[1].equals("4")){

                et3.setError("کد ملی نامعتبر می باشد");


            }



        }






        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(G.activity);
            progressDialog.setMessage("چند لحظه صبر کنید...."); // Setting Message
            progressDialog.setTitle("در حال تایید اطلاعات..."); // Setting Title
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

                nameValuePairs.add(new BasicNameValuePair("username", user.trim()));
                nameValuePairs.add(new BasicNameValuePair("password", password.trim()));
                nameValuePairs.add(new BasicNameValuePair("mobile", mobail.trim()));
                nameValuePairs.add(new BasicNameValuePair("national_number", kodmeli.trim()));


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