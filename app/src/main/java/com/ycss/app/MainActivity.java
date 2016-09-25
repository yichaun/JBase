package com.ycss.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ycss.j.J;
import com.ycss.j.common.utils.L;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        J.initDBConfig(this,"test",1,null);


        findViewById(R.id.btn_insert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test test=new Test();
                test.setAmount(false);
                test.setJ(22);
                test.setName("123456");
                J.db().insert("J",test);
            }
        });
        findViewById(R.id.btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Test> tests= J.db().query("J",Test.class,"name","123456");
                L.e("size",tests.size());
            }
        });
        findViewById(R.id.btn_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Test test=new Test();
                test.setId(1);
                test.setAmount(true);
                test.setJ(11111);
                test.setName("12222222222222");
                J.db().update("J","id","1",test);
            }
        });

    }
}
