package android.familydoctor.Fragment;

import android.familydoctor.Adapter.ItemHoSoBenh_Adapter;
import android.familydoctor.Adapter.Item;
import android.familydoctor.Class.BacSi;
import android.familydoctor.R;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DanhSachBacSi_BenhNhan extends Fragment {

    private List<Item> listItem_NS_Ban = new ArrayList<>();
    private List<BacSi> listTenBacSi = new ArrayList<>();
    private ItemHoSoBenh_Adapter adapterNS_Ban;
    private RecyclerView recyclerViewNS_Ban;

    private DatabaseReference databaseReference;
    private String id;

    private String tenngtao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1_sanpham_canban, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference();



        recyclerViewNS_Ban = (RecyclerView) rootView.findViewById(R.id.recyclerView_khoinghiep);
        adapterNS_Ban = new ItemHoSoBenh_Adapter(listItem_NS_Ban,this.getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerViewNS_Ban.setLayoutManager(layoutManager);
        recyclerViewNS_Ban.setAdapter(adapterNS_Ban);


        even();

        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void even(){

    }





}
