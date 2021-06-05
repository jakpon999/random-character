package com.example.randomcharacter;

import android.os.AsyncTask;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.Function;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

interface AsyncResponse
{
    void onFinish(Character result);
}

public class DownloadRandomCharacter extends AsyncTask<String, Void, Character> implements AsyncResponse
{
    public AsyncResponse delegate = null;

    public DownloadRandomCharacter(AsyncResponse delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public void onFinish(Character result) {}

    @Override
    protected Character doInBackground(String... strings) {
        //utworzenie klienta http
        OkHttpClient client = new OkHttpClient();
        //utworzenie zapytania do API o ilość dostępnych postaci
        RequestBody body = RequestBody.create("{\"query\":\"query { characters { info { count }  } }\"}", MediaType.parse("application/json"));
        //stworzenie zapytania (podanie klucza API i query)
        Request request = new Request.Builder()
                .url("https://rick-and-morty-graphql.p.rapidapi.com/")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("x-rapidapi-key", "ad85e0bb19msh30ad22e2a1c8abap1b2652jsn17aec57c6ba5")
                .addHeader("x-rapidapi-host", "rick-and-morty-graphql.p.rapidapi.com")
                .build();

        try {
            //wykonanie zapytania 
            Response response = client.newCall(request).execute();
            //konwersja zwróconego przez zapytanie tekstu na JSON
            JSONObject json = new JSONObject(response.body().string());
            //odnalezienie liczby dostępnych postaci z obiektu JSON
            int charactersCount = json.getJSONObject("data").getJSONObject("characters").getJSONObject("info").getInt("count");
            //wylosowanie nowej postaci
            int id = new Random().nextInt(charactersCount);
            //utworzenie zapytania o postać o powyższym id
            body = RequestBody.create("{\"query\":\"query { character(id: " + id + ") { name species gender origin {name} location {name} status image } }\"}", MediaType.parse("application/json"));
            //stworzenie zapytania (podanie klucza API i query)
            request = new Request.Builder()
                    .url("https://rick-and-morty-graphql.p.rapidapi.com/")
                    .post(body)
                    .addHeader("content-type", "application/json")
                    .addHeader("x-rapidapi-key", "ad85e0bb19msh30ad22e2a1c8abap1b2652jsn17aec57c6ba5")
                    .addHeader("x-rapidapi-host", "rick-and-morty-graphql.p.rapidapi.com")
                    .build();
            //wykonanie zapytania 
            response = client.newCall(request).execute();
            //konwersja zwróconego przez zapytanie tekstu na JSON
            json = new JSONObject(response.body().string()).getJSONObject("data").getJSONObject("character");
            //stworzenie obiektu Character na podstawie zwróconych danych
            Character character = new Character(
                    json.getString("name"),
                    json.getString("species"),
                    json.getString("gender"),
                    json.getJSONObject("origin").getString("name"),
                    json.getJSONObject("location").getString("name"),
                    json.getString("status"),
                    json.getString("image")
            );

            return character;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Character s) {
        super.onPostExecute(s);
        //wywołanie callback
        delegate.onFinish(s);
    }
}
