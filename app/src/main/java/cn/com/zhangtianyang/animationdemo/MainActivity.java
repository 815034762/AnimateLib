package cn.com.zhangtianyang.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import cn.com.animationlibrary.LeftToRightInfinite;

public class MainActivity extends AppCompatActivity {

    LeftToRightInfinite test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.leftToRightInfinite);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(test.isStop()){
                    test.start();
                }else {
                    test.stop();
                }
            }
        });
    }
}
