package com.ycss.j.common.app.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycss.j.R;


/**
 * Todo
 * Created by 王剑洪
 * on 2016/7/13.
 */
public class LoadingDialog2 extends Dialog {

    private TextView tv;
    private String text = "";

    public LoadingDialog2(Context context, String text) {
        super(context, R.style.loadingDialogStyle);
        setText(text);
    }

    public LoadingDialog2(Context context) {
        super(context, R.style.loadingDialogStyle);
        setText("");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.view_loading);
//        tv = (TextView)findViewById(R.id.tv);
//        tv.setText(text);
//        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.ll);
//        linearLayout.getBackground().setAlpha(210);
//        RelativeLayout root = new RelativeLayout(getContext());
//        ProgressBar progressBar=new ProgressBar(getContext());
//        progressBar.setProgress(R.style.loadingDialogStyle);
//        tv=new TextView(getContext());
//        RelativeLayout.LayoutParams braLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        RelativeLayout.LayoutParams tvLp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//        braLp.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
//        tvLp.addRule(RelativeLayout.RIGHT_OF, progressBar.getId());
//        root.addView(progressBar,braLp);
//        root.addView(tv,braLp);
//        setContentView(root);
        LinearLayout root = new LinearLayout(getContext());
        LinearLayout.LayoutParams rootPL = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        root.setLayoutParams(rootPL);
        root.setOrientation(LinearLayout.HORIZONTAL);
        root.setGravity(Gravity.CENTER);
         tv = new TextView(getContext());
        tv.setText("加载中..");
        LinearLayout.LayoutParams tvPL = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
        tv.setText(text);
    }
}
