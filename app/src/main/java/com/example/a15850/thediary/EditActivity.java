package com.example.a15850.thediary;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class EditActivity extends AppCompatActivity {


    private EditText editText;
    String inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setElevation(0);

        //TickerView
        final TickerView tickerView = findViewById(R.id.day_tickerView);
        tickerView.setCharacterLists(TickerUtils.provideNumberList());

        tickerView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                tickerView.setText("234");
            }
        });

        //EditText
        editText = (EditText) findViewById(R.id.editText_edit);
        //Floating Button
        FloatingActionButton button_EditToMain = (FloatingActionButton) findViewById(R.id.mainButton_edit);

        button_EditToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputText = editText.getText().toString();
                // Toast.makeText(EditActivity.this,inputText,Toast.LENGTH_SHORT).show(); //测试EditText内容传入了inputText//测试成功
                Intent intent_edit_main = new Intent(EditActivity.this,MainActivity.class);
                startActivity(intent_edit_main);
            }

        });
    }
}

