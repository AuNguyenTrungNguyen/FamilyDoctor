package android.familydoctor.Activity;

import android.content.Intent;
import android.familydoctor.Class.BacSi;
import android.net.Uri;
import android.os.Bundle;
import android.familydoctor.Class.BenhNhan;
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

public class XemTTBacSi_Act extends AppCompatActivity{

    private Intent intent = this.getIntent();

    private List<BenhNhan> listBenhNhan = new ArrayList<>();
    private DatabaseReference databaseReference;
    private String id;
    private String iduser;

    private int position=-1;

    private String tenngtao;
    private BenhNhan benhNhan;
    private BacSi bacSi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemttbacsi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        //Lấy ID từ Item

        Bundle extras = this.getIntent().getExtras();
        id = extras.getString("id");
        Log.e("ID INTENT", id);

                            //SetText
                            ImageView imageView = (ImageView) findViewById(R.id.img_nongsan_ban_view);
                            TextView tenNongSan = (TextView) findViewById(R.id.tv_ten_nongsan_ban);
                            TextView tenNguoiBan = (TextView) findViewById(R.id.tv_nguonban_nongsan_ban);
                            TextView loaiNongSan = (TextView) findViewById(R.id.tv_loai_nongsan_ban);
                            TextView soLuong_NS = (TextView) findViewById(R.id.tv_soluong_nongsan_ban);
                            TextView giaBan = (TextView) findViewById(R.id.tv_giaban_ban_view);
                            TextView ngayThuHoach = (TextView) findViewById(R.id.tv_time_batdau_th_nongsan_ban);
                            TextView ngayKetThuc_ThHoach = (TextView) findViewById(R.id.tv_time_ketthuc_th_nongsan_ban);
                            TextView diaDiemGiaodich = (TextView) findViewById(R.id.tv_diadiem_nongsan_ban);

    }
}
