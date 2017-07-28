package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.app.Dialog;
import android.content.Intent;
import android.familydoctor.Activity.ThemHoSoBenhAnActivity;
import android.familydoctor.Adapter.AdapterDanhSachThuoc;
import android.familydoctor.Adapter.ItemHoSoBenh_Adapter;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.Class.Thuoc;
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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
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
    ExpandableListView elvDanhSachThuoc;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;
    AdapterDanhSachThuoc adapterDanhSachThuoc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ho_so_benh_an, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewHoSoBenhAn);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        databaseHSBA = FirebaseDatabase.getInstance().getReference();

        adapter = new ItemHoSoBenh_Adapter(list);
        recyclerView.setAdapter(adapter);

        loadDuLieu();
        adapter.setOnItemClickListener(new ItemHoSoBenh_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //set up dialog
                Dialog dialog = new Dialog(getContext());
                dialog.setTitle("THÔNG TIN HỒ SƠ BỆNH ÁN");
                dialog.setContentView(R.layout.dialog_show_ho_so_benh_an);

                //ánh xạ - khai báo
                TextView tv = (TextView) dialog.findViewById(R.id.txtThongTinHSBA);
                HoSoBenh hsb = list.get(position);
                List<Thuoc> listTHuocFB =hsb.getThuocDung();

                elvDanhSachThuoc = (ExpandableListView) dialog.findViewById(R.id.elvDanhSachThuoc);
                listTenThuoc = new ArrayList<>();//list header
                listThongTinThuoc = new HashMap<>();//child list

                //for gán list header
                for (int i = 0; i <listTHuocFB.size() ; i++) {
                    listTenThuoc.add(listTHuocFB.get(i).getTenThuoc());
                    listThongTinThuoc.put(listTHuocFB.get(i).getTenThuoc(),listTHuocFB.get(i));
                }
                //event
                adapterDanhSachThuoc = new AdapterDanhSachThuoc(getContext(), listTenThuoc, listThongTinThuoc);
                elvDanhSachThuoc.setAdapter(adapterDanhSachThuoc);

                elvDanhSachThuoc.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                String thongtin_HSBA =
                        "Họ và tên: "+hsb.getIdBacSi()
                        +"\n"
                        +"Bệnh cần điều trị : "+hsb.getTenBenh()
                        +"\n"
                        +"Ngày bắt đầu khám : "+hsb.getNgayKham()
                        +"\n"
                        +"Ngày tái khám : "+hsb.getNgayTaiKham()
                        +"\n"
                        ;

                tv.setText(thongtin_HSBA);
                dialog.show();
            }
        });

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

    public void loadDuLieu() {
        databaseHSBA.child("HoSoBenhAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HoSoBenh hsb = dataSnapshot.getValue(HoSoBenh.class);
                adapter.addItem(list.size(), hsb);
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
    }
}
