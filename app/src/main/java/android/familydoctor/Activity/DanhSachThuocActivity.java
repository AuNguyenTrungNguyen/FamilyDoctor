package android.familydoctor.Activity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_thuoc);

        toolbarDanhSachThuoc = (Toolbar) findViewById(R.id.toolbarDanhSachThuoc);
        setSupportActionBar(toolbarDanhSachThuoc);

        elvDanhSachThuoc = (ExpandableListView) findViewById(R.id.elvDanhSachThuoc);
        listTenThuoc = new ArrayList<>();
        listThongTinThuoc = new HashMap<>();

        for(int i = 0; i < 9; i++){
            listTenThuoc.add("Thuốc " + i);
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

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search, menu);

        searchView = (SearchView) menu.findItem(R.id.search).getActionView();

        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                if(!newText.equals("")){
                    isSearch = true;
                }else{
                    isSearch = false;
                }
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).toLowerCase().contains(newText.toLowerCase())){
                        listSearch.add(list.get(i));
                    }
                }

                adapter = new ArrayAdapter<>(DanhSachThuocActivity.this, android.R.layout.simple_list_item_1, listSearch);
                listView.setAdapter(adapter);
                return true;
            }
        });*/
        return super.onCreateOptionsMenu(menu);
    }
}
