package com.example.footer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FooterLayout footer = findViewById(R.id.footer);
        final EditText edit1 = findViewById(R.id.edit1);
        final EditText edit2 = findViewById(R.id.edit2);
        final EditText edit3 = findViewById(R.id.edit3);

        edit1.setText(footer.getText1());
        edit2.setText(footer.getText2());
        edit3.setText(footer.getText3());

        edit1.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                footer.setText1(s.toString());
            }
        });
        edit2.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                footer.setText2(s.toString());
            }
        });
        edit3.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                footer.setText3(s.toString());
            }
        });
    }

    private static class MyTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
