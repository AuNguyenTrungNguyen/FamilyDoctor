package android.familydoctor.Activity;

import android.content.Intent;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class DanhSachThuocActivity extends AppCompatActivity {

    ListView listView;
    ArrayAdapter adapter;
    ArrayList<String> list, listSearch;

    Toolbar toolbarDanhSachThuoc;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_thuoc);

        toolbarDanhSachThuoc = (Toolbar) findViewById(R.id.toolbarDanhSachThuoc);
        setSupportActionBar(toolbarDanhSachThuoc);

        listView = (ListView) findViewById(R.id.lvDanhSachThuoc);
        list = new ArrayList<>();

        list.add("Đau đầu");
        list.add("Đau mắt");
        list.add("Đau răng");
        list.add("Đau họng");
        list.add("Nghẹt mũi");
        list.add("Cảm");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent= new Intent();

                String tenThuoc= list.get(position);
                intent.putExtra("tenThuoc", tenThuoc);
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
                listSearch = new ArrayList<>();
                for(int i = 0; i < list.size(); i++){
                    if(list.get(i).toLowerCase().contains(newText.toLowerCase())){
                        listSearch.add(list.get(i));
                    }
                }

                adapter = new ArrayAdapter<>(DanhSachThuocActivity.this, android.R.layout.simple_list_item_1, listSearch);
                listView.setAdapter(adapter);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
