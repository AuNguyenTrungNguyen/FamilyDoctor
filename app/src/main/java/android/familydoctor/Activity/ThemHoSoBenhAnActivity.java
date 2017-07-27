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
import android.widget.CheckBox;
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
    TextView txtChonNgayTaiKham, txtHeader;
    Button btnThemThuoc, btnKiemTra, btnHoanThanhHoSoBenhAn;

    ListView lvDanhSachThuocDaThem;
    ArrayList<Thuoc> danhSachThuoc;
    AdapterThuoc adapterThuoc;

    EditText edtTenThuocSeThem, edtSoLuongThuoc;
    Button btnHoanThanh;
    CheckBox chkSang, chkTrua, chkChieu;
    EditText edtSoLuongSang, edtDonViSang;
    EditText edtSoLuongTrua, edtDonViTrua;
    EditText edtSoLuongChieu, edtDonViChieu;

    public static final int REQUEST_CODE = 999;
    public static final int RESULT_CODE = 998;

    DatabaseReference databaseReference;


    public static final String ID_BacSi = "12345678";


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
            BenhNhan benhNhan = new BenhNhan(soDienThoaiBenhNhan, namSinhBenhNhan, hoTenBenhNhan, "", "", "", "", "");
            databaseReference.child("BenhNhan").child(soDienThoaiBenhNhan).setValue(benhNhan);
        }
    }

    private void addEvents() {
        danhSachThuoc = new ArrayList<>();
        adapterThuoc = new AdapterThuoc(ThemHoSoBenhAnActivity.this, R.layout.item_thuoc, danhSachThuoc);
        lvDanhSachThuocDaThem.setAdapter(adapterThuoc);

        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String soDienThoai = edtSoDienThoaiCanKiemTra.getText().toString();
                btnHoanThanhHoSoBenhAn.setVisibility(View.GONE);
                layoutThemHoSoBenhAn.setVisibility(View.GONE);
                btnThemThuoc.setVisibility(View.GONE);
                lvDanhSachThuocDaThem.setVisibility(View.GONE);
                txtHeader.setVisibility(View.GONE);
                databaseReference.child("BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = 1;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BenhNhan benhNhan = data.getValue(BenhNhan.class);
                            assert benhNhan != null;
                            count++;
                            if (soDienThoai.equals(benhNhan.getSoDienThoaiBenhNhan())) {
                                String tenBenhNhan = benhNhan.getHotTenBenhNhan();
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
                                        txtHeader.setVisibility(View.VISIBLE);
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
                Toast.makeText(ThemHoSoBenhAnActivity.this, "Làm PUT firebase đi cha", Toast.LENGTH_SHORT).show();
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
        txtHeader = (TextView) findViewById(R.id.txtHeader);

        btnThemThuoc = (Button) findViewById(R.id.btnThemThuoc);
        btnKiemTra = (Button) findViewById(R.id.btnKiemTra);
        btnHoanThanhHoSoBenhAn = (Button) findViewById(R.id.btnHoanThanhHoSoBenhAn);

        lvDanhSachThuocDaThem = (ListView) findViewById(R.id.lvDanhSachThuocDaThem);
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == REQUEST_CODE && resultCode == RESULT_CODE && data != null) {
//
//            final String tenThuoc = data.getStringExtra("tenThuoc");
//
//            final Dialog dialogThemThuoc = new Dialog(ThemHoSoBenhAnActivity.this);
//            dialogThemThuoc.setTitle("Thông tin thuốc:");
//            dialogThemThuoc.setContentView(R.layout.dialog_them_thuoc_moi);
//
//            edtTenThuocSeThem = (EditText) dialogThemThuoc.findViewById(R.id.edtTenThuocSeThem);
//            edtSoLuongThuoc = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongThuoc);
//
//            edtTenThuocSeThem.setText(tenThuoc);
//
//            btnHoanThanh = (Button) dialogThemThuoc.findViewById(R.id.btnHoanThanh);
//
//            dialogThemThuoc.show();
//
//            chkSang = (CheckBox) dialogThemThuoc.findViewById(R.id.chkSang);
//            chkTrua = (CheckBox) dialogThemThuoc.findViewById(R.id.chkTrua);
//            chkChieu = (CheckBox) dialogThemThuoc.findViewById(R.id.chkChieu);
//
//            chkSang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                    edtSoLuongSang = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongSang);
//                    edtDonViSang = (EditText) dialogThemThuoc.findViewById(R.id.edtDonViSang);
//
//                    if (b) {
//                        edtSoLuongSang.setVisibility(View.VISIBLE);
//                        edtDonViSang.setVisibility(View.VISIBLE);
//                    } else {
//                        edtSoLuongSang.setVisibility(View.INVISIBLE);
//                        edtDonViSang.setVisibility(View.INVISIBLE);
//                    }
//                }
//            });
//
//
//            chkTrua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                    edtSoLuongTrua = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongTrua);
//                    edtDonViTrua = (EditText) dialogThemThuoc.findViewById(R.id.edtDonViTrua);
//
//                    if (b) {
//                        edtSoLuongTrua.setVisibility(View.VISIBLE);
//                        edtDonViTrua.setVisibility(View.VISIBLE);
//                    } else {
//                        edtSoLuongTrua.setVisibility(View.INVISIBLE);
//                        edtDonViTrua.setVisibility(View.INVISIBLE);
//                    }
//                }
//            });
//
//            chkChieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//
//                    edtSoLuongChieu = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongChieu);
//                    edtDonViChieu = (EditText) dialogThemThuoc.findViewById(R.id.edtDonViChieu);
//
//                    if (b) {
//                        edtSoLuongChieu.setVisibility(View.VISIBLE);
//                        edtDonViChieu.setVisibility(View.VISIBLE);
//                    } else {
//                        edtSoLuongChieu.setVisibility(View.INVISIBLE);
//                        edtDonViChieu.setVisibility(View.INVISIBLE);
//                    }
//                }
//            });
//
//            btnHoanThanh.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    Thuoc thuoc = new Thuoc();
//                    thuoc.setTenThuoc(tenThuoc);
//                    thuoc.setSoLuong("Số lượng: "+edtSoLuongThuoc.getText().toString());
//
//                    String lieuDungSang = "";
//                    String lieuDungTrua = "";
//                    String lieuDungChieu = "";
//
//                    if (chkSang.isChecked()) {
//                        lieuDungSang += chkSang.getText().toString() + edtSoLuongSang.getText().toString() + " " + edtDonViSang.getText().toString();
//                    }else{
//                        lieuDungSang = "";
//                    }
//
//                    if (chkTrua.isChecked()) {
//                        lieuDungTrua += chkTrua.getText().toString() + edtSoLuongTrua.getText().toString() + " " + edtDonViTrua.getText().toString();
//                    }else{
//                        lieuDungTrua = "";
//                    }
//
//                    if (chkChieu.isChecked()) {
//                        lieuDungChieu += chkChieu.getText().toString() + edtSoLuongChieu.getText().toString() + " " + edtDonViChieu.getText().toString();
//                    }else{
//                        lieuDungChieu = "";
//                    }
//
//                    thuoc.setLieuDungSang(lieuDungSang);
//                    thuoc.setLieuDungTrua(lieuDungTrua);
//                    thuoc.setLieuDungChieu(lieuDungChieu);
//
//                    danhSachThuoc.add(thuoc);
//
//                    dialogThemThuoc.dismiss();
//
//                    adapterThuoc.notifyDataSetChanged();
//                }
//            });
//        }
//    }
}
