package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.familydoctor.Activity.HienThiThongTinHoSoBenhAnActivity;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Adapter.AdapterHoSoBenhAn;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class FragmentHoSoBenhAn extends Fragment {

    DatabaseReference databaseHSBA;

    RecyclerView recyclerView;
    AdapterHoSoBenhAn adapter;
    LinearLayoutManager layoutManager;
    List<HoSoBenh> list = new ArrayList<>();

    // xem người đang đăng nhập là bác sĩ hay bệnh nhân
    int dinhDanh = LoginPhone.dinhDanh;

    // số điện thoại người dùng hiện hành
    String id = LoginPhone.sdt_key;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ho_so_benh_an, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewHoSoBenhAn);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        databaseHSBA = FirebaseDatabase.getInstance().getReference();

        adapter = new AdapterHoSoBenhAn(list, dinhDanh,getContext() );

        recyclerView.setAdapter(adapter);

        // hàm đọc toàn bộ hồ sơ bệnh án của người dùng hiện hành
        loadDuLieu();

        // xử lý sự kiện khi chọn vào 1 hồ sơ bệnh án
        adapter.setOnItemClickListener(new AdapterHoSoBenhAn.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

                // chuyển đến Activity Hiển thị thông tin hồ sơ bệnh án
                Intent intentHienThiThongTinHoSoBenhAn = new Intent(getContext(), HienThiThongTinHoSoBenhAnActivity.class);
                HoSoBenh hoSoBenh = list.get(position);
                intentHienThiThongTinHoSoBenhAn.putExtra("hoSoBenhAn", (Serializable) hoSoBenh);
                startActivity(intentHienThiThongTinHoSoBenhAn);
            }
        });

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void loadDuLieu() {
        // duyệt tất cả hồ sơ bệnh án
        databaseHSBA.child("HoSoBenhAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                HoSoBenh hoSoBenh = dataSnapshot.getValue(HoSoBenh.class);
                assert hoSoBenh != null;
                // người dùng hiện hành là bác sĩ
                if(dinhDanh == 1){
                    //nếu id của bác sĩ trong Hồ sơ bệnh án bằng với id hiện hành sẽ hiển thị hồ sơ bệnh án này
                    if (TextUtils.equals(hoSoBenh.getIdBacSi(), id)) {
                        adapter.addItem(list.size(), hoSoBenh);
                    }
                // người dùng hiện hành là bệnh nhân
                }else if (dinhDanh == 2){
                    //nếu id của bệnh nhân trong Hồ sơ bệnh án bằng với id hiện hành sẽ hiển thị hồ sơ bệnh án này
                    if (TextUtils.equals(hoSoBenh.getIdBenhNhan(), id)) {
                        adapter.addItem(list.size(), hoSoBenh);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
