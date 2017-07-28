package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.content.Intent;
import android.familydoctor.Activity.ThemHoSoBenhAnActivity;
import android.familydoctor.Adapter.ItemHoSoBenh_Adapter;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;


public class FragmentHoSoBenhAn extends Fragment {
    DatabaseReference databaseHSBA;
    DatabaseReference databaseBS = FirebaseDatabase.getInstance().getReference("BacSi");
    DatabaseReference databaseBN = FirebaseDatabase.getInstance().getReference("BenhNhan");
    FloatingActionButton fabThemHoSoBenhAn;
    RecyclerView recyclerView;
    ItemHoSoBenh_Adapter adapter;
    LinearLayoutManager layoutManager;
    List<HoSoBenh> list = new ArrayList<HoSoBenh>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ho_so_benh_an, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewHoSoBenhAn);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        databaseHSBA = FirebaseDatabase.getInstance().getReference();
        databaseHSBA.child("HSBA").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HoSoBenh hsb = dataSnapshot.getValue(HoSoBenh.class);
                list.add(hsb);
                adapter.addItem(list.size(),hsb);
                Log.d("FireBaseQuoc", hsb.getTenBenh() + "  " + hsb.getNgayKham() + "   " + hsb.getNgayTaiKham());
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


        adapter = new ItemHoSoBenh_Adapter(list);
        recyclerView.setAdapter(adapter);


        fabThemHoSoBenhAn = (FloatingActionButton) rootView.findViewById(R.id.fabThemHoSoBenhAn);

        fabThemHoSoBenhAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThemHoSoBenhAn = new Intent(getContext(), ThemHoSoBenhAnActivity.class);
                startActivity(intentThemHoSoBenhAn);
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
