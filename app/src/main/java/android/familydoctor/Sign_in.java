package android.familydoctor;

import android.content.Intent;
import android.familydoctor.Activity.LuaChonLoaiTaiKhoanActivity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


public class Sign_in extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        //Sign in by phone number
        Button btnSignInByPhone = (Button) findViewById(R.id.btn_signPhone);
        btnSignInByPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentPhone = new Intent(Sign_in.this, LuaChonLoaiTaiKhoanActivity.class);
                startActivity(intentPhone);
            }
        });
    }
}
