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

        }
    }
}
