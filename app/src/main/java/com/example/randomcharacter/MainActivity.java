package com.example.randomcharacter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity
{

    private TextView mTextView;
    private ImageView mImageView;
    private Button reloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //wczytywanie kolejnej postaci po wcisnięciu przycisku reload
        reloadButton = findViewById(R.id.reloadButton);
        reloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reload();
            }
        });
        //wczytanie postaci
        Reload();
    }

    public void Reload()
    {
        //znalezienie pola tekstowego i elementu wyświetlającego obraz
        mTextView = findViewById(R.id.testText);
        mImageView = findViewById(R.id.imageView);
        MainActivity ma = this;
        //pobieranie danych o losowej postaci i jej wizerunku
        DownloadRandomCharacter drc = new DownloadRandomCharacter(new AsyncResponse()
        {
            @Override
            public void onFinish(Character result)
            {
                mTextView.setText(result.toString());
                Glide.with(ma).load(result.image).into(mImageView);
            }
        });
        drc.execute("");
    }
}