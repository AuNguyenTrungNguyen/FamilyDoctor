package android.familydoctor.Adapter;

import android.content.Context;
import android.familydoctor.Class.TinTuc;
import android.familydoctor.R;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by quocb14005xx on 7/21/2017.
 */

public class TinTucAdapter  extends ArrayAdapter<TinTuc>{
    Context context;
    int res;
    List<TinTuc> objects;
    public TinTucAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TinTuc> objects) {
        super(context, resource, objects);
        this.context=context;
        res=resource;
        this.objects=objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowRSS =inflater.inflate(res,null);
        ImageView img = rowRSS.findViewById(R.id.imgPhoto);
        TextView txtTitle,txtNgay;
        txtTitle=(TextView)rowRSS.findViewById(R.id.txtTenTinTuc);
        txtNgay=(TextView) rowRSS.findViewById(R.id.txtThoiGianUpdate);
        TinTuc tt = this.objects.get(position);
        txtNgay.setText(tt.getPublish());
        txtTitle.setText(tt.getTitle());
        img.setImageResource(R.drawable.nhadautu);
        return rowRSS;
    }
}
