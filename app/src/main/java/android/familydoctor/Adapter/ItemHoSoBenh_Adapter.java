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

public class ItemHoSoBenh_Adapter extends RecyclerView.Adapter<ItemHoSoBenh_Adapter.RecyclerViewHolder>  {


    private List<Item> danhSachShow = new ArrayList<>();
    private Context context;


    private static ClickListenerKN listenerKN;


    public ItemHoSoBenh_Adapter(List<Item> danhSachShow, Context context) {
        this.danhSachShow = danhSachShow;
        this.context=context;

    }

    //tạo một ViewHolder mới khi được gọi.
    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    //gắn kết dữ liệu và view trong ViewHolder.
    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, int position) {
        final Item item = danhSachShow.get(position);

        holder.ten.setText(item.getTen());
        holder.nguoitao.setText(item.getNguoitao());
        holder.loai.setText(item.getLoai());
        holder.gia.setText(item.getGia());
        //View hình ảnh
        Picasso.with(context).load(item.getSrc()).into(holder.imageView);

    }

    //trả về số lượng item của danh sách.
    @Override
    public int getItemCount() {
        return danhSachShow == null ? 0 : danhSachShow.size();
    }

    //Gắn kết Với File XML
    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView ten,nguoitao, loai,gia;
        private RecyclerViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imgPicture);
            ten = (TextView) itemView.findViewById(R.id.tv_ten);
            nguoitao = (TextView) itemView.findViewById(R.id.tv_nguoitao);
            loai = (TextView) itemView.findViewById(R.id.tv_loai);
            gia = (TextView) itemView.findViewById(R.id.tv_gia);

            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View view) {

                listenerKN.onItemClick(view, getAdapterPosition());

        }
    }


    //Suwk kiện CLick Item
    public void setOnItemClickListenerKhoiNghiep(ClickListenerKN clickListener) {
        ItemHoSoBenh_Adapter.listenerKN = clickListener;
    }




    public interface ClickListenerKN {
        void onItemClick(View v, int position);
    }



}
