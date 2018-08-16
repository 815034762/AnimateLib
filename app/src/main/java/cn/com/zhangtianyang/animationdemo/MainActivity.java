package cn.com.zhangtianyang.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import cn.com.animationlibrary.AnimateTextView;
import cn.com.animationlibrary.LeftToRightInfinite;
import cn.com.zhangtianyang.animationdemo.utils.NotificationUtils;

public class MainActivity extends AppCompatActivity {

    LeftToRightInfinite test;
    AnimateTextView animateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        test = findViewById(R.id.leftToRightInfinite);
        animateTextView = findViewById(R.id.animateTextView);
        animateTextView.setAnimations(R.anim.right_to_left);

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (test.isStop()) {
                    test.start();
                } else {
                    test.stop();
                }
            }
        });


        animateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!animateTextView.isStarted()) {
                    animateTextView.start();
                } else {
                    animateTextView.stop();
                }

            }
        });

        animateTextView.start();

        findViewById(R.id.tv_create_notifycation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NotificationUtils notificationUtils = new NotificationUtils(MainActivity.this);
                notificationUtils.sendNotification("测试标题", "测试内容");
            }
        });

        findViewById(R.id.btn_go_path_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PathActivity.start(MainActivity.this);
            }
        });

    }
}
