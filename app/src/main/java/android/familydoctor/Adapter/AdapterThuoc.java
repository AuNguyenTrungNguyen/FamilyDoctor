package android.familydoctor.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.familydoctor.Class.Thuoc;
import android.familydoctor.R;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = this.activity.getLayoutInflater();
        View item = inflater.inflate(this.resource, null);

        TextView txtTenThuoc = (TextView) item.findViewById(R.id.txtTenThuoc);
        TextView txtSoLuongThuoc = (TextView) item.findViewById(R.id.txtSoLuongThuoc);
        TextView txtLieuDungSang = (TextView) item.findViewById(R.id.txtLieuDungSang);
        TextView txtLieuDungTrua = (TextView) item.findViewById(R.id.txtLieuDungTrua);
        TextView txtLieuDungChieu = (TextView) item.findViewById(R.id.txtLieuDungChieu);
        Button btnXoaThuoc = (Button) item.findViewById(R.id.btnXoaThuoc);
        Button btnCapNhapThuoc = (Button) item.findViewById(R.id.btnCapNhatThuoc);

        Thuoc thuoc = this.objects.get(position);

        txtTenThuoc.setText(thuoc.getTenThuoc().toUpperCase());
        txtSoLuongThuoc.setText("Số lượng: " + thuoc.getSoLuong() + " viên");

        if (!thuoc.getLieuDungSang().equals("")) {

            txtLieuDungSang.setText(thuoc.getLieuDungSang() + " viên");
        }

        if (!thuoc.getLieuDungTrua().equals("")) {
            txtLieuDungTrua.setText(thuoc.getLieuDungTrua() + " viên");
        }

        if (!thuoc.getLieuDungChieu().equals("")) {
            txtLieuDungChieu.setText(thuoc.getLieuDungChieu() + " viên");
        }

        btnXoaThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                objects.remove(position);
                notifyDataSetChanged();
            }
        });

        btnCapNhapThuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogCapNhatThuoc = new Dialog(getContext());
                dialogCapNhatThuoc.setTitle("Cập nhật thuốc: ");
                dialogCapNhatThuoc.setContentView(R.layout.dialog_cap_nhat_thuoc);

                final EditText edtSoLuongThuoc;
                final CheckBox chkSang, chkTrua, chkChieu;
                final EditText edtSoLuongSang, edtDonViSang;
                final EditText edtSoLuongTrua, edtDonViTrua;
                final EditText edtSoLuongChieu, edtDonViChieu;
                Button btnXacNhanCapNhatThuoc = (Button) dialogCapNhatThuoc.findViewById(R.id.btnXacNhanCapNhatThuoc);

                final Thuoc thuoc = (Thuoc) objects.get(position);

                edtSoLuongThuoc = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtSoLuongThuoc);
                edtSoLuongSang = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtSoLuongSang);
                edtDonViSang = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtDonViSang);
                edtSoLuongTrua = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtSoLuongTrua);
                edtDonViTrua = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtDonViTrua);
                edtSoLuongChieu = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtSoLuongChieu);
                edtDonViChieu = (EditText) dialogCapNhatThuoc.findViewById(R.id.edtDonViChieu);

                chkSang = (CheckBox) dialogCapNhatThuoc.findViewById(R.id.chkSang);
                chkTrua = (CheckBox) dialogCapNhatThuoc.findViewById(R.id.chkTrua);
                chkChieu = (CheckBox) dialogCapNhatThuoc.findViewById(R.id.chkChieu);

                edtSoLuongThuoc.setText(thuoc.getSoLuong());

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

                btnXacNhanCapNhatThuoc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int soLuongThuoc = -1;
                        int soLuongSang = 0;
                        int soLuongTrua = 0;
                        int soLuongChieu = 0;
                        boolean hopLe = true;

                        if (!edtSoLuongThuoc.getText().toString().equals("")) {
                            soLuongThuoc = Integer.parseInt(edtSoLuongThuoc.getText().toString());
                        }

                        if (!edtSoLuongSang.getText().toString().equals("")) {
                            soLuongSang = Integer.parseInt(edtSoLuongSang.getText().toString());
                        }

                        if (!edtSoLuongTrua.getText().toString().equals("")) {
                            soLuongTrua = Integer.parseInt(edtSoLuongTrua.getText().toString());
                        }

                        if (!edtSoLuongChieu.getText().toString().equals("")) {
                            soLuongChieu = Integer.parseInt(edtSoLuongChieu.getText().toString());
                        }

                        if((soLuongSang + soLuongTrua + soLuongChieu) != 0){
                            if(soLuongThuoc % (soLuongSang + soLuongTrua + soLuongChieu) != 0){
                                hopLe = false;
                            }
                        }

                        if (soLuongThuoc < (soLuongSang + soLuongTrua + soLuongChieu)
                                || soLuongThuoc == 0 || (soLuongSang == 0 && chkSang.isChecked())
                                || (soLuongTrua == 0 && chkTrua.isChecked())
                                || (soLuongChieu == 0 && chkChieu.isChecked())
                                || !hopLe) {
                            Toast.makeText(activity, "Thông tin thuốc không chính xác", Toast.LENGTH_SHORT).show();
                        } else {
                            thuoc.setSoLuong(edtSoLuongThuoc.getText().toString());
                            if (soLuongSang > 0) {
                                thuoc.setLieuDungSang(String.valueOf(soLuongSang));
                            }

                            if (soLuongTrua > 0) {
                                thuoc.setLieuDungTrua(String.valueOf(soLuongTrua));
                            }

                            if (soLuongChieu > 0) {
                                thuoc.setLieuDungChieu(String.valueOf(soLuongChieu));
                            }
                            Toast.makeText(activity, "Đã lưu thông tin: " + thuoc.getTenThuoc(), Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                            dialogCapNhatThuoc.dismiss();
                        }
                    }
                });

                dialogCapNhatThuoc.show();
            }
        });

        return item;
    }
}