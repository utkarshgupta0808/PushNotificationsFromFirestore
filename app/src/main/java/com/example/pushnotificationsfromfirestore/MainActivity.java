package com.example.pushnotificationsfromfirestore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQue;
    private String URL = "https://fcm.googleapis.com/fcm/send";

    private TextView notificationTitle, notificationBody;

    Button sendNotification;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        sendNotification =findViewById(R.id.send_noti);
        notificationTitle =findViewById(R.id.noti_title);
        notificationBody =findViewById(R.id.noti_text);
        mRequestQue = Volley.newRequestQueue(MainActivity.this);
        FirebaseMessaging.getInstance().subscribeToTopic("news");

        if (getIntent().hasExtra("titleNoti")){

            Intent intent = new Intent(this,ReceiveNotificationActivity.class);
            intent.putExtra("notiTitle",notificationTitle.getText().toString().trim());
            intent.putExtra("notiBody",notificationBody.getText().toString().trim());
            startActivity(intent);
        }





        sendNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });
    }




    private void sendNotification() {

        JSONObject json = new JSONObject();
        try {
            json.put("to","/topics/"+"news");
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title",notificationTitle.getText().toString());
            notificationObj.put("body",notificationBody.getText().toString());

            JSONObject extraData = new JSONObject();
            extraData.put("notiTitle",notificationTitle.getText().toString().trim());
            extraData.put("notiBody",notificationBody.getText().toString().trim());



            json.put("notification",notificationObj);
            json.put("data",extraData);


            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("MUR", "onResponse: ");
                            Toast.makeText(MainActivity.this, "Notification Sent", Toast.LENGTH_SHORT).show();
                            notificationTitle.setText("");
                            notificationBody.setText("");
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("MUR", "onError: "+error.networkResponse);
                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA7k2rlxk:APA91bEA2-If8_pQB4-IsfMnbd2UOnl0n6rR3ay6rYP3JBqqSFv4IE8V6o7XC_he4bXt_LVxvhskcOESSqDDQPwSQ4ESuYKSMJKaUKRfvusQCgBxg83JqGeKDb7lKId60dtRKLqh37rK");
                    return header;
                }
            };
            mRequestQue.add(request);
        }
        catch (JSONException e)

        {
            e.printStackTrace();
        }
    }
}