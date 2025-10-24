package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.*;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editTextUrl;
    Button buttonAdd, buttonEdit, buttonDelete;
    ListView listViewUrls;
    ArrayList<String> urlList;
    ArrayAdapter<String> adapter;
    int selectedIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUrl = findViewById(R.id.editTextUrl);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);
        listViewUrls = findViewById(R.id.listViewUrls);

        urlList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, urlList);
        listViewUrls.setAdapter(adapter);

        buttonAdd.setOnClickListener(v -> {
            String url = editTextUrl.getText().toString().trim();
            if (!url.isEmpty()) {
                urlList.add(url);
                adapter.notifyDataSetChanged();
                editTextUrl.setText("");
            } else {
                Toast.makeText(MainActivity.this, "لطفاً آدرس سایت را وارد کنید!", Toast.LENGTH_SHORT).show();
            }
        });

        listViewUrls.setOnItemClickListener((parent, view, position, id) -> {
            selectedIndex = position;
            editTextUrl.setText(urlList.get(position));
        });

        listViewUrls.setOnItemLongClickListener((parent, view, position, id) -> {
            String url = urlList.get(position);
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
            return true;
        });

        buttonEdit.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                String newUrl = editTextUrl.getText().toString().trim();
                if (!newUrl.isEmpty()) {
                    urlList.set(selectedIndex, newUrl);
                    adapter.notifyDataSetChanged();
                    editTextUrl.setText("");
                    selectedIndex = -1;
                } else {
                    Toast.makeText(MainActivity.this, "آدرس جدید را وارد کنید!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MainActivity.this, "لینک را از لیست انتخاب کنید!", Toast.LENGTH_SHORT).show();
            }
        });

        buttonDelete.setOnClickListener(v -> {
            if (selectedIndex != -1) {
                urlList.remove(selectedIndex);
                adapter.notifyDataSetChanged();
                editTextUrl.setText("");
                selectedIndex = -1;
            } else {
                Toast.makeText(MainActivity.this, "لینک را از لیست انتخاب کنید!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
