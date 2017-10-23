package android.familydoctor.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Class.BacSi;
import android.familydoctor.R;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by buimi on 6/19/2017.
 */

public class XemTTBacSi_Act extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private BacSi bacSi;
    FloatingActionButton fabCall;
    TextView txtTen, txtChuyenMon, txtDiaChi, txtEmail, txtSDT;
    ImageView imganhdaidien,imgvanbang;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemttbacsi);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        init();


    }

    private void init() {
        Intent it = getIntent();
        String name = it.getStringExtra("name");
        final String sdt = it.getStringExtra("sdt");
        String chuyenmon = it.getStringExtra("chuyenmon");
        String diachi = it.getStringExtra("diachi");
        String url1 = it.getStringExtra("url1");
        String url2 = it.getStringExtra("url2");
        String show =
                name + "\n" + sdt  + "\n" + chuyenmon + "\n" + diachi + "\n" + url1 + "\n" + url2;
        Log.d("THONGTIN", show);
        imganhdaidien= (ImageView) findViewById(R.id.imgAnhdaidien);
        imgvanbang= (ImageView) findViewById(R.id.imgVanBang);
        txtTen = (TextView) findViewById(R.id.txtTen);
        txtChuyenMon = (TextView) findViewById(R.id.txtChuyenMon);
        txtDiaChi = (TextView) findViewById(R.id.txtDiaChi);
        txtSDT = (TextView) findViewById(R.id.txtSDT);
        final ProgressBar progressAnhDaiDien = (ProgressBar) findViewById(R.id.progressAnhdaidien);
        final ProgressBar progressVanBang = (ProgressBar) findViewById(R.id.progressVanBang);
        txtTen.setText(getResources().getString(R.string.Name)+":"+name);
        txtChuyenMon.setText(getResources().getString(R.string.Doctorname)+":"+chuyenmon);
        txtDiaChi.setText(getResources().getString(R.string.Address)+" : "+diachi);
        txtSDT.setText(getResources().getString(R.string.Phone_number)+": "+sdt);
        Picasso.with(this).load(url1).into(imganhdaidien, new Callback() {
            @Override
            public void onSuccess() {
                progressAnhDaiDien.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                imgvanbang.setImageResource(R.drawable.no_picture_available);
            }
        });
        Picasso.with(this).load(url2).into(imgvanbang, new Callback() {
            @Override
            public void onSuccess() {
                progressVanBang.setVisibility(View.GONE);
            }

            @Override
            public void onError() {
                imgvanbang.setImageResource(R.drawable.no_picture_available);
            }
        });
        findViewById(R.id.fabCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + sdt));
                if (ActivityCompat.checkSelfPermission(XemTTBacSi_Act.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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
