package com.example.amhso.fix_left.otherclass;

/**
 * Created by amhso on 18/05/2018.
 */

public class Request {

    private String request_text;
    private String color;


    public Request(String request_text,String color){
        this.request_text=request_text;
        this.color=color;
    }

    public String getRequestText(){
        return this.request_text;
    }



    public String getColor(){
        return this.color;
    }


}
