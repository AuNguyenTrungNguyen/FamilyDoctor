package android.familydoctor.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Activity.MainActivity;
import android.familydoctor.Class.BenhNhan;
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

public class FragmentBenhNhan extends Fragment {


    private static String ARG_SECTION_NUMBER = "secion_number";

    public FragmentBenhNhan() {
    }

    public static FragmentBenhNhan newInstance(int sectionNumber) {
        FragmentBenhNhan fragment = new FragmentBenhNhan();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    private DatabaseReference mDatabase;
    private FirebaseStorage firebaseStorage;
    private ProgressDialog progressDialog;
    private Spinner NamSinh;
    private EditText HoTen, DiaChi;
    private Button setData;
    private ImageView img;
    private double x;
    private double y;
    BenhNhan Us = new BenhNhan();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_benh_nhan, container, false);
        // xin quyen chup anh
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
        }

        // khai bao can ban
        progressDialog = new ProgressDialog(getContext());
        HoTen = (EditText) view.findViewById(R.id.HoTenE);
        NamSinh = (Spinner) view.findViewById(R.id.spNamSinhBenhNhan);
        DiaChi = (EditText) view.findViewById(R.id.DiaChiE);
        img = (ImageView) view.findViewById(R.id.ImgAvaE);
        setData = (Button) view.findViewById(R.id.Submit);

        // tao nam sinh tu dong
        List<String> namList = new ArrayList<>();
        for (int i = 1960; i < 2018; i++) {

            namList.add(i + "");
        }

        ArrayAdapter aa = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, namList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set nam sinh vao adpter
        NamSinh.setAdapter(aa);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        // xac dinh tai khoan tren firebase
        //firebase storing de luu tru anh
        firebaseStorage = FirebaseStorage.getInstance();

        img = (ImageView) view.findViewById(R.id.ImgAvaE);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String key = mDatabase.child("User_BenhNhan").push().getKey();
                //  chon cay noi de luu anh
                new AlertDialog.Builder(getContext()).setNeutralButton(getResources().getString(R.string.Take_a_new_photo), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);
                    }
                })
                        .setNegativeButton(getResources().getString(R.string.Select_a_photo_from_the_gallery), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.setType("image/*");
                                // luu vao bitmap voi dinh dah la 2
                                startActivityForResult(intent, 2);
                            }
                        })
                        .show();
            }
        });
        // goi du lieu di
        setData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage(getResources().getString(R.string.Processing));
                progressDialog.show();
                String hoTen = HoTen.getText().toString();
                String namSinh = NamSinh.getSelectedItem().toString();
                String sdt = LoginPhone.sdt_key;
                String diaChi = DiaChi.getText().toString();

                // llay toa do
                LocationManager manager = (LocationManager) getContext().getSystemService(LOCATION_SERVICE);
                GPSTracker gpsTracker = new GPSTracker(getContext());
                if (gpsTracker != null && manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    Location location = gpsTracker.getLocation();
                    x = location.getLatitude() ;
                    y =location.getLongitude() ;
                }

                Us = new BenhNhan(hoTen, namSinh, sdt, diaChi,x,y);

                //Up Hình ảnh
                StorageReference storageReference = firebaseStorage.getReferenceFromUrl("gs://familydoctor-56b96.appspot.com");//*
                StorageReference reference = storageReference.child(System.currentTimeMillis() + "jpg");//*
                    // lua hinh anh vao bitmap
                Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                // phantich than mang va put len firebase
                byte[] bitMapData = stream.toByteArray();

                UploadTask uploadTask = reference.putBytes(bitMapData);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(getActivity(), getResources().getString(R.string.Error_no_registration_information), Toast.LENGTH_LONG).show();
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Us.setUriHinhAnhBenhNhan(downloadUrl.toString());

                        mDatabase.child("User_BenhNhan").child(Us.getSoDienThoaiBenhNhan()).setValue(Us);
                        progressDialog.hide();
                        Toast.makeText(getContext(), getResources().getString(R.string.Successfully_registered_information), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getContext(), MainActivity.class));
                    }
                });

            }
        });


        return view;
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