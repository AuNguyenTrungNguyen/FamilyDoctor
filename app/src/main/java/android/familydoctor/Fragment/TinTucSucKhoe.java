package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.familydoctor.Activity.ViewWeb;
import android.familydoctor.Adapter.TinTucAdapter;
import android.familydoctor.Class.TinTuc;
import android.familydoctor.Class.XMLDOMParser;
import android.familydoctor.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class TinTucSucKhoe extends Fragment {
//abcbcbcascsa test git hub
    Context context;
    ArrayList<TinTuc> dsTinTuc;
    ListView lv;
    TinTucAdapter adapter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    private static final String ARG_SECTION_NUMBER = "section_number";

    public TinTucSucKhoe() {
    }

    public static TinTucSucKhoe newInstance(int sectionNumber) {
        TinTucSucKhoe fragment = new TinTucSucKhoe();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab1_tintuc, container, false);
        dsTinTuc= new ArrayList<>();
        lv= (ListView) rootView.findViewById(R.id.List_item);
        new ReadRSS().execute("http://vnexpress.net/rss/suc-khoe.rss");
        adapter= new TinTucAdapter(context,R.layout.item_rss,dsTinTuc);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ViewWeb.class);
                intent.putExtra("link", dsTinTuc.get(i).getLink());
                startActivity(intent);
                Log.d("LINKLINK",dsTinTuc.get(i).getLink());
            }
        });
        return rootView;
    }

    class ReadRSS extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            StringBuilder content = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                InputStreamReader inputStreamReader =new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line="";

                while((line=bufferedReader.readLine())!=null)
                {
                    content.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser parser= new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            String tieude="";
            String link="";
            String ngay_dang="";

            for(int i =0;i<nodeList.getLength();i++)
            {
                Element element= (Element) nodeList.item(i);
                tieude=parser.getValue(element,"title");
                link=parser.getValue(element,"link");
                ngay_dang=parser.getValue(element,"pubDate");
                dsTinTuc.add(new TinTuc(tieude,link,ngay_dang));
            }
        }
    }
}
