package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

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


public class HoSoBenhAn extends Fragment {

    private List<Item> listItem_CanMua = new ArrayList<>();
    private List<BacSi> listTenBacSi = new ArrayList<>();
    private ItemHoSoBenh_Adapter adapter_CanMua;
    private RecyclerView recyclerView_CanMua;

    private DatabaseReference databaseReference;
    private String id;

    final String NONG_SAN_MUA = "NONG_SAN_MUA";

    private String tenngtao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab2_sanpham_canmua, container, false);

        databaseReference= FirebaseDatabase.getInstance().getReference();
        databaseReference.child("NONG_SAN_MUA");


        recyclerView_CanMua = (RecyclerView) rootView.findViewById(R.id.recyclerView_CanMua);
        adapter_CanMua = new ItemHoSoBenh_Adapter(listItem_CanMua,this.getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView_CanMua.setLayoutManager(layoutManager);
        recyclerView_CanMua.setAdapter(adapter_CanMua);

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
