package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.app.Dialog;
import android.content.Intent;
import android.familydoctor.Activity.MainActivity;
import android.familydoctor.Activity.ThemHoSoBenhAnActivity;
import android.familydoctor.Adapter.AdapterThongTinHoSoBenhAn;
import android.familydoctor.Adapter.ItemHoSoBenh_Adapter;
import android.familydoctor.Class.BenhNhan;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
    DatabaseReference databaseBS = FirebaseDatabase.getInstance().getReference();
    DatabaseReference databaseBN = FirebaseDatabase.getInstance().getReference();
    FloatingActionButton fabThemHoSoBenhAn;

    RecyclerView recyclerView;
    ItemHoSoBenh_Adapter adapter;
    LinearLayoutManager layoutManager;
    List<HoSoBenh> list = new ArrayList<HoSoBenh>();
    List<BenhNhan> listBn =new ArrayList<BenhNhan>();
    ExpandableListView elvDanhSachThuocShow;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;

    AdapterThongTinHoSoBenhAn adapterThongTinHoSoBenhAn;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ho_so_benh_an, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewHoSoBenhAn);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        databaseHSBA = FirebaseDatabase.getInstance().getReference();

        adapter = new ItemHoSoBenh_Adapter(list);
        recyclerView.setAdapter(adapter);

        loadDuLieu();
        // nhận dử liệu từ Main
        //Bundle main = getArguments();
        //String sdt = main.getString("sdt","null");

        Log.i("log", MainActivity.sdt);

        adapter.setOnItemClickListener(new ItemHoSoBenh_Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int position) {
                //set up dialog
                Dialog dialog = new Dialog(getContext());
                dialog.setTitle("THÔNG TIN HỒ SƠ");
                dialog.setContentView(R.layout.dialog_show_ho_so_benh_an);

                //ánh xạ - khai báo
                TextView txtNameShow = (TextView) dialog.findViewById(R.id.txtNameShow);
                TextView txtTenBenhShow = (TextView) dialog.findViewById(R.id.txtTenBenhShow);
                TextView txtNgayKhamShow = (TextView) dialog.findViewById(R.id.txtNgayKhamShow);
                TextView txtngayTaiKhamShow = (TextView) dialog.findViewById(R.id.txtNgayTaiKhamShow);

                HoSoBenh hsb = list.get(position);
                List<Thuoc> listThuocTrongHSBA = hsb.getThuocDung();

                elvDanhSachThuocShow = (ExpandableListView) dialog.findViewById(R.id.elvDanhSachThuocShow);
                listTenThuoc = new ArrayList<>();//list header
                listThongTinThuoc = new HashMap<>();//child list

                //for gán list header
                for (int i = 0; i <listThuocTrongHSBA.size() ; i++) {
                    listTenThuoc.add(listThuocTrongHSBA.get(i).getTenThuoc());
                    listThongTinThuoc.put(listThuocTrongHSBA.get(i).getTenThuoc(),listThuocTrongHSBA.get(i));
                }
                //event

                adapterThongTinHoSoBenhAn = new AdapterThongTinHoSoBenhAn(getContext(), listTenThuoc, listThongTinThuoc);
                elvDanhSachThuocShow.setAdapter(adapterThongTinHoSoBenhAn);
                elvDanhSachThuocShow.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);
                for (int i = 0; i <listBn.size() ; i++) {
                    if(TextUtils.equals(listBn.get(i).getSoDienThoaiBenhNhan(),MainActivity.sdt))
                    {
                        txtNameShow.setText("Họ và tên: " + listBn.get(i).getHoTenBenhNhan());
                        break;
                    }
                }
                txtTenBenhShow.setText("Bệnh cần điều trị : "+hsb.getTenBenh());
                txtNgayKhamShow.setText("Ngày khám : "+hsb.getNgayKham());
                txtngayTaiKhamShow.setText("Ngày tái khám : "+hsb.getNgayTaiKham());
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
        databaseBS.child("BenhNhan").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                BenhNhan bn = dataSnapshot.getValue(BenhNhan.class);
                listBn.add(bn);
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
        databaseHSBA.child("HoSoBenhAn").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HoSoBenh hsb = dataSnapshot.getValue(HoSoBenh.class);
                adapter.addItem(list.size(), hsb);
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
