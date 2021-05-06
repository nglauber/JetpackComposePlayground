package br.com.nglauber.jetpackcomposeplayground;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import br.com.nglauber.jetpackcomposeplayground.screens.MyComposeView;

public class MyJavaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this);
        tv.setText("This is a Java Activity using Compose");

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(tv);
        ll.addView(new MyComposeView(this));
        setContentView(ll);
    }
}
