package android.familydoctor.Adapter;

import android.content.Context;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Au Nguyen on 7/25/2017.
 */

public class AdapterDanhSachThuoc extends BaseExpandableListAdapter {
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    Context context;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;

    public AdapterDanhSachThuoc(Context context, List<String> listTenThuoc, HashMap<String, Thuoc> listThongTinThuoc) {
        this.context = context;
        this.listTenThuoc = listTenThuoc;
        this.listThongTinThuoc = listThongTinThuoc;
    }



    @Override
    public int getGroupCount() {
        return listTenThuoc.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return listTenThuoc.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return listThongTinThuoc.get(listTenThuoc.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String header = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(R.layout.item_header_danh_sach_thuoc, parent, false);
        }

        TextView txtTenThuoc = (TextView) convertView.findViewById(R.id.txtTenThuocTrongDanhSachThuoc);
        txtTenThuoc.setText(header);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater;
            layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(R.layout.item_child_danh_sach_thuoc, parent, false);
        }

        final EditText edtSoLuongThuoc;
        CheckBox chkSang, chkTrua, chkChieu;
        final EditText edtSoLuongSang, edtDonViSang;
        final EditText edtSoLuongTrua, edtDonViTrua;
        final EditText edtSoLuongChieu, edtDonViChieu;
        Button btnXacNhan = (Button) convertView.findViewById(R.id.btnXacNhan);

        final Thuoc thuoc = (Thuoc) getChild(groupPosition, childPosition);

        edtSoLuongThuoc = (EditText) convertView.findViewById(R.id.edtSoLuongThuoc);
        edtSoLuongSang = (EditText) convertView.findViewById(R.id.edtSoLuongSang);
        edtDonViSang = (EditText) convertView.findViewById(R.id.edtDonViSang);
        edtSoLuongTrua = (EditText) convertView.findViewById(R.id.edtSoLuongTrua);
        edtDonViTrua = (EditText) convertView.findViewById(R.id.edtDonViTrua);
        edtSoLuongChieu = (EditText) convertView.findViewById(R.id.edtSoLuongChieu);
        edtDonViChieu = (EditText) convertView.findViewById(R.id.edtDonViChieu);

        chkSang = (CheckBox) convertView.findViewById(R.id.chkSang);
        chkTrua = (CheckBox) convertView.findViewById(R.id.chkTrua);
        chkChieu = (CheckBox) convertView.findViewById(R.id.chkChieu);

        if (!thuoc.getSoLuong().equals("")) {
            edtSoLuongThuoc.setText(thuoc.getSoLuong());
        } else {
            edtSoLuongThuoc.setText("");
        }

        if (!thuoc.getLieuDungSang().equals("")) {
            chkSang.setChecked(true);
            edtSoLuongSang.setVisibility(View.VISIBLE);
            edtDonViSang.setVisibility(View.VISIBLE);
            edtSoLuongSang.setText(thuoc.getLieuDungSang());
        } else {
            chkSang.setChecked(false);
            edtSoLuongSang.setText("");
        }

        if (!thuoc.getLieuDungTrua().equals("")) {
            chkTrua.setChecked(true);
            edtSoLuongTrua.setVisibility(View.VISIBLE);
            edtDonViTrua.setVisibility(View.VISIBLE);
            edtSoLuongTrua.setText(thuoc.getLieuDungTrua());
        } else {
            chkTrua.setChecked(false);
            edtSoLuongTrua.setText("");
        }

        if (!thuoc.getLieuDungChieu().equals("")) {
            chkChieu.setChecked(true);
            edtSoLuongChieu.setVisibility(View.VISIBLE);
            edtDonViChieu.setVisibility(View.VISIBLE);
            edtSoLuongChieu.setText(thuoc.getLieuDungChieu());
        } else {
            chkChieu.setChecked(false);
            edtSoLuongChieu.setText("");
        }

        chkSang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    edtSoLuongSang.setVisibility(View.VISIBLE);
                    edtDonViSang.setVisibility(View.VISIBLE);
                } else {
                    edtSoLuongSang.setText("");
                    edtSoLuongSang.setVisibility(View.INVISIBLE);
                    edtDonViSang.setVisibility(View.INVISIBLE);
                }
            }
        });


        chkTrua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    edtSoLuongTrua.setVisibility(View.VISIBLE);
                    edtDonViTrua.setVisibility(View.VISIBLE);
                } else {
                    edtSoLuongTrua.setText("");
                    edtSoLuongTrua.setVisibility(View.INVISIBLE);
                    edtDonViTrua.setVisibility(View.INVISIBLE);
                }
            }
        });

        chkChieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    edtSoLuongChieu.setVisibility(View.VISIBLE);
                    edtDonViChieu.setVisibility(View.VISIBLE);
                } else {
                    edtSoLuongChieu.setText("");
                    edtSoLuongChieu.setVisibility(View.INVISIBLE);
                    edtDonViChieu.setVisibility(View.INVISIBLE);
                }
            }
        });

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edtSoLuongThuoc.getText().toString().equals("")) {
                    thuoc.setSoLuong(edtSoLuongThuoc.getText().toString());
                }

                if (!edtSoLuongSang.getText().toString().equals("")) {
                    thuoc.setLieuDungSang(edtSoLuongSang.getText().toString());
                }

                if (!edtSoLuongTrua.getText().toString().equals("")) {
                    thuoc.setLieuDungTrua(edtSoLuongTrua.getText().toString());
                }

                if (!edtSoLuongChieu.getText().toString().equals("")) {
                    thuoc.setLieuDungChieu(edtSoLuongChieu.getText().toString());
                }
                Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
