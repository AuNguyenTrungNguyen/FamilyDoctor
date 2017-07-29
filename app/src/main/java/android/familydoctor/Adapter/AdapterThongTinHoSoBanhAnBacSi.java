package android.familydoctor.Adapter;


import android.familydoctor.Class.BenhNhan;
import android.familydoctor.Class.HoSoBenh;
import android.familydoctor.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdapterThongTinHoSoBanhAnBacSi extends RecyclerView.Adapter<AdapterThongTinHoSoBanhAnBacSi.RecyclerViewHolder>  {

    private List<HoSoBenh> listData = new ArrayList<>();
    DatabaseReference databaseHSBA;

    public AdapterThongTinHoSoBanhAnBacSi(List<HoSoBenh> listData) {
        this.listData = listData;
        databaseHSBA = FirebaseDatabase.getInstance().getReference();
    }

    public void addItem(int position, HoSoBenh data) {
        listData.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_ho_so_benh_an_bac_si, parent, false);
        return new RecyclerViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        HoSoBenh hoSoBenh = listData.get(position);

        if(hoSoBenh != null){
            final String idBenhNhan = listData.get(position).getIdBenhNhan();
            String tenBenh = listData.get(position).getTenBenh();
            String ngayKham = listData.get(position).getNgayKham();
            String ngayTaiKham = listData.get(position).getNgayTaiKham();

            databaseHSBA.child("User_BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot data : dataSnapshot.getChildren()) {
                        BenhNhan benhNhan = data.getValue(BenhNhan.class);
                        assert benhNhan != null;
                        if (benhNhan.getSoDienThoaiBenhNhan().equals(idBenhNhan)){
                            holder.txtName.setText("Họ tên : " + benhNhan.getHoTenBenhNhan());
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            holder.txtTenBenh.setText("Bệnh : "+ tenBenh);
            holder.txtNgayKham.setText("Ngày khám : "+ ngayKham);
            holder.txtNgayTaiKham.setText("Ngày tái khám : " + ngayTaiKham);
        }
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
