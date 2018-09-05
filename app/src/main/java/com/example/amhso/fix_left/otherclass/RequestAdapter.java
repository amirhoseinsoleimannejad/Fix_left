package com.example.amhso.fix_left.otherclass;


import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.amhso.fix_left.Detail_requestActivity;
import com.example.amhso.fix_left.LoginActivity;
import com.example.amhso.fix_left.MainActivity;
import com.example.amhso.fix_left.R;


import java.util.List;


public class RequestAdapter extends ArrayAdapter<Request> {

    private final Activity context;
    private final List<Request> itemname;

    public RequestAdapter(Activity context, List<Request> itemname) {
        super(context, R.layout.listrequest, itemname);
        this.context=context;
        this.itemname=itemname;
    }




    public View getView(final int position, View view, ViewGroup parent) {




        View listItem = view;
        MyWrapper wrapper = null;


        try {

            if (listItem == null) {

                listItem = LayoutInflater.from(context).inflate(R.layout.listrequest, parent, false);
                wrapper = new MyWrapper(listItem);
                listItem.setTag(wrapper);

            } else {
                wrapper = (MyWrapper) listItem.getTag();
            }


            wrapper.getRequest().setText(itemname.get(position).getRequestText());

            Log.i("redredredred", "getView: reeeeeeeeeeed"+itemname.get(position).getColor());

            if(itemname.get(position).getColor().equals("red")){

                Log.i("redredredred", "getView: reeeeeeeeeeed");
                wrapper.getLinearLayout().setVisibility(View.VISIBLE);

                wrapper.getLinearLayout().setBackgroundColor(0x00FF00);

            }



            wrapper.getCancel().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(G.activity,MainActivity.class);
                    G.activity.startActivity(i);
                }
            });




            wrapper.getOk().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    MainActivity.send_message();
                    Intent i = new Intent(G.activity,Detail_requestActivity.class);
                    G.activity.startActivity(i);
                }
            });




        }
        catch (Exception e){
            Log.i("eeeeee", "eeeeeeeeeeeeeeee"+e.toString());
        }

        return listItem;

    };







    class MyWrapper
    {
        private View base;
        private TextView request;
        private Button cancel;
        private Button ok;
        private LinearLayout linearLayout;

        public MyWrapper(View base)
        {
            this.base = base;
        }



        public TextView getRequest(){
            if(request == null){
                request = (TextView) base.findViewById(R.id.request);
            }
            return request;
        }




        public Button getOk(){
            if(ok == null){
                ok = (Button) base.findViewById(R.id.ok_request);
            }
            return ok;
        }


        public Button getCancel(){
            if(cancel == null){
                cancel = (Button) base.findViewById(R.id.cancel_request);
            }
            return cancel;
        }



        public LinearLayout getLinearLayout(){
            if(linearLayout == null){
                linearLayout = (LinearLayout) base.findViewById(R.id.new_request);
            }
            return linearLayout;
        }


    }
}
