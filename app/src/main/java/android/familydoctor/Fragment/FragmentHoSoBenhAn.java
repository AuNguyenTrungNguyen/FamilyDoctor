package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.content.Intent;
import android.familydoctor.Activity.HienThiThongTinHoSoBenhAnActivity;
import android.familydoctor.Activity.LoginPhone;
import android.familydoctor.Adapter.AdapterThongTinHoSoBanhAnBacSi;
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
    AdapterThongTinHoSoBanhAnBacSi adapter;
    LinearLayoutManager layoutManager;
    List<HoSoBenh> list = new ArrayList<>();
    int dinhDanh = LoginPhone.dinhDanh;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ho_so_benh_an, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewHoSoBenhAn);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        databaseHSBA = FirebaseDatabase.getInstance().getReference();

        adapter = new AdapterThongTinHoSoBanhAnBacSi(list, dinhDanh);
        recyclerView.setAdapter(adapter);

        loadDuLieu();

        adapter.setOnItemClickListener(new AdapterThongTinHoSoBanhAnBacSi.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {

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

        final String id = LoginPhone.sdt_key;

        databaseHSBA.child("HoSoBenhAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HoSoBenh hoSoBenh = dataSnapshot.getValue(HoSoBenh.class);
                assert hoSoBenh != null;
                if(dinhDanh == 1){
                    if (TextUtils.equals(hoSoBenh.getIdBacSi(), id)) {
                        adapter.addItem(list.size(), hoSoBenh);
                    }
                }else if (dinhDanh == 2){
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
