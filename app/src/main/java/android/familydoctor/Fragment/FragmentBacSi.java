package android.familydoctor.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Activity.MainActivity;
import android.familydoctor.Class.BacSi;
import android.familydoctor.R;
import android.familydoctor.service.GPSTracker;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import static android.app.Activity.RESULT_OK;
import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Au Nguyen on 7/14/2017.
 */

public class FragmentBacSi extends Fragment {

    private static String ARG_SECTION_NUMBER = "secion_number";

    public FragmentBacSi() {
    }

    public static FragmentBacSi newInstance(int sectionNumber) {
        FragmentBacSi fragment = new FragmentBacSi();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private DatabaseReference mDatabase;
    private FirebaseStorage firebaseStorage;
    final Context context = this.getContext();
    private ImageView imageView;
    private ProgressDialog progressDialog;
    private String id;

    Spinner NamSinh ;
    EditText HoTen ,SDT ,DiaChi,edtChuyenMon ;
    Button setData;
    ImageView imgXT,imgAva ;
    double x = 0.0;
    double y = 0.0;
    BacSi Us ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bac_si, container, false);

        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

        progressDialog = new ProgressDialog(getContext());

        HoTen = (EditText) view.findViewById(R.id.HoTenD);
        NamSinh = (Spinner) view.findViewById(R.id.spNamSinhBacSi);
        DiaChi = (EditText) view.findViewById(R.id.DiaChiD);
        imgAva  = (ImageView) view.findViewById(R.id.Ava);
        imgXT  = (ImageView) view.findViewById(R.id.ImgXacThuc);
        setData = (Button) view.findViewById(R.id.SubmitD);
        edtChuyenMon= (EditText) view.findViewById(R.id.edtChuyenMon);
        //Tạo danh sách năm
        List<String> namList = new ArrayList<>();
        for (int i= 1960; i< 2018; i++){
            namList.add(i+"");
        }

        ArrayAdapter aa= new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, namList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NamSinh.setAdapter(aa);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();

        //Set hình ảnh đại diện và ảnh xác thực bác sĩ
        imgAva = (ImageView) view.findViewById(R.id.Ava);
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setNeutralButton("Chụp ảnh mới", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                }).setNegativeButton("Chọn ảnh từ thư viện", new DialogInterface.OnClickListener() {
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

        imgXT = (ImageView) view.findViewById(R.id.ImgXacThuc);
        imgXT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setNeutralButton("Chụp ảnh mới", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 3);
                    }
                }).setNegativeButton("Chọn ảnh từ thư viện", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("image/*");
                        startActivityForResult(intent, 4);
                    }
                })
                        .show();
            }
        });
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Đang đăng ký.....");
                progressDialog.show();
                String key = mDatabase.child("User").push().getKey();

                //Khởi tạo và thêm dữ liệu cho USER
                String hoTen = HoTen.getText().toString();
                String namSinh = NamSinh.getSelectedItem().toString();
                String sdt = LoginPhone.sdt_key;
                String diaChi = DiaChi.getText().toString();
                String chuyenMon = edtChuyenMon.getText().toString();
                LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                GPSTracker gpsTracker = new GPSTracker(getContext());
                if (gpsTracker != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Location location = gpsTracker.getLocation();
                    x = location.getLatitude() ;
                    y =location.getLongitude() ;
                }
                Us = new BacSi(hoTen,sdt,namSinh,chuyenMon,diaChi,x,y);
                Log.d("hbqhbq",x+"     "+y);

                //Khai báo Up Hình ảnh
                StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://familydoctor-56b96.appspot.com/");
                StorageReference reference = storageReference.child("Users").child(key+"jpg");

                // stream avata
                Bitmap bitmap = ((BitmapDrawable) imgAva.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                byte[] bitMapData = stream.toByteArray();

                final UploadTask uploadTask = reference.putBytes(bitMapData);

                //stream xác thực
                Bitmap bitmapXT = ((BitmapDrawable) imgXT.getDrawable()).getBitmap();
                ByteArrayOutputStream streamXT = new ByteArrayOutputStream();
                bitmapXT.compress(Bitmap.CompressFormat.JPEG, 100, streamXT);
                byte[] bitMapDataXT = streamXT.toByteArray();

                final UploadTask uploadTaskXT = reference.putBytes(bitMapDataXT);

                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(),"lổi không đăng kí thông tin được",Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Us.setUriHinhAnhBacSi(downloadUrl.toString());

                        if (uploadTaskXT.isSuccessful()){
                            mDatabase.child("User_BacSi").child(Us.getSoDienThoaiBacSi()).setValue(Us);
                            progressDialog.hide();
                            Toast.makeText(getContext(), "Đã đăng ký xong", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    }
                });
                uploadTaskXT.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(),"lổi không đăng kí thông tin được",Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Us.setUriVanBangBacSi(downloadUrl.toString());

                        if (uploadTask.isSuccessful()){
                            mDatabase.child("User_BacSi").child(Us.getSoDienThoaiBacSi()).setValue(Us);
                            progressDialog.hide();
                            Toast.makeText(getContext(), "Đã đăng ký xong", Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(getContext(), MainActivity.class));
                        }
                    }
                });

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 2) {
            Uri imageUri2 = data.getData();
            imgAva.setImageURI(imageUri2);
        }
        if (resultCode == RESULT_OK && requestCode == 1) {
            // lay hinh thu nho cua hinh vua chup
            Bitmap hinh2 = (Bitmap) data.getExtras().get("data");
            imgAva.setImageBitmap(hinh2);
        }
        if (resultCode == RESULT_OK && requestCode == 3) {
            // lay hinh thu nho cua hinh vua chup
            Bitmap hinh1 = (Bitmap) data.getExtras().get("data");
            imgXT.setImageBitmap(hinh1);
        }

        if (resultCode == RESULT_OK && requestCode == 4) {

            Uri imageUri1 = data.getData();
            imgXT.setImageURI(imageUri1);
        }

    }
}
