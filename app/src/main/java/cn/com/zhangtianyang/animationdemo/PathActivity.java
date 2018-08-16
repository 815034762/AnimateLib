package cn.com.zhangtianyang.animationdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;

import cn.com.animationlibrary.GesturePathView;

/**
 *
 */
public class PathActivity extends AppCompatActivity {

    private GesturePathView gesturePathView;
    public static void start(Context context) {

        Intent intent = new Intent(context, PathActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        setContentView(R.layout.activity_path);
        gesturePathView = findViewById(R.id.tuyaView);
        gesturePathView.init(dm.widthPixels, dm.heightPixels);
        findViewById(R.id.tv_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gesturePathView.undo();
            }
        });

        findViewById(R.id.tv_redo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gesturePathView.redo();
            }
        });

        findViewById(R.id.tv_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                gesturePathView.next();
            }
        });


    }

}
