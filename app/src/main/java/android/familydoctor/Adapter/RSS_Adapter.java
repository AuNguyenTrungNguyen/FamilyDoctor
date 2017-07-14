package android.familydoctor.Adapter;


import android.content.Context;
import android.familydoctor.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RSS_Adapter extends RecyclerView.Adapter<RSS_Adapter.RecyclerViewHolder>  {


    private List<RSS_Item> danhSachShow = new ArrayList<>();
    private Context context;


//    private static ClickListenerRSS listenerRSS;


    public RSS_Adapter(List<RSS_Item> danhSachShow, Context context) {
        this.danhSachShow = danhSachShow;
        this.context=context;

    }

    //tạo một ViewHolder mới khi được gọi.
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rss, parent, false);
        return new RecyclerViewHolder(view);
    }

    //gắn kết dữ liệu và view trong ViewHolder.
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final RSS_Item item = danhSachShow.get(position);

        holder.tieude.setText(item.getTieude());
        holder.noidung.setText(item.getNoidung());
        //View hình ảnh
        Picasso.with(context).load(item.getUrl_img()).into(holder.imageView);

    }

    //trả về số lượng item của danh sách.
    @Override
    public int getItemCount() {
        return danhSachShow == null ? 0 : danhSachShow.size();
    }

    //Gắn kết Với File XML
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView tieude,noidung;
        private RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img_rss);
            tieude = (TextView) itemView.findViewById(R.id.tv_tieude_rss);
            noidung = (TextView) itemView.findViewById(R.id.tv_noidung_rss);

//            itemView.setOnClickListener(this);
        }
//        @Override
//        public void onClick(View view) {
//
//            listenerRSS.onItemClick(view, getAdapterPosition());
//
//        }
    }


    //Suwk kiện CLick Item
//    public void setOnItemClickListenerRSS(ClickListenerRSS clickListener) {
//        RSS_Adapter.listenerRSS = clickListener;
//    }
//
//
//
//
//    public interface ClickListenerRSS {
//        void onItemClick(View v, int position);
//    }



}
