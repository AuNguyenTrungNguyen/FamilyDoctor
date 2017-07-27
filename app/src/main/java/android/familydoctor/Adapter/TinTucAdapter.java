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

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

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
        final ImageView img = (ImageView) rowRSS.findViewById(R.id.imgPhoto);
        TextView txtTitle,txtNgay;
        txtTitle=(TextView)rowRSS.findViewById(R.id.txtTenTinTuc);
        TinTuc tt = this.objects.get(position);
        txtTitle.setText(tt.getTitle());
        Picasso.with(context).load(tt.getImage()).into(img, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                img.setImageResource(R.drawable.no_picture_available);
            }
        });
        return rowRSS;
    }
}
