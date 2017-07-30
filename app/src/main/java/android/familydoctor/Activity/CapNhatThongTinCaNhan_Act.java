package android.familydoctor.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.familydoctor.Class.BacSi;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.service.GPSTracker;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.familydoctor.R;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.key;

/**
 * Created by buimi on 6/19/2017.
 */

public class CapNhatThongTinCaNhan_Act extends AppCompatActivity{

    private DatabaseReference mDatabase;
    private FirebaseStorage firebaseStorage;

    private Spinner NamSinh;
    private EditText HoTen, DiaChi;
    private Button setData;
    private ImageView img;

    private BacSi bacSi = new BacSi();
    private BenhNhan benhNhan = new BenhNhan();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xemttbacsi);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_about);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        HoTen = (EditText) findViewById(R.id.HoTenE);
        NamSinh = (Spinner) findViewById(R.id.spNamSinhBenhNhan);
        DiaChi = (EditText) findViewById(R.id.DiaChiE);
        img = (ImageView) findViewById(R.id.ImgAvaE);
        setData = (Button) findViewById(R.id.Submit);

        List<String> namList = new ArrayList<>();
        for (int i = 1960; i < 2018; i++) {
            namList.add(i + "");
        }
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, namList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NamSinh.setAdapter(aa);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();

        img = (ImageView) findViewById(R.id.ImgAvaE);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new AlertDialog.Builder(getApplicationContext()).setNeutralButton("Chụp ảnh mới", new DialogInterface.OnClickListener() {
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
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            }
                        })
                        .show();
            }
        });

        if (LoginPhone.dinhDanh == 1){
            //bac si
            capNhatBacSi();
        }else if (LoginPhone.dinhDanh == 2){
            //Benh nhan
            capNhatBenhNhan();
        }


    }

    private void capNhatBenhNhan(){
        final String key = mDatabase.child("User_BenhNhan").push().getKey();
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hoTen = HoTen.getText().toString();
                String namSinh = NamSinh.getSelectedItem().toString();
                String diaChi = DiaChi.getText().toString();

                benhNhan.setHoTenBenhNhan(hoTen);
                benhNhan.setNamSinhBenhNhan(namSinh);
                benhNhan.setDiaChiBenhNhan(diaChi);

                //Up Hình ảnh
                StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://familydoctor-56b96.appspot.com/");
                StorageReference reference = storageReference.child("Users").child(key + "jpg");

                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitMapData = stream.toByteArray();

                UploadTask uploadTask = reference.putBytes(bitMapData);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "lổi không đăng kí thông tin được", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        benhNhan.setUriHinhAnhBenhNhan(downloadUrl.toString());

                        mDatabase.child("User_BenhNhan").child(benhNhan.getSoDienThoaiBenhNhan()).setValue(benhNhan);
                    }
                });

            }
        });

    }

    private void capNhatBacSi(){
        final String key = mDatabase.child("User_BacSi").push().getKey();
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String hoTen = HoTen.getText().toString();
                String namSinh = NamSinh.getSelectedItem().toString();
                String diaChi = DiaChi.getText().toString();

                bacSi.setHoTenBacSi(hoTen);
                bacSi.setNamSinhBacSi(namSinh);
                bacSi.setDiaChiBacSi(diaChi);

                //Up Hình ảnh
                StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://familydoctor-56b96.appspot.com/");
                StorageReference reference = storageReference.child("Users").child(key + "jpg");

                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitMapData = stream.toByteArray();

                UploadTask uploadTask = reference.putBytes(bitMapData);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getApplicationContext(), "lổi không đăng kí thông tin được", Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        benhNhan.setUriHinhAnhBenhNhan(downloadUrl.toString());

                        mDatabase.child("User_BacSi").child(benhNhan.getSoDienThoaiBenhNhan()).setValue(benhNhan);
                    }
                });

            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 1) {
            // lay hinh thu nho cua hinh vua chup
            Bitmap hinh = (Bitmap) data.getExtras().get("data");
            img.setImageBitmap(hinh);
        }

        if (resultCode == RESULT_OK && requestCode == 2) {

            Uri imageUri = data.getData();
            img.setImageURI(imageUri);
        }
    }

}
