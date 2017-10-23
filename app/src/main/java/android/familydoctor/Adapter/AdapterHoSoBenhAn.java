package android.familydoctor.Adapter;


import android.content.Context;
import android.familydoctor.Class.BacSi;
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

public class AdapterHoSoBenhAn extends RecyclerView.Adapter<AdapterHoSoBenhAn.RecyclerViewHolder>  {

    //adapter này dùng để hiển thị các hồ sơ bệnh án trên Firebase về Fragment
    // nếu là bác sĩ sẽ hiện thị: tên bệnh nhân đến khám và thông tin về hồ sơ bệnh
    // nếu là bệnh nhân sẽ hiện thị: tên tên bác sĩ đã khám và thông tin về hồ sơ bệnh
    // thông tin bệnh gồm: tên bệnh, ngày khám và ngày tái khám (thuốc sẽ ko hiển thị ở đây)

    private List<HoSoBenh> listData = new ArrayList<>();
    DatabaseReference databaseHSBA;
    int dinhDanh = 0;
    private Context context;

    public AdapterHoSoBenhAn(List<HoSoBenh> listData, int dinhDanh, Context context) {
        this.listData = listData;
        this.dinhDanh = dinhDanh;
        this.context = context;
        databaseHSBA = FirebaseDatabase.getInstance().getReference();
    }

    // thêm 1 hồ sơ bệnh án vào list sẽ hiển thị
    public void addItem(int position, HoSoBenh data) {
        listData.add(position, data);
        notifyItemInserted(position);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.item_ho_so_benh_an, parent, false);
        return new RecyclerViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {

        HoSoBenh hoSoBenh = listData.get(position);

        if(hoSoBenh != null){
            //lấy thông tin hồ sơ bệnh
            final String idBacSi = listData.get(position).getIdBacSi();
            final String idBenhNhan = listData.get(position).getIdBenhNhan();
            String tenBenh = listData.get(position).getTenBenh();
            String ngayKham = listData.get(position).getNgayKham();
            String ngayTaiKham = listData.get(position).getNgayTaiKham();

            //nếu người dùng hiện hành là bác sĩ thì tên hiển thị trong HSBA là tên của bẹnh nhân
            if(dinhDanh == 1){
                databaseHSBA.child("User_BenhNhan").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BenhNhan benhNhan = data.getValue(BenhNhan.class);
                            assert benhNhan != null;
                            if (benhNhan.getSoDienThoaiBenhNhan().equals(idBenhNhan)){
                                holder.txtName.setText(context.getResources().getString(R.string.Full_name)+" : " + benhNhan.getHoTenBenhNhan());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            //nếu người dùng hiện hành là bệnh nhân thì tên hiển thị trong HSBA là tên của bác sĩ
            else if(dinhDanh == 2){
                databaseHSBA.child("User_BacSi").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            BacSi bacSi = data.getValue(BacSi.class);
                            assert bacSi != null;
                            if (bacSi.getSoDienThoaiBacSi().equals(idBacSi)){
                                holder.txtName.setText(context.getResources().getString(R.string.Full_name)+": " + bacSi.getHoTenBacSi());
                                break;
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }


            holder.txtTenBenh.setText( context.getResources().getString(R.string.Disease_name)+": "+ tenBenh);
            holder.txtNgayKham.setText(context.getResources().getString(R.string.Drug_information_is_inaccurate)+": "+ ngayKham);
            holder.txtNgayTaiKham.setText(context.getResources().getString(R.string.On_reexamination)+" " + ngayTaiKham);
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
