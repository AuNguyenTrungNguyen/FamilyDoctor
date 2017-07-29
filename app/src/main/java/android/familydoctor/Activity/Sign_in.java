package android.familydoctor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.familydoctor.R;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Sign_in extends AppCompatActivity {

    Button phone ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        //sign in with phone
        phone = (Button) findViewById(R.id.btn_signPhone);

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Sign_in.this, LoginPhone.class);
                startActivity(intent);
            }
        });

    }


}