package com.example.amhso.fix_left;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Detail_requestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_request);


        TextView call = (TextView) findViewById(R.id.mobile);
        call.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                TextView call = (TextView) findViewById(R.id.mobile);


                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(call.getText().toString())));

                    startActivity(intent);


            }
        });
    }
}
