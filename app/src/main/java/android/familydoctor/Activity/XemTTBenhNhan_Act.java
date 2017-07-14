package android.familydoctor.Activity;

import android.familydoctor.Class.BacSi;
import android.familydoctor.Class.HoSoBenh;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.familydoctor.R;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buimi on 6/19/2017.
 */

public class XemTTBenhNhan_Act extends AppCompatActivity{

    Intent intent = this.getIntent();

    List<HoSoBenh> listHoSoBenh = new ArrayList<>();
    DatabaseReference databaseReference;
    private String id;
    private String iduser;

    int position=-1;

    String tenngtao;
    HoSoBenh hoSoBenh;
    BacSi bacSi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemttbenhnhan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Lấy ID từ Item

        Bundle extras = this.getIntent().getExtras();
        id = extras.getString("id");
        Log.e("ID INTENT", id);

        //GetDATA

                                //SetText
                                ImageView imageView = (ImageView) findViewById(R.id.img_canmua_view);
                                TextView tenNS = (TextView) findViewById(R.id.tv_ten_canmua_view);
                                TextView tenNguoiMua = (TextView) findViewById(R.id.tv_nguoimua_canmua_view);
                                TextView loaiNS = (TextView) findViewById(R.id.tv_loai_canmua_view);
                                TextView soLuong = (TextView) findViewById(R.id.tv_soluong_canmua_view);
                                TextView giaMua = (TextView) findViewById(R.id.tv_giamua_canmua_view);
                                TextView diaChiGD = (TextView) findViewById(R.id.tv_diachi_giaodich_canmua_view);

                                Button sdt = (Button) findViewById(R.id.btn_sdt_congty);
                                final Button email = (Button) findViewById(R.id.btn_email_congty);


    }


}
