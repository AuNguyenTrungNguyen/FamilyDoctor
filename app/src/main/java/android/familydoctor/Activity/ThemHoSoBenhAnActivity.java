package android.familydoctor.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ThemHoSoBenhAnActivity extends AppCompatActivity {

    Toolbar toolbarThemHoSoBenhAn;

    LinearLayout layoutThemHoSoBenhAn;
    EditText edtSoDienThoaiCanKiemTra, edtTenBenhTrongHoSoBenhAn;
    TextView txtChonNgayTaiKham;
    Button btnThemThuoc, btnKiemTra, btnHoanThanhHoSoBenhAn;

    ListView lvDanhSachThuocDaThem;
    ArrayList<Thuoc> danhSachThuoc;

    EditText edtTenThuocSeThem, edtSoLuongThuoc;
    Button btnHoanThanh;
    CheckBox chkSang, chkTrua, chkChieu;
    EditText edtSoLuongSang, edtDonViSang;
    EditText edtSoLuongTrua, edtDonViTrua;
    EditText edtSoLuongChieu, edtDonViChieu;

    public static final int REQUEST_CODE = 999;
    public static final int RESULT_CODE = 998;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_ho_so_benh_an);

        addControls();

        addEvents();
    }

    private void addEvents() {
        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String soDienThoai = edtSoDienThoaiCanKiemTra.getText().toString();
                if (soDienThoai.equals("123")) {
                    final Dialog dialogKiemTra = new Dialog(ThemHoSoBenhAnActivity.this);
                    dialogKiemTra.setTitle("Thông tin của: " + soDienThoai);
                    dialogKiemTra.setContentView(R.layout.dialog_kiem_tra_so_dien_thoai);
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

                } else {
                    btnHoanThanhHoSoBenhAn.setVisibility(View.GONE);
                    layoutThemHoSoBenhAn.setVisibility(View.GONE);
                    btnThemThuoc.setVisibility(View.GONE);
                    lvDanhSachThuocDaThem.setVisibility(View.GONE);
                    Toast.makeText(ThemHoSoBenhAnActivity.this, "Số điện thoại không tồn tại", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentDanhSachThuoc = new Intent(ThemHoSoBenhAnActivity.this, DanhSachThuocActivity.class);
                startActivityForResult(intentDanhSachThuoc, REQUEST_CODE);
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

            final String tenThuoc = data.getStringExtra("tenThuoc");

            final Dialog dialogThemThuoc = new Dialog(ThemHoSoBenhAnActivity.this);
            dialogThemThuoc.setTitle("Thông tin thuốc:");
            dialogThemThuoc.setContentView(R.layout.dialog_them_thuoc_moi);

            edtTenThuocSeThem = (EditText) dialogThemThuoc.findViewById(R.id.edtTenThuocSeThem);
            edtSoLuongThuoc = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongThuoc);

            edtTenThuocSeThem.setText(tenThuoc);

            btnHoanThanh = (Button) dialogThemThuoc.findViewById(R.id.btnHoanThanh);

            dialogThemThuoc.show();

            chkSang = (CheckBox) dialogThemThuoc.findViewById(R.id.chkSang);
            chkTrua = (CheckBox) dialogThemThuoc.findViewById(R.id.chkTrua);
            chkChieu = (CheckBox) dialogThemThuoc.findViewById(R.id.chkChieu);

            chkSang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    edtSoLuongSang = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongSang);
                    edtDonViSang = (EditText) dialogThemThuoc.findViewById(R.id.edtDonViSang);

                    if(b){
                        edtSoLuongSang.setVisibility(View.VISIBLE);
                        edtDonViSang.setVisibility(View.VISIBLE);
                    }else{
                        edtSoLuongSang.setVisibility(View.INVISIBLE);
                        edtDonViSang.setVisibility(View.INVISIBLE);
                    }
                }
            });


            chkTrua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    edtSoLuongTrua = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongTrua);
                    edtDonViTrua = (EditText) dialogThemThuoc.findViewById(R.id.edtDonViTrua);

                    if(b){
                        edtSoLuongTrua.setVisibility(View.VISIBLE);
                        edtDonViTrua.setVisibility(View.VISIBLE);
                    }else{
                        edtSoLuongTrua.setVisibility(View.INVISIBLE);
                        edtDonViTrua.setVisibility(View.INVISIBLE);
                    }
                }
            });

            chkChieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    edtSoLuongChieu = (EditText) dialogThemThuoc.findViewById(R.id.edtSoLuongChieu);
                    edtDonViChieu = (EditText) dialogThemThuoc.findViewById(R.id.edtDonViChieu);

                    if(b){
                        edtSoLuongChieu.setVisibility(View.VISIBLE);
                        edtDonViChieu.setVisibility(View.VISIBLE);
                    }else{
                        edtSoLuongChieu.setVisibility(View.INVISIBLE);
                        edtDonViChieu.setVisibility(View.INVISIBLE);
                    }
                }
            });

            btnHoanThanh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Thuoc thuoc = new Thuoc();
                    thuoc.setTenThuoc(tenThuoc);
                    thuoc.setSoLuong(edtSoLuongThuoc.getText().toString());

                    String lieuDung = "";

                    if(chkSang.isChecked()){
                        lieuDung += chkSang.getText().toString() + edtSoLuongSang.getText().toString() + " " + edtDonViSang.getText().toString() + "\n";
                    }

                    if(chkTrua.isChecked()){
                        lieuDung += chkTrua.getText().toString() + edtSoLuongTrua.getText().toString() + " " + edtDonViTrua.getText().toString() + "\n";
                    }

                    if(chkChieu.isChecked()){
                        lieuDung += chkChieu.getText().toString() + edtSoLuongChieu.getText().toString() + " " + edtDonViChieu.getText().toString()+ "\n";
                    }

                    thuoc.setLieuDung(lieuDung);

                    dialogThemThuoc.dismiss();
                    Toast.makeText(ThemHoSoBenhAnActivity.this, "Thêm thuốc thành công.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
