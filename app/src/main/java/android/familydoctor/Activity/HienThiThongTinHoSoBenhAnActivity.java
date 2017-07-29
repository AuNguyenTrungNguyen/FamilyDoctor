package android.familydoctor.Activity;

import android.content.Intent;
import android.familydoctor.Adapter.AdapterThongTinHoSoBenhAn;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HienThiThongTinHoSoBenhAnActivity extends AppCompatActivity {

    ExpandableListView elvDanhSachThuocShow;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;
    AdapterThongTinHoSoBenhAn adapterThongTinHoSoBenhAn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hien_thi_thong_tin_ho_so_benh_an);

        TextView txtNameShow = (TextView) findViewById(R.id.txtNameShow);
        TextView txtTenBenhShow = (TextView) findViewById(R.id.txtTenBenhShow);
        TextView txtNgayKhamShow = (TextView) findViewById(R.id.txtNgayKhamShow);
        TextView txtngayTaiKhamShow = (TextView) findViewById(R.id.txtNgayTaiKhamShow);
        elvDanhSachThuocShow = (ExpandableListView) findViewById(R.id.elvDanhSachThuocShow);
        listTenThuoc = new ArrayList<>();//list header
        listThongTinThuoc = new HashMap<>();//child list

        Intent intent = getIntent();

        if(intent != null){
            HoSoBenh hsb = (HoSoBenh) intent.getSerializableExtra("hoSoBenhAn");
            List<Thuoc> listThuocTrongHSBA = hsb.getThuocDung();

            //for gán list header
            for (int i = 0; i <listThuocTrongHSBA.size() ; i++) {
                listTenThuoc.add(listThuocTrongHSBA.get(i).getTenThuoc());
                listThongTinThuoc.put(listThuocTrongHSBA.get(i).getTenThuoc(),listThuocTrongHSBA.get(i));
            }

            //event

            txtNameShow.setText("Họ và tên: " + hsb.getIdBacSi());
            txtTenBenhShow.setText("Bệnh cần điều trị : "+hsb.getTenBenh());
            txtNgayKhamShow.setText("Ngày khám : "+hsb.getNgayKham());
            txtngayTaiKhamShow.setText("Ngày tái khám : "+hsb.getNgayTaiKham());
            adapterThongTinHoSoBenhAn = new AdapterThongTinHoSoBenhAn(HienThiThongTinHoSoBenhAnActivity.this, listTenThuoc, listThongTinThuoc);
            elvDanhSachThuocShow.setAdapter(adapterThongTinHoSoBenhAn);
        }
    }
}
