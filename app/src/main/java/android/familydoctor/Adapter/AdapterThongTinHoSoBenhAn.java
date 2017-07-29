package android.familydoctor.Adapter;

import android.content.Context;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Au Nguyen on 7/25/2017.
 */

public class AdapterThongTinHoSoBenhAn extends BaseExpandableListAdapter {

    Context context;
    List<String> listTenThuoc;
    HashMap<String, Thuoc> listThongTinThuoc;


    public AdapterThongTinHoSoBenhAn(Context context, List<String> listTenThuoc, HashMap<String, Thuoc> listThongTinThuoc) {
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
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_child_show_danh_sach_thuoc, parent, false);
        }

        final EditText edtSoLuongThuoc;
        LinearLayout layoutSang, layoutTrua, layoutChieu;
        final EditText edtSoLuongSang, edtDonViSang;
        final EditText edtSoLuongTrua, edtDonViTrua;
        final EditText edtSoLuongChieu, edtDonViChieu;

        final Thuoc thuoc = (Thuoc) getChild(groupPosition, childPosition);

        layoutSang = (LinearLayout) convertView.findViewById(R.id.layoutSang);
        layoutTrua = (LinearLayout) convertView.findViewById(R.id.layoutTrua);
        layoutChieu = (LinearLayout) convertView.findViewById(R.id.layoutChieu);

        edtSoLuongThuoc = (EditText) convertView.findViewById(R.id.edtSoLuongThuocShow);
        edtSoLuongSang = (EditText) convertView.findViewById(R.id.edtSoLuongSangShow);
        edtDonViSang = (EditText) convertView.findViewById(R.id.edtDonViSangShow);
        edtSoLuongTrua = (EditText) convertView.findViewById(R.id.edtSoLuongTruaShow);
        edtDonViTrua = (EditText) convertView.findViewById(R.id.edtDonViTruaShow);
        edtSoLuongChieu = (EditText) convertView.findViewById(R.id.edtSoLuongChieuShow);
        edtDonViChieu = (EditText) convertView.findViewById(R.id.edtDonViChieuShow);


        edtSoLuongThuoc.setText(thuoc.getSoLuong());

        if (!thuoc.getLieuDungSang().equals("")) {
            layoutSang.setVisibility(View.VISIBLE);
            edtSoLuongSang.setText(thuoc.getLieuDungSang());
        }

        if (!thuoc.getLieuDungTrua().equals("")) {
            layoutTrua.setVisibility(View.VISIBLE);
            edtSoLuongTrua.setText(thuoc.getLieuDungTrua());
        }

        if (!thuoc.getLieuDungChieu().equals("")) {
            layoutChieu.setVisibility(View.VISIBLE);
            edtSoLuongChieu.setText(thuoc.getLieuDungChieu());
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
