package android.familydoctor.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.familydoctor.Class.BacSi;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.familydoctor.Class.DanhSach_LinhVuc_Thuoc;
import android.familydoctor.R;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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

/**
 * Created by buimi on 6/23/2017.
 */

public class User_Edit extends AppCompatActivity {
    final Context context = this;
    private Toolbar toolbar;
    private ImageView imageView;

    private DatabaseReference databaseReference;
    private FirebaseStorage firebaseStorage;

    String id = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suattcanhan);

        initToolBar();
        initDanhSachGoiY();
        setHinhAnh();

        //Get ID
        Bundle bundle=this.getIntent().getExtras();
        id = bundle.getString("id");

    }


    public void luuUser(View view){

        AutoCompleteTextView tenNgdung = (AutoCompleteTextView) findViewById(R.id.tv_add_ten_user);
        EditText sdt = (EditText) findViewById(R.id.tv_add_sdt_user);
        EditText diachi = (EditText) findViewById(R.id.tv_diachi_user);
        EditText kynang = (EditText) findViewById(R.id.tv_kynang_user);
        Spinner lv = (Spinner) findViewById(R.id.spinner_linhvuc);
        Spinner kv = (Spinner) findViewById(R.id.spinner_khuvuc);

        final BacSi bacSi = new BacSi();
        bacSi.setHoten(tenNgdung.getText().toString());
        bacSi.setSdt(sdt.getText().toString());
        bacSi.setDiachi(diachi.getText().toString());
        bacSi.setKynang(kynang.getText().toString());
        bacSi.setLinhvucchuyenmon(lv.getSelectedItem().toString());
        bacSi.setKhuvuc(kv.getSelectedItem().toString());


        //Updata
        databaseReference= FirebaseDatabase.getInstance().getReference();
        final String key = databaseReference.child("USER").push().getKey();
        bacSi.setId(id);

        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://startlinktesttheme.appspot.com/");

        StorageReference reference= storageReference.child(key+".jpg");
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();

        UploadTask uploadTask = reference.putBytes(bitMapData);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                bacSi.setImgUserURL(downloadUrl+"");
                databaseReference.child("USER").child(id).child(id).setValue(bacSi);

            }
        });

        databaseReference.child("USER").child(id).child(id).setValue(bacSi);

        Toast.makeText(this, "Đã lưu thành công", Toast.LENGTH_SHORT).show();
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
        Spinner khuvuc = (Spinner) findViewById(R.id.spinner_khuvuc);
        Spinner linhvuc = (Spinner) findViewById(R.id.spinner_linhvuc);
        khuvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        linhvuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter adapterKhuVuc = new ArrayAdapter(this,android.R.layout.simple_spinner_item, DanhSach_LinhVuc_Thuoc.getKhuvuc());
        ArrayAdapter adapterLinhVuc = new ArrayAdapter(this,android.R.layout.simple_spinner_item, DanhSach_LinhVuc_Thuoc.getLoai());

        adapterKhuVuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterLinhVuc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        khuvuc.setAdapter(adapterKhuVuc);
        linhvuc.setAdapter(adapterLinhVuc);
    }


    public void setHinhAnh(){
        imageView = (ImageView) findViewById(R.id.img_add_user);
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
                                intent.setType("image/*");
                                startActivityForResult(intent, 2);
                            }
                        })
                        .show();

            }
        });
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
