package android.familydoctor.Fragment;

import android.familydoctor.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

/**
 * Created by Au Nguyen on 7/14/2017.
 */

public class FragmentBacSi extends Fragment {
    Spinner spNamSinh;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_bac_si, container, false);
        spNamSinh = (Spinner)  container.findViewById(R.id.spNamSinh);

        ArrayList list = new ArrayList();

        for(int i = 1960; i < 2000; i++){
            list.add(i);
        }

        final ArrayAdapter<String> adapter = new ArrayAdapter(getContext(),android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spNamSinh.setAdapter(adapter);

        return rootView;
    }
}
