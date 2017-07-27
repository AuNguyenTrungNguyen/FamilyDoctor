package android.familydoctor.Adapter;

import android.app.Activity;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterThuoc extends ArrayAdapter<Thuoc> {
    private Activity activity;
    private int resource;
    private List<Thuoc> objects;

    public AdapterThuoc(Activity activity, int resource, List<Thuoc> objects) {
        super(activity, resource, objects);
        this.activity = activity;
        this.resource = resource;
        this.objects = objects;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.activity.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtTenThuoc = (TextView) item.findViewById(R.id.txtTenThuoc);
        TextView txtSoLuongThuoc = (TextView) item.findViewById(R.id.txtSoLuongThuoc);
        TextView txtLieuDungSang = (TextView) item.findViewById(R.id.txtLieuDungSang);
        TextView txtLieuDungTrua = (TextView) item.findViewById(R.id.txtLieuDungTrua);
        TextView txtLieuDungChieu = (TextView) item.findViewById(R.id.txtLieuDungChieu);


        Thuoc thuoc = this.objects.get(position);

        txtTenThuoc.setText(thuoc.getTenThuoc());
        txtSoLuongThuoc.setText(thuoc.getSoLuong());

        if(!thuoc.getLieuDungSang().equals("")){
            txtLieuDungSang.setVisibility(View.VISIBLE);
            txtLieuDungSang.setText(thuoc.getLieuDungSang());
        }

        if(!thuoc.getLieuDungTrua().equals("")){
            txtLieuDungTrua.setVisibility(View.VISIBLE);
            txtLieuDungTrua.setText(thuoc.getLieuDungTrua());
        }

        if(!thuoc.getLieuDungChieu().equals("")){
            txtLieuDungChieu.setVisibility(View.VISIBLE);
            txtLieuDungChieu.setText(thuoc.getLieuDungChieu());
        }

        return item;
    }
}