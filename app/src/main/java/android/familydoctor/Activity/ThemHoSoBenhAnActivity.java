package android.familydoctor.Activity;

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ThemHoSoBenhAnActivity extends AppCompatActivity {

    Toolbar toolbarThemHoSoBenhAn;

    LinearLayout layoutThemHoSoBenhAn;
    EditText edtSoDienThoaiCanKiemTra, edtTenBenhTrongHoSoBenhAn;
    TextView txtChonNgayTaiKham;
    Button btnThemThuoc, btnKiemTra, btnHoanThanhHoSoBenhAn;

    ListView lvDanhSachThuocDaThem;
    ArrayList<Thuoc> listThuocSeThem;
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

        final String[] soDienThoai = {""};

        btnKiemTra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                soDienThoai[0] = edtSoDienThoaiCanKiemTra.getText().toString();
                btnHoanThanhHoSoBenhAn.setVisibility(View.GONE);
                layoutThemHoSoBenhAn.setVisibility(View.GONE);
                btnThemThuoc.setVisibility(View.GONE);
                lvDanhSachThuocDaThem.setVisibility(View.GONE);

                listThuocSeThem = new ArrayList<>();
                adapterThuoc = new AdapterThuoc(ThemHoSoBenhAnActivity.this, R.layout.item_thuoc, listThuocSeThem);
                lvDanhSachThuocDaThem.setAdapter(adapterThuoc);
                txtChonNgayTaiKham.setText("CHỌN NGÀY TÁI KHÁM");
                edtTenBenhTrongHoSoBenhAn.setText("");

                databaseReference.child("BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int count = 1;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BenhNhan benhNhan = data.getValue(BenhNhan.class);
                            assert benhNhan != null;
                            count++;
                            if (soDienThoai[0].equals(benhNhan.getSoDienThoaiBenhNhan())) {
                                String tenBenhNhan = benhNhan.getHoTenBenhNhan();
                                String namSinhBenhNhan = benhNhan.getNamSinhBenhNhan();

                                final Dialog dialogKiemTra = new Dialog(ThemHoSoBenhAnActivity.this);
                                dialogKiemTra.setTitle("Thông tin của: " + soDienThoai[0]);
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

                        if (count > dataSnapshot.getChildrenCount()) {
                            Toast.makeText(ThemHoSoBenhAnActivity.this, "Số điện thoại này không tồn tại.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        txtChonNgayTaiKham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog date = new DatePickerDialog(ThemHoSoBenhAnActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                txtChonNgayTaiKham.setText(dayOfMonth + "/" + month + "/" + year);
                            }
                        },  mYear, mMonth, mDay);
                date.setTitle("Chọn ngày tái khám");
                date.show();

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

                Date date = new Date();

                String strDateFormat = "dd/MM/yyyy";

                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

                String toast = "SĐT BN" + soDienThoai[0] + "\n";
                toast += "Tên bệnh: " + edtTenBenhTrongHoSoBenhAn.getText() + "\n";
                toast += "Ngày khám" + sdf.format(date) + "\n";
                toast += "Ngày tài khám" + txtChonNgayTaiKham.getText() + "\n";
                toast += "Thuốc: " + listThuocSeThem.size() +"\n";

                Toast.makeText(ThemHoSoBenhAnActivity.this, toast, Toast.LENGTH_SHORT).show();
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

            //Kiểm tra người dùng thêm thuốc lại thì sẽ update

            if (listThuocSeThem.size() == 0) {
                for (int i = 0; i < listThuoc.size(); i++) {
                    Thuoc thuocGui = listThuoc.get(i);
                    listThuocSeThem.add(thuocGui);
                }
            } else {
                for (int i = 0; i < listThuoc.size(); i++) {
                    Thuoc thuocGui = listThuoc.get(i);
                    boolean checkAdd = true;
                    for (int j = 0; j < listThuocSeThem.size(); j++) {
                        Thuoc thuocNhan = listThuocSeThem.get(j);
                        if (thuocGui.getTenThuoc().equals(thuocNhan.getTenThuoc())) {
                            checkAdd = false;
                            thuocNhan.setSoLuong(thuocGui.getSoLuong());
                            thuocNhan.setLieuDungSang(thuocGui.getLieuDungSang());
                            thuocNhan.setLieuDungTrua(thuocGui.getLieuDungTrua());
                            thuocNhan.setLieuDungChieu(thuocGui.getLieuDungChieu());
                            break;
                        }
                    }
                    if(checkAdd){
                        listThuocSeThem.add(thuocGui);
                    }
                }
            }

            adapterThuoc = new AdapterThuoc(this, R.layout.item_thuoc, listThuocSeThem);
            lvDanhSachThuocDaThem.setAdapter(adapterThuoc);
        }
    }
}
