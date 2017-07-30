package android.familydoctor.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by buimi on 6/19/2017.
 */

public class XemTTBenhNhan_Act extends AppCompatActivity{

    List<HoSoBenh> listHoSoBenh = new ArrayList<>();
    DatabaseReference databaseReference;
    FloatingActionButton fab;
    ImageView img;
    TextView txtTen,txtDiaChi,txtSDT;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemttbenhnhan);
        final ProgressBar progressAnhDaiDien = (ProgressBar) findViewById(R.id.progressAnhdaidien);
        img= (ImageView) findViewById(R.id.imgAnhdaidien);
        txtDiaChi= (TextView) findViewById(R.id.txtDiaChi);
        txtSDT= (TextView) findViewById(R.id.txtSDT);
        txtTen= (TextView) findViewById(R.id.txtTen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }


        Intent it = getIntent();
        String name = it.getStringExtra("name");
        final String sdt = it.getStringExtra("sdt");
        String diachi = it.getStringExtra("diachi");
        String url1 = it.getStringExtra("url1");
        txtTen.setText("Tên bệnh nhân: "+ name);
        txtSDT.setText("SDT liên hệ: "+sdt);
        txtDiaChi.setText("Địa chỉ : "+diachi);
        Picasso.with(this).load(url1).into(img, new Callback() {
            @Override
            public void onSuccess() {
                progressAnhDaiDien.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                img.setImageResource(R.drawable.no_picture_available);
            }
        });







        findViewById(R.id.fabCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + sdt));
                if (ActivityCompat.checkSelfPermission(XemTTBenhNhan_Act.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
        });
    }
}
