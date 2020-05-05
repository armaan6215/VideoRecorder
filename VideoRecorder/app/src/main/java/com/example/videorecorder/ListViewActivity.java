package com.example.videorecorder;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_REQUEST = 1;
    ArrayList<String> arrayList;
    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    String externalStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView) findViewById(R.id.listView);
        doStuff();

    }

    public void doStuff() {
        externalStorage = Environment.getExternalStorageDirectory().getAbsolutePath();
        File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES), "VideoRecorder");
        String[] files = directory.list();
        if (files == null) {

        } else {
            arrayList = new ArrayList<>();
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, files);
            listView.setAdapter(arrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                }
            });
        }
    }

    public static class CreatePasswordActivity extends AppCompatActivity {

        EditText editText1, editText2, oldPassword;
        Button button, hide;
        String oldPass;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_create_password);

            editText1 = (EditText) findViewById(R.id.editText1);
            editText2 = (EditText) findViewById(R.id.editText2);
            oldPassword = (EditText) findViewById(R.id.oldPassword);
            button = (Button) findViewById(R.id.button);
            hide = (Button) findViewById(R.id.hide);

            final SharedPreferences settings = getSharedPreferences("PREFS", 0);
            oldPass = settings.getString("password", "");

            if (oldPass.equals("")) {
                oldPassword.setVisibility(View.INVISIBLE);
            }

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text1 = editText1.getText().toString();
                        String text2 = editText2.getText().toString();
                        String text3 = oldPassword.getText().toString();

                        if(!oldPass.equals("") && !oldPass.equals(text3)){
                            Toast.makeText(CreatePasswordActivity.this, "Old Password Incorrect", Toast.LENGTH_LONG).show();
                        }

                        else if (text1.equals("") || text2.equals("")) {
                            Toast.makeText(CreatePasswordActivity.this, "Password not entered", Toast.LENGTH_SHORT).show();
                        } else if (text1.equals(text2)) {
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putString("password", text1);
                            editor.apply();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(CreatePasswordActivity.this, "Password does'nt match", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }

        }
}

