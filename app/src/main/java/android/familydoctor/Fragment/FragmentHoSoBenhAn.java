package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.content.Intent;
import android.familydoctor.Activity.ThemHoSoBenhAnActivity;
import android.familydoctor.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentHoSoBenhAn extends Fragment {

    FloatingActionButton fabThemHoSoBenhAn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_ho_so_benh_an, container, false);

        fabThemHoSoBenhAn = (FloatingActionButton) rootView.findViewById(R.id.fabThemHoSoBenhAn);

        fabThemHoSoBenhAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentThemHoSoBenhAn = new Intent(getContext(), ThemHoSoBenhAnActivity.class);
                startActivity(intentThemHoSoBenhAn);
            }
        });
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
