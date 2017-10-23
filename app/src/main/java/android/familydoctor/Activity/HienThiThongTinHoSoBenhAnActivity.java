package android.familydoctor.Activity;

import android.content.Intent;
import android.familydoctor.Adapter.AdapterThongTinHoSoBenhAn;
import android.familydoctor.Class.BacSi;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.Fragment.FragmentHoSoBenhAn;
import android.familydoctor.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HienThiThongTinHoSoBenhAnActivity extends AppCompatActivity {

    ExpandableListView elvDanhSachThuocShow;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;
    AdapterThongTinHoSoBenhAn adapterThongTinHoSoBenhAn;

    DatabaseReference databaseHSBA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_thong_tin_ho_so_benh_an);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        final TextView txtNameShow = (TextView) findViewById(R.id.txtNameShow);
        TextView txtTenBenhShow = (TextView) findViewById(R.id.txtTenBenhShow);
        TextView txtNgayKhamShow = (TextView) findViewById(R.id.txtNgayKhamShow);
        TextView txtNgayTaiKhamShow = (TextView) findViewById(R.id.txtNgayTaiKhamShow);

        elvDanhSachThuocShow = (ExpandableListView) findViewById(R.id.elvDanhSachThuocShow);
        listTenThuoc = new ArrayList<>();//list header
        listThongTinThuoc = new HashMap<>();//child list

        Intent intent = getIntent();

        if(intent != null){
            //Nhận hồ sơ bệnh án từ FragmentHoSoBenhAn để xem thông tin đầy dử
            final HoSoBenh hsb = (HoSoBenh) intent.getSerializableExtra("hoSoBenhAn");

            // Kiểm tra người dùng là bác sĩ hay bệnh nhân
            int dinhDanh = LoginPhone.dinhDanh;

            //Lấy toàn bộ danh sách thuốc trong Hồ sơ bệnh án
            List<Thuoc> listThuocTrongHSBA = hsb.getThuocDung();

            for (int i = 0; i <listThuocTrongHSBA.size() ; i++) {
                // thêm tất cả các tên thuốc để gán vào list Header Adapter
                listTenThuoc.add(listThuocTrongHSBA.get(i).getTenThuoc());

                // thêm tất cả các thông tin thuốc để gán vào list Child Adapter
                listThongTinThuoc.put(listThuocTrongHSBA.get(i).getTenThuoc(),listThuocTrongHSBA.get(i));
            }

            //gán tên bệnh, ngày khám và ngày tái khám
            txtTenBenhShow.setText(getResources().getString(R.string.The_disease_needs_treatment)+": "+hsb.getTenBenh());
            txtNgayKhamShow.setText(getResources().getString(R.string.Day_of_the_examination)+" "+hsb.getNgayKham());
            txtNgayTaiKhamShow.setText(getResources().getString(R.string.On_reexamination)+" "+hsb.getNgayTaiKham());

            adapterThongTinHoSoBenhAn = new AdapterThongTinHoSoBenhAn(HienThiThongTinHoSoBenhAnActivity.this, listTenThuoc, listThongTinThuoc);
            elvDanhSachThuocShow.setAdapter(adapterThongTinHoSoBenhAn);

            databaseHSBA = FirebaseDatabase.getInstance().getReference();

            //nếu người đang đăng nhập là bác sĩ
            if(dinhDanh == 1){
                //lấy id bệnh nhân ra để tìm tên bệnh nhân
                final String idBenhNhan = hsb.getIdBenhNhan();
                databaseHSBA.child("User_BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BenhNhan benhNhan = data.getValue(BenhNhan.class);
                            assert benhNhan != null;
                            if (benhNhan.getSoDienThoaiBenhNhan().equals(idBenhNhan)){
                                txtNameShow.setText(getResources().getString(R.string.Patient_name)+": " + benhNhan.getHoTenBenhNhan());
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            //nếu người đang đăng nhập là bệnh nhân
            }else if(dinhDanh == 2){
                //lấy id bác sĩ ra để tìm tên bác sĩ
                final String idBacSi = hsb.getIdBacSi();
                databaseHSBA.child("User_BacSi").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BacSi bacSi = data.getValue(BacSi.class);
                            assert bacSi != null;
                            if (bacSi.getSoDienThoaiBacSi().equals(idBacSi)){
                                txtNameShow.setText(getResources().getString(R.string.Patient_name)+": " + bacSi.getHoTenBacSi());
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }
}
