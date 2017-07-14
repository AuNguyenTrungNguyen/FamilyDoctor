package android.familydoctor.Activity;

import android.content.Intent;
import android.familydoctor.Class.BacSi;
import android.os.Bundle;
import android.familydoctor.R;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by buimi on 6/19/2017.
 */

public class CaiDat_TaiKhoan extends AppCompatActivity {



    private DatabaseReference databaseReference;

    String id="";
    BacSi bacSi;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tt_caidattaikhoan);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        //Get ID
        Bundle bundle=this.getIntent().getExtras();
        id = bundle.getString("id");

        //Firebase



    }

    public void chinhsuaUser(View view){
        Intent intent = new Intent(CaiDat_TaiKhoan.this, User_Edit.class);
        intent.putExtra("id",id);
        startActivity(intent);
    }






}
