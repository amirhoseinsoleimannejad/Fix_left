package com.example.amhso.fix_left;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amhso.fix_left.otherclass.G;
import com.example.amhso.fix_left.otherclass.Request;
import com.example.amhso.fix_left.otherclass.RequestAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_17;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static WebSocketClient mWebSocketClient;


    public ListView listview;
    public List<Request> list;
    public RequestAdapter requestAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        G.activity=this;



        listview=(ListView)findViewById(R.id.list_request);
        list=new ArrayList<Request>();

        requestAdapter = new RequestAdapter(G.activity,list);
        listview.setAdapter(requestAdapter);



        HttpPostAsyncTask task = new HttpPostAsyncTask();
        task.execute(G.urlserver + "list_request");

        URI uri;



        try {
            uri = new URI(G.service);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

        mWebSocketClient = new WebSocketClient(uri, new Draft_17()) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i("Websocket", "Open" );




            }

            @Override
            public void onMessage(String s) {
                final String message = s;




                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {



                        if(message.equals("1895")){




                    AlertDialog.Builder builder1 = new AlertDialog.Builder(G.activity);
                    builder1.setMessage("در آدرس میدان انقلاب خیابان منیری جاوید در خواستی برای تعمیر آسانسور دارید آیا برای تعمیر آن را قبول می کنید؟");
                    builder1.setCancelable(true);


                    builder1.setPositiveButton(
                            "بله",
                            new DialogInterface.OnClickListener(){
                                public void onClick(DialogInterface dialog, int id) {

                                    mWebSocketClient.send("1");

                                    Intent i = new Intent(G.activity,Detail_requestActivity.class);
                                    G.activity.startActivity(i);
                                    dialog.cancel();

                                }
                            });

                    builder1.setNegativeButton(
                            "خیر",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                            Log.i("tttttttttttt", "111111111111111");
//                                    mWebSocketClient.send("1");


//                            Request r=new Request("میدان انقلاب خیابان منیری جاوید","red");
//                            list.add(r);
//
//
//
//                            requestAdapter.notifyDataSetChanged();

                        }

                    }
                });






            }

            @Override
            public void onClose(int i, String s, boolean b) {
                Log.i("Websocket", "Closed " + s);
            }

            @Override
            public void onError(Exception e) {
                Log.i("Websocket", "Error " + e.getMessage());
            }
        };


        try {
            mWebSocketClient.connect();
        }
        catch (Exception a){

            Log.i("dddddd", "ddddddd: "+a.toString());
        }
    }



    public static void send_message(){
        mWebSocketClient.send("1");
    }










    public class HttpPostAsyncTask extends AsyncTask<String, String, String> {


        HttpPost httppost;
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;







        @Override
        protected void onPostExecute(String result) {

            Log.i("22222222222222222", "22222222222222222222222222" + result);


            try {


                JSONArray contacts;
                JSONObject jsonObj = new JSONObject(result);
                contacts = jsonObj.getJSONArray("request");




                int k=contacts.length();
                for (int i = 0; i < contacts.length(); i++) {

                    JSONObject c = contacts.getJSONObject(i);
                    String address = c.getString("address_client");

                    Request r=new Request(address,"white");
                    list.add(r);
                }





                requestAdapter.notifyDataSetChanged();

            }
            catch (Exception e){


                Log.i("eeeeee", "errrrrrrrror: "+e.toString());
            }

        }






        @Override
        protected void onPreExecute() {


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
                nameValuePairs = new ArrayList<NameValuePair>(1);


                SharedPreferences sharedpreferences = getSharedPreferences(G.MyPREFERENCES, Context.MODE_PRIVATE);
                nameValuePairs.add(new BasicNameValuePair("id_sick", sharedpreferences.getString(G.id_sick ,"-1").trim()));

                Log.i("111111111111", "11111111111111: "+sharedpreferences.getString(G.id_sick ,"-1").trim());
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
