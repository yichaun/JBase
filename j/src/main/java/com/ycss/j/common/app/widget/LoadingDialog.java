package com.ycss.j.common.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycss.j.R;


/**
 * Todo
 * Created by 王剑洪
 * on 2016/7/13.
 */
public class LoadingDialog extends Dialog {

    private TextView tv;
    private String text="";

    public LoadingDialog(Context context, String text) {
        super(context, R.style.loadingDialogStyle);
        setText(text);
    }
    public LoadingDialog(Context context) {
        super(context, R.style.loadingDialogStyle);
        setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading);
        tv = (TextView)findViewById(R.id.tv);
        tv.setText(text);
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.ll);
        linearLayout.getBackground().setAlpha(210);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        tv.setText(text);
    }
}
