package com.example.a310finalproj;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatButton;

public class ResponseView extends AppCompatButton {
    public String responseId;

    public ResponseView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ResponseView(Context context){
        super(context);
    }

    public void setResponseId(String id){
        responseId = id;
    }
}
