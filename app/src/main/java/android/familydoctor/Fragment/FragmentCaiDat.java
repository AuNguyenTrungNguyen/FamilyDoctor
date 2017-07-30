package android.familydoctor.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.familydoctor.Activity.CapNhatThongTinCaNhan_Act;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Activity.MainActivity;
import android.familydoctor.Activity.ThongTinUngDungActivity;
import android.familydoctor.R;
import android.familydoctor.Activity.Sign_in;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Au Nguyen on 7/15/2017.
 */

public class FragmentCaiDat extends Fragment {

    TextView txtCapNhatThongTin, txtThongTinUngDung, txtPhanHoiUngDung, txtDangXuat;

    // khai báo tài khoản
    private FirebaseAuth mAuth;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cai_dat, container, false);

        //xác định tài khoản tại firebase
        mAuth = FirebaseAuth.getInstance();

        txtCapNhatThongTin = (TextView) rootView.findViewById(R.id.txtCapNhatThongTin);
        txtThongTinUngDung = (TextView) rootView.findViewById(R.id.txtThongTinUngDung);
        txtPhanHoiUngDung = (TextView) rootView.findViewById(R.id.txtPhanHoiUngDung);
        txtDangXuat = (TextView) rootView.findViewById(R.id.txtDangXuat);

        txtCapNhatThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent txtCapNhatThongTin = new Intent(getContext(), CapNhatThongTinCaNhan_Act.class);
                startActivity(txtCapNhatThongTin);
            }
        });

        txtThongTinUngDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThongTinUngDung = new Intent(getContext(), ThongTinUngDungActivity.class);
                startActivity(intentThongTinUngDung);
            }
        });

        txtPhanHoiUngDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phanHoiUngDung();
            }
        });

        txtDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(getContext()).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        getActivity().finish();
                        Intent intent = new Intent(getActivity(),LoginPhone.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("Hủy", null)
                        .setMessage("Bạn có chắc chắn muốn đăng xuất không ?")
                        .setTitle("Xác nhận thoát")
                        .show();
            }
        });

        return rootView;
    }

    public void phanHoiUngDung() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:familydoctor1996@gmail.com"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Phản hồi về FamilyDoctor");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Không tìm thấy Gmail", Toast.LENGTH_SHORT).show();
        }
    }
}
