package br.com.nglauber.jetpackcomposeplayground;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import br.com.nglauber.jetpackcomposeplayground.screens.MyComposeView;
import br.com.nglauber.jetpackcomposeplayground.screens.MyViewModel;

public class MyJavaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_java);

        MyViewModel model = new ViewModelProvider(this).get(MyViewModel.class);

        MyComposeView composeView = findViewById(R.id.my_composable);
        composeView.setViewModel(model);
    }
}
