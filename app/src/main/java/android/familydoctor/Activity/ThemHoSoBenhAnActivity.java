package android.familydoctor.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.familydoctor.Adapter.AdapterThuoc;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ThemHoSoBenhAnActivity extends AppCompatActivity {

    Toolbar toolbarThemHoSoBenhAn;

    LinearLayout layoutThemHoSoBenhAn;
    EditText edtSoDienThoaiCanKiemTra, edtTenBenhTrongHoSoBenhAn;
    TextView txtChonNgayTaiKham;
    Button btnThemThuoc, btnKiemTra, btnHoanThanhHoSoBenhAn;

    ListView lvDanhSachThuocDaThem;
    ArrayList<Thuoc> listThuocSeThem = new ArrayList<>();
    AdapterThuoc adapterThuoc;

    public static final int REQUEST_CODE = 999;
    public static final int RESULT_CODE = 998;

    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ho_so_benh_an);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //taoBenhNhanTrenFirebase();

        addControls();

        addEvents();
    }

    private void taoBenhNhanTrenFirebase() {

        for (int i = 1; i < 10; i++) {
            String soDienThoaiBenhNhan = String.valueOf(i * 11111111);
            String namSinhBenhNhan = String.valueOf(1990 + i);
            String hoTenBenhNhan = "Bệnh nhân: " + i;
            //emailBenhNhan = diaChiBenhNhan = uriHinhAnhBenhNhan = xBenhNhan = yBenhNhan= "";
            BenhNhan benhNhan = new BenhNhan(soDienThoaiBenhNhan, namSinhBenhNhan, hoTenBenhNhan, "", "", "", 1, 1);
            databaseReference.child("BenhNhan").child(soDienThoaiBenhNhan).setValue(benhNhan);
        }
    }

    private void addEvents() {
        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String soDienThoai = edtSoDienThoaiCanKiemTra.getText().toString();
                btnHoanThanhHoSoBenhAn.setVisibility(View.GONE);
                layoutThemHoSoBenhAn.setVisibility(View.GONE);
                btnThemThuoc.setVisibility(View.GONE);
                lvDanhSachThuocDaThem.setVisibility(View.GONE);
                databaseReference.child("BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = 1;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BenhNhan benhNhan = data.getValue(BenhNhan.class);
                            assert benhNhan != null;
                            count++;
                            if (soDienThoai.equals(benhNhan.getSoDienThoaiBenhNhan())) {
                                String tenBenhNhan = benhNhan.getHoTenBenhNhan();
                                String namSinhBenhNhan = benhNhan.getNamSinhBenhNhan();

                                final Dialog dialogKiemTra = new Dialog(ThemHoSoBenhAnActivity.this);
                                dialogKiemTra.setTitle("Thông tin của: " + soDienThoai);
                                dialogKiemTra.setContentView(R.layout.dialog_kiem_tra_so_dien_thoai);

                                EditText edtTenBenhNhan, edtNamSinhBenhNhan;
                                edtTenBenhNhan = (EditText) dialogKiemTra.findViewById(R.id.edtTenBenhNhan);
                                edtNamSinhBenhNhan = (EditText) dialogKiemTra.findViewById(R.id.edtNamSinhBenhNhan);

                                edtTenBenhNhan.setText(tenBenhNhan);
                                edtNamSinhBenhNhan.setText(namSinhBenhNhan);

                                dialogKiemTra.show();

                                Button btnLapHoSo, btnHuyBo;

                                btnLapHoSo = (Button) dialogKiemTra.findViewById(R.id.btnLapHoSo);
                                btnHuyBo = (Button) dialogKiemTra.findViewById(R.id.btnHuyBo);

                                btnHuyBo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Toast.makeText(ThemHoSoBenhAnActivity.this, "Hủy bỏ lập hồ sơ", Toast.LENGTH_SHORT).show();
                                        dialogKiemTra.dismiss();
                                    }
                                });

                                btnLapHoSo.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        btnHoanThanhHoSoBenhAn.setVisibility(View.VISIBLE);
                                        layoutThemHoSoBenhAn.setVisibility(View.VISIBLE);
                                        btnThemThuoc.setVisibility(View.VISIBLE);
                                        lvDanhSachThuocDaThem.setVisibility(View.VISIBLE);
                                        dialogKiemTra.dismiss();
                                    }
                                });

                                break;
                            }
                        }

                        if(count > dataSnapshot.getChildrenCount()){
                            Toast.makeText(ThemHoSoBenhAnActivity.this, "Số điện thoại này không tồn tại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDanhSachThuoc = new Intent(ThemHoSoBenhAnActivity.this, DanhSachThuocActivity.class);
                startActivityForResult(intentDanhSachThuoc, REQUEST_CODE);
            }
        });

        btnHoanThanhHoSoBenhAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ThemHoSoBenhAnActivity.this, "Put HSBA chỗ này nè", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addControls() {

        toolbarThemHoSoBenhAn = (Toolbar) findViewById(R.id.toolbarThemHoSoBenhAn);
        setSupportActionBar(toolbarThemHoSoBenhAn);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        layoutThemHoSoBenhAn = (LinearLayout) findViewById(R.id.layoutThemHoSoBenhAn);
        edtSoDienThoaiCanKiemTra = (EditText) findViewById(R.id.edtSoDienThoaiCanKiemTra);
        edtTenBenhTrongHoSoBenhAn = (EditText) findViewById(R.id.edtTenBenhTrongHoSoBenhAn);
        txtChonNgayTaiKham = (TextView) findViewById(R.id.txtChonNgayTaiKham);
        btnThemThuoc = (Button) findViewById(R.id.btnThemThuoc);
        btnKiemTra = (Button) findViewById(R.id.btnKiemTra);
        btnHoanThanhHoSoBenhAn = (Button) findViewById(R.id.btnHoanThanhHoSoBenhAn);
        lvDanhSachThuocDaThem = (ListView) findViewById(R.id.lvDanhSachThuocDaThem);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE && data != null) {

            ArrayList<Thuoc> listThuoc = (ArrayList<Thuoc>) data.getSerializableExtra("listThuocSeThem");

            for (int i = 0; i < listThuoc.size(); i++){
                listThuocSeThem.add(listThuoc.get(i));
            }

            adapterThuoc = new AdapterThuoc(this, R.layout.item_thuoc, listThuocSeThem);
            lvDanhSachThuocDaThem.setAdapter(adapterThuoc);
        }
    }
}
