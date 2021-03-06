package android.familydoctor.Activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.familydoctor.Adapter.AdapterThuoc;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.graphics.Color;
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
            String hoTenBenhNhan = getResources().getString(R.string.Patient)+" : " + i;
            //emailBenhNhan = diaChiBenhNhan = uriHinhAnhBenhNhan = xBenhNhan = yBenhNhan= "";
            BenhNhan benhNhan = new BenhNhan(soDienThoaiBenhNhan, namSinhBenhNhan, hoTenBenhNhan, "", "", "", 1, 1);
            databaseReference.child("User_BenhNhan").child(soDienThoaiBenhNhan).setValue(benhNhan);
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
                txtChonNgayTaiKham.setText( getResources().getString(R.string.Choose_reexamination_date));
                edtTenBenhTrongHoSoBenhAn.setText("");

                databaseReference.child("User_BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean tonTai = false;
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BenhNhan benhNhan = data.getValue(BenhNhan.class);
                            assert benhNhan != null;
                            if (soDienThoai[0].equals(benhNhan.getSoDienThoaiBenhNhan())) {
                                tonTai = true;
                                String tenBenhNhan = benhNhan.getHoTenBenhNhan();
                                String namSinhBenhNhan = benhNhan.getNamSinhBenhNhan();

                                final Dialog dialogKiemTra = new Dialog(ThemHoSoBenhAnActivity.this);
                                dialogKiemTra.setTitle(getResources().getString(R.string.Information_of) + soDienThoai[0]);
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
                                        Toast.makeText(ThemHoSoBenhAnActivity.this, getResources().getString(R.string.cancel) , Toast.LENGTH_SHORT).show();
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
                                        edtSoDienThoaiCanKiemTra.clearFocus();
                                        edtTenBenhTrongHoSoBenhAn.findFocus();

                                    }
                                });

                                break;
                            }
                        }

                        if (!tonTai) {
                            Toast.makeText(ThemHoSoBenhAnActivity.this, getResources().getString(R.string.Phone_number_does_not_exist) , Toast.LENGTH_SHORT).show();
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
                final int mMonth = calendar.get(Calendar.MONTH);
                int mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog date = new DatePickerDialog(ThemHoSoBenhAnActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                String dayString = String.valueOf(dayOfMonth);
                                String monthString = String.valueOf((month+1));

                                if(dayOfMonth < 10){
                                    dayString = "0" + dayOfMonth;
                                }

                                if((month+1) < 10){
                                    monthString = "0" + (month+1);
                                }
                                String ngayTaiKham = year + "" + monthString + "" + dayString;

                                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyyMMdd");
                                Date now = new Date();
                                String ngayHienTai = sdfDate.format(now);

                                if(Integer.parseInt(ngayTaiKham) > Integer.parseInt(ngayHienTai)){
                                    txtChonNgayTaiKham.setText(dayString + "-" + monthString + "-" + year);
                                }else{
                                    Toast.makeText(ThemHoSoBenhAnActivity.this, getResources().getString(R.string.Invalid_return_date) , Toast.LENGTH_SHORT).show();
                                }

                            }
                        },  mYear, mMonth, mDay);
                date.setTitle(getResources().getString(R.string.Choose_reexamination_date));
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
                String strDateFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

                if(!soDienThoai[0].equals("") &&
                   !txtChonNgayTaiKham.getText().equals( getResources().getString(R.string.Choose_reexamination_date)) &&
                   listThuocSeThem.size() != 0){
                    HoSoBenh hoSoBenh = new HoSoBenh();

                    String idHoSoBenh = getDateTimeSystem();

                    hoSoBenh.setIdHoSo(idHoSoBenh);
                    hoSoBenh.setIdBacSi(LoginPhone.sdt_key);
                    hoSoBenh.setIdBenhNhan(soDienThoai[0]);
                    hoSoBenh.setTenBenh(edtTenBenhTrongHoSoBenhAn.getText().toString());
                    hoSoBenh.setNgayKham(sdf.format(date));
                    hoSoBenh.setNgayTaiKham(txtChonNgayTaiKham.getText().toString());
                    hoSoBenh.setThuocDung(listThuocSeThem);
                    databaseReference.child("HoSoBenhAn").child(idHoSoBenh).setValue(hoSoBenh);
                    Toast.makeText(ThemHoSoBenhAnActivity.this,  getResources().getString(R.string.Add_medical_records_successfully) , Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    Toast.makeText(ThemHoSoBenhAnActivity.this,  getResources().getString(R.string.The_information_is_not_valid) , Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addControls() {

        toolbarThemHoSoBenhAn = (Toolbar) findViewById(R.id.toolbarThemHoSoBenhAn);
        toolbarThemHoSoBenhAn.setTitleTextColor(Color.WHITE);
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

    private String getDateTimeSystem() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate + "-" +System.currentTimeMillis();
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
