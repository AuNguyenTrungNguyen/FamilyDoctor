package android.familydoctor.Fragment;

import android.content.Intent;
import android.familydoctor.Activity.ThongTinUngDungActivity;
import android.familydoctor.R;
import android.familydoctor.Activity.Sign_in;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
                //Phần này thì giao cho Tâm nhé
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
                Toast.makeText(getContext(), "Chưa up app lên CHPlay", Toast.LENGTH_SHORT).show();
            }
        });

        txtDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().finish();
                Intent intent = new Intent(getActivity(),Sign_in.class);
                startActivity(intent);
            }
        });

        return rootView;
    }
}
