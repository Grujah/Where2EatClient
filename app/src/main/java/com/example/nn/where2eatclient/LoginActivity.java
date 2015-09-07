package com.example.nn.where2eatclient;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.InputStream;


public class LoginActivity extends ActionBarActivity {

    ProgressDialog pDialog = new ProgressDialog(this);

    TextView usernameContainer, passwordContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameContainer = (TextView) findViewById(R.id.username_prompt);
        passwordContainer = (TextView) findViewById(R.id.password_prompt);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void beginLogin (View view){

    }

    private class AsyncLogin extends AsyncTask<String,String,String>{

        private final String LOGIN_URL = "192.168.1.5:8081/where2eat/login.php";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // progress dialog make
            pDialog.setMessage("Conecting to Database");
            pDialog.show();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pDialog.hide();
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                // build jason
                JSONObject jason = new JSONObject();
                jason.accumulate("username", usernameContainer.toString());
                jason.accumulate("password", passwordContainer.toString());

                String jasonString = jason.toString();
                StringEntity sEntity = new StringEntity(jasonString);

                //bild Http Client + stuff

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost();

                httpPost.setHeader("Accept", "application/json");
                httpPost.setHeader("Content-type", "application/json");

                // put data in http client

                httpPost.setEntity(sEntity);

                // Send stuff
                HttpResponse httpResponse = httpClient.execute(httpPost);

                // receive answer

                InputStream inputStream = httpResponse.getEntity().getContent();
                String answerString = inputStream.toString();
                JSONObject jasonAnswer = new JSONObject(answerString);





            } catch (Exception e) {
                Log.d("InputStream", e.getLocalizedMessage());

            }
        return null;
        }
    }
}
