package android.familydoctor.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.familydoctor.Adapter.AdapterDanhSachThuoc;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DanhSachThuocActivity extends AppCompatActivity {

    ExpandableListView elvDanhSachThuoc;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;

    AdapterDanhSachThuoc adapterDanhSachThuoc;

    Toolbar toolbarDanhSachThuoc;
    SearchView searchView;

    Button btnHoanThanhThemThuoc;

    int count = 0;

    String[] danhSachThuoc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_thuoc);

        toolbarDanhSachThuoc = (Toolbar) findViewById(R.id.toolbarDanhSachThuoc);
        setSupportActionBar(toolbarDanhSachThuoc);

        elvDanhSachThuoc = (ExpandableListView) findViewById(R.id.elvDanhSachThuoc);
        listTenThuoc = new ArrayList<>();
        listThongTinThuoc = new HashMap<>();

        danhSachThuoc =  getResources().getStringArray(R.array.danhSachThuoc);

        for(int i = 0; i < danhSachThuoc.length; i++){
            listTenThuoc.add(danhSachThuoc[i]);
            Thuoc thuoc = new Thuoc(listTenThuoc.get(i), "", "", "", "");
            listThongTinThuoc.put(listTenThuoc.get(i), thuoc);
        }

        adapterDanhSachThuoc = new AdapterDanhSachThuoc(DanhSachThuocActivity.this, listTenThuoc, listThongTinThuoc);
        elvDanhSachThuoc.setAdapter(adapterDanhSachThuoc);

        elvDanhSachThuoc.setDescendantFocusability(ViewGroup.FOCUS_BEFORE_DESCENDANTS);

        btnHoanThanhThemThuoc = (Button) findViewById(R.id.btnHoanThanhThemThuoc);

        btnHoanThanhThemThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Thuoc> listThuocSeThem = new ArrayList<>();
                for(int i = 0; i < listTenThuoc.size(); i++){
                    Thuoc thuoc = listThongTinThuoc.get(listTenThuoc.get(i));
                    if (!thuoc.getSoLuong().equals("")){
                        listThuocSeThem.add(thuoc);
                    }
                }
                Intent intent = new Intent();
                intent.putExtra("listThuocSeThem", listThuocSeThem);
                setResult(ThemHoSoBenhAnActivity.RESULT_CODE, intent);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                List<String> listSearch = new ArrayList<>();
                for (int i = 0; i < listTenThuoc.size(); i++) {
                    if (listTenThuoc.get(i).toLowerCase().contains(newText.toLowerCase())) {
                        listSearch.add(listTenThuoc.get(i));
                    }
                }
                adapterDanhSachThuoc = new AdapterDanhSachThuoc(DanhSachThuocActivity.this, listSearch, listThongTinThuoc);
                elvDanhSachThuoc.setAdapter(adapterDanhSachThuoc);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {

        for(int i = 0; i < listTenThuoc.size(); i++){
            Thuoc thuoc = listThongTinThuoc.get(listTenThuoc.get(i));
            if (!thuoc.getSoLuong().equals("")){
                count++;
            }
        }

        if(count > 0){
            Dialog dialog = new Dialog(this);
            dialog.setTitle("Nhắc nhở:");
            dialog.setContentView(R.layout.dialog_nhac_nho);
            dialog.show();
        }else{
            finish();
        }
    }
}
