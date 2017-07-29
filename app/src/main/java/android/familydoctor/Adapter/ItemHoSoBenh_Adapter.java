package android.familydoctor.Adapter;


import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemHoSoBenh_Adapter extends RecyclerView.Adapter<ItemHoSoBenh_Adapter.RecyclerViewHolder>  {

    private List<HoSoBenh> listData = new ArrayList<HoSoBenh>();

    public ItemHoSoBenh_Adapter(List<HoSoBenh> listData) {
        this.listData = listData;
    }

    public void updateList(List<HoSoBenh> data) {
        listData = data;
        notifyDataSetChanged();
    }

    public void addItem(int position, HoSoBenh data) {
        listData.add(position, data);
        notifyItemInserted(position);
    }

    public void removeItem(int position) {
        listData.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_hsba, parent, false);
        return new RecyclerViewHolder(row);
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {

        holder.txtName.setText("Họ tên : "+listData.get(position).getIdBacSi());
        holder.txtTenBenh.setText("Bệnh : "+listData.get(position).getTenBenh());
        holder.txtNgayKham.setText("Ngày khám : "+listData.get(position).getNgayKham());
        holder.txtNgayTaiKham.setText("Ngày tái khám : "+listData.get(position).getNgayTaiKham());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    private static OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView txtName,txtTenBenh,txtNgayKham,txtNgayTaiKham;
        public RecyclerViewHolder(final View itemView) {
            super(itemView);
            txtName = (TextView) itemView.findViewById(R.id.txtName);
            txtNgayKham = (TextView) itemView.findViewById(R.id.txtNgayKham);
            txtNgayTaiKham= (TextView) itemView.findViewById(R.id.txtNgayTaiKham);
            txtTenBenh= (TextView) itemView.findViewById(R.id.txtTenBenh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null)
                        listener.onItemClick(itemView, getAdapterPosition());
                }
            });
        }
    }
}
