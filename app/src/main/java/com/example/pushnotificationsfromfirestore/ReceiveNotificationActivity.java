package com.example.pushnotificationsfromfirestore;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceiveNotificationActivity extends AppCompatActivity {

    TextView recNotiTitle, recNotiBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_notification);

//        TextView categotyTv = findViewById(R.id.category);
//        TextView brandTv = findViewById(R.id.brand);

//        if (getIntent().hasExtra("category")) {
//            String category = getIntent().getStringExtra("category");
//            String brand = getIntent().getStringExtra("brandId");
//            categotyTv.setText(category);
//            brandTv.setText(brand);
//        }
        recNotiTitle=findViewById(R.id.rec_noti_title);
        recNotiBody=findViewById(R.id.rec_noti_body);

        recNotiBody.setText(getIntent().getStringExtra("bodyNoti"));
        recNotiTitle.setText(getIntent().getStringExtra("titleNoti"));

    }
}