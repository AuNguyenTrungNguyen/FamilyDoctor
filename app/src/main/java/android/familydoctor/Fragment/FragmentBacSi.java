package android.familydoctor.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Activity.MainActivity;
import android.familydoctor.Class.BacSi;
import android.familydoctor.Class.DanhSach_LinhVuc_Thuoc;
import android.familydoctor.R;
import android.familydoctor.service.GPSTracker;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
    private ProgressDialog progressDialog;
    Spinner NamSinh, edtChuyenMon;
    EditText HoTen, DiaChi;
    Button setData;
    ImageView imgXT, imgAva;
    double x = 0.0;
    double y = 0.0;
    BacSi Us;
    boolean uploadAnhXong = false;

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
        imgAva = (ImageView) view.findViewById(R.id.Ava);
        imgXT = (ImageView) view.findViewById(R.id.ImgXacThuc);
        setData = (Button) view.findViewById(R.id.SubmitD);
        edtChuyenMon = (Spinner) view.findViewById(R.id.edtChuyenMon);
        //Tạo danh sách năm
        List<String> namList = new ArrayList<>();
        for (int i = 1960; i < 2018; i++) {
            namList.add(i + "");
        }

        ArrayAdapter chuyenmon = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, DanhSach_LinhVuc_Thuoc.getLinhvucchuyenmon());
        chuyenmon.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        edtChuyenMon.setAdapter(chuyenmon);

        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, namList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        NamSinh.setAdapter(aa);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        firebaseStorage = FirebaseStorage.getInstance();

        //Set hình ảnh đại diện và ảnh xác thực bác sĩ
        imgAva = (ImageView) view.findViewById(R.id.Ava);
        imgAva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getContext()).setNeutralButton(getResources().getString(R.string.Take_a_new_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                }).setNegativeButton(getResources().getString(R.string.Select_a_photo_from_the_gallery), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("scale", true);
                        intent.putExtra("outputX", 256);
                        intent.putExtra("outputY", 256);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("return-data", true);
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
                new AlertDialog.Builder(getContext()).setNeutralButton(getResources().getString(R.string.Take_a_new_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 3);
                    }
                }).setNegativeButton(getResources().getString(R.string.Select_a_photo_from_the_gallery), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                        intent.setType("image/*");
                        intent.putExtra("crop", "true");
                        intent.putExtra("scale", true);
                        intent.putExtra("outputX", 256);
                        intent.putExtra("outputY", 256);
                        intent.putExtra("aspectX", 1);
                        intent.putExtra("aspectY", 1);
                        intent.putExtra("return-data", true);
                        startActivityForResult(intent, 4);
                    }
                })
                        .show();
            }
        });

        //Put dữ liệu
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(getResources().getString(R.string.Processing));
                progressDialog.show();
                String key = mDatabase.child("Users").push().getKey();

                //Khởi tạo và thêm dữ liệu cho USER
                String hoTen = HoTen.getText().toString();
                String namSinh = NamSinh.getSelectedItem().toString();
                String sdt = LoginPhone.sdt_key;
                String diaChi = DiaChi.getText().toString();
                String chuyenMon = edtChuyenMon.getSelectedItem().toString();
                LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                GPSTracker gpsTracker = new GPSTracker(getContext());
                if (gpsTracker != null && manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Location location = gpsTracker.getLocation();
                    x = location.getLatitude();
                    y = location.getLongitude();
                }
                Us = new BacSi(hoTen, sdt, namSinh, chuyenMon, diaChi, x, y);
                Log.d("hbqhbq", x + "     " + y);

                //Khai báo Up Hình ảnh
                StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://familydoctor-56b96.appspot.com/");
                StorageReference reference = storageReference.child(System.currentTimeMillis() + "jpg");

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


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(),getResources().getString(R.string.Error_no_registration_information), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Us.setUriHinhAnhBacSi(downloadUrl.toString());

                        if (uploadTask.isSuccessful()) {
                            mDatabase.child("User_BacSi").child(Us.getSoDienThoaiBacSi()).setValue(Us);
                            progressDialog.hide();
                            Toast.makeText(getContext(), getResources().getString(R.string.Successfully_registered_information), Toast.LENGTH_SHORT).show();

                            uploadAnhXong = true;
                        }
                    }
                });

                Handler hanler = new Handler();
                hanler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getContext(), "xong anh dai dien1", Toast.LENGTH_SHORT).show();
                    }
                }, 2000);
                StorageReference storageReferenceXT = firebaseStorage.getReferenceFromUrl("gs://familydoctor-56b96.appspot.com/");
                StorageReference referenceXT = storageReference.child(System.currentTimeMillis() + "jpg");
                final UploadTask uploadTaskXT = referenceXT.putBytes(bitMapDataXT);

                uploadTaskXT.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.Error_no_registration_information), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Us.setUriVanBangBacSi(downloadUrl.toString());

                        if (uploadTaskXT.isSuccessful()) {
                            mDatabase.child("User_BacSi").child(Us.getSoDienThoaiBacSi()).setValue(Us);
                            progressDialog.hide();
                            Toast.makeText(getContext(),getResources().getString(R.string.Successfully_registered_information), Toast.LENGTH_SHORT).show();
                            uploadAnhXong = true;
                        }
                    }
                });
                Handler hanler2 = new Handler();
                hanler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(getContext(), "xong anh dai dien2", Toast.LENGTH_SHORT).show();
                        if (uploadAnhXong == true) {
                            startActivity(new Intent(getContext(), MainActivity.class));

                        }
                    }
                }, 2000);


            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == 2) {
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                imgAva.setImageBitmap(newProfilePic);
            }
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
            final Bundle extras = data.getExtras();
            if (extras != null) {
                //Get image
                Bitmap newProfilePic = extras.getParcelable("data");
                imgXT.setImageBitmap(newProfilePic);
            }
        }

    }
}
