package android.familydoctor.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.R;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

public class Them_HoSoBenhAn extends AppCompatActivity {


    final Context context = this;
    private Toolbar toolbar;
    private ImageView imageView;
    BenhNhan benhNhan = new BenhNhan();

    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themhosobenhan);

        initToolBar();
        initDanhSachGoiY();


        Bundle bundle=this.getIntent().getExtras();

        id = bundle.getString("id");

        imageView = (ImageView) findViewById(R.id.img_add_nongsan_ban);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                .setNeutralButton("Chụp ảnh mới", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                })
                .setNegativeButton("Chọn ảnh từ thư viện", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image*//*");
                        startActivityForResult(intent, 2);
                    }
                })
                .show();

            }
        });




    }

    public String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    public void luuKhoiNghiep(View view){

        databaseReference= FirebaseDatabase.getInstance().getReference();

        //Lấy ngày hệ thống làm ID
//        Calendar c = Calendar.getInstance();
//        int ms = c.get(Calendar.MILLISECOND);
//        int s = c.get(Calendar.SECOND);
//        int m = c.get(Calendar.MINUTE);
//        int h = c.get(Calendar.HOUR);
//        int d = c.get(Calendar.DATE);
//        int mon = c.get(Calendar.MONTH);
//        int y = c.get(Calendar.YEAR);
//        Random r = new Random();
//        int ramdom = r.nextInt(1000 - 0) + 0;
//
//        final String key = y+mon+d+h+m+s+ms+ramdom+"";




        AutoCompleteTextView tenNongSan= (AutoCompleteTextView) findViewById(R.id.tv_add_ten_ban);
        Spinner loaiNongSan = (Spinner) findViewById(R.id.spinner_add_loai_ns_ban);

        EditText soLuong= (EditText) findViewById(R.id.tv_add_soluong_ban);
        EditText giaBan= (EditText) findViewById(R.id.tv_add_gia_ban);
        DatePicker ngayThuHoach= (DatePicker) findViewById(R.id.tv_add_time_batdau_thuhoach);
        DatePicker ngayketthuc= (DatePicker) findViewById(R.id.tv_add_time_ketthuc_thuhoach);
        EditText diaDiemGiaoDich= (EditText) findViewById(R.id.tv_add_diadiem_ban);



//        String ten = tenNongSan.getText().toString();
//
//        Log.i("ten",ten);

        int dayOfMonth=ngayThuHoach.getDayOfMonth();
        int monthOfYear=ngayThuHoach.getMonth();
        int year=ngayThuHoach.getYear();
        String dateNgayThuHoach=checkDigit(dayOfMonth+1)+"/"+checkDigit(monthOfYear)+"/"+year;

        int dayOfMonthKT=ngayThuHoach.getDayOfMonth();
        int monthOfYearKT=ngayThuHoach.getMonth();
        int yearKT=ngayThuHoach.getYear();
        String dateNgayThuHoachKT=checkDigit(dayOfMonth+1)+"/"+checkDigit(monthOfYear)+"/"+year;


        //Up Hình ảnh
//        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//        byte[] bitMapData = stream.toByteArray();
//
//        UploadTask uploadTask = reference.putBytes(bitMapData);
//        uploadTask.addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception exception) {
//            }
//        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                benhNhan.setImg_url(downloadUrl+"");
//                databaseReference.child("NONG_SAN_BAN").child(key).setValue(benhNhan);
//
//            }
//        });


        finish();


    }

    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initDanhSachGoiY(){
        Spinner spin = (Spinner) findViewById(R.id.spinner_add_loai_ns_ban);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

       /* ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item, DanhSach_LinhVuc_Thuoc.getLoai());
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);*/
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            // lay hinh thu nho cua hinh vua chup
            Bitmap hinh = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(hinh);

        }

        if (resultCode == RESULT_OK && requestCode == 2) {

            Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }
}
