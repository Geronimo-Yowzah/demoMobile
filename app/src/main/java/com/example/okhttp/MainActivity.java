package com.example.okhttp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    MyRecyclerAdapter adapter;
    List<String> strings = new ArrayList<>();
    OkHttpClient client;
    String getUrl = "https://jsonplaceholder.typicode.com/";
    TextView textView;
    EditText inputText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
//ตั้งค่า Layout
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//ตั้งค่า Adapter
        inputText = findViewById(R.id.editText);

        adapter = new MyRecyclerAdapter(strings);
        recyclerView.setAdapter(adapter);

        client = new OkHttpClient();
        textView = findViewById(R.id.textData);
        Button btnGet = findViewById(R.id.btnGet);


        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(getUrl)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                JSONPlaceholder jsonPlaceholder =retrofit.create(JSONPlaceholder.class);
                retrofit2.Call<List<Post>> call = jsonPlaceholder.getM();
                call.enqueue(new retrofit2.Callback<List<Post>>() {
                    @Override
                    public void onResponse(retrofit2.Call<List<Post>> call, retrofit2.Response<List<Post>> response) {
                        if(!response.isSuccessful()){
                            Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_SHORT);
                            return;
                        }
                        List<Post> postList = response.body();
                        PostAdapter PostAdapter = new PostAdapter(MainActivity.this, postList, new PostAdapter.PostAdapterListener() {
                            @Override
                            public void onClicked(Post data) {
                                Toast.makeText(MainActivity.this, data.getUserId(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        recyclerView.setAdapter(PostAdapter);
                    }

                    @Override
                    public void onFailure(retrofit2.Call<List<Post>> call, Throwable t) {
                        Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

//    public void getA(){
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getUrl)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build();
//
//        JSONPlaceholder jsonPlaceholder =retrofit.create(JSONPlaceholder.class);
//        retrofit2.Call<List<post>> call = jsonPlaceholder.getM();
//        call.enqueue(new retrofit2.Callback<List<post>>() {
//            @Override
//            public void onResponse(retrofit2.Call<List<post>> call, retrofit2.Response<List<post>> response) {
//                if(!response.isSuccessful()){
//                    Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_SHORT);
//                    return;
//                }
//                List<post> postList = response.body();
//                posts posts = new posts(MainActivity.this,postList);
//
//            }
//
//            @Override
//            public void onFailure(retrofit2.Call<List<post>> call, Throwable t) {
//                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
    public void get() {
        Request request = new Request.Builder().url(getUrl).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    textView.setText(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void update(View view) {
        String text = inputText.getText().toString();
        if (text.isEmpty()) {
            Toast.makeText(MainActivity.this, "Text Is Null", Toast.LENGTH_SHORT).show();
        } else {
            strings.add(text);
            adapter.setData(strings);
        }

    }
}