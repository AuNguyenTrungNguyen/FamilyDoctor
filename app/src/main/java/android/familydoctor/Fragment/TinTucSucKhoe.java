package android.familydoctor.Fragment;

import android.content.Context;
import android.content.Intent;
import android.familydoctor.Activity.ViewWeb;
import android.familydoctor.Adapter.CustomAdapter;
import android.familydoctor.Adapter.TinTuc;
import android.familydoctor.Adapter.XMLDOMParser;
import android.familydoctor.R;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Trung Banh on 30-Jun-17.
 */

public class TinTucSucKhoe extends Fragment {

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    ListView listView;

    CustomAdapter customAdapter;
    ArrayList<TinTuc> tinTucs;

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

        tinTucs = new ArrayList<TinTuc>();

        listView = (ListView) rootView.findViewById(R.id.List_item);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("http://vnexpress.net/rss/suc-khoe.rss");
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ViewWeb.class);
                intent.putExtra("link", tinTucs.get(position).link);
                startActivity(intent);
            }
        });

        return rootView;
    }

    class ReadData extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return docNoiDung_Tu_URL(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDes = document.getElementsByTagName("description");
            String title = "";
            String Image = "";
            String link = "";
            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);
                title = parser.getValue(element, "title");
                link = parser.getValue(element, "link");
                String Cdata = nodeListDes.item(i + 1).getTextContent();
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                Matcher matcher = p.matcher(Cdata);
                if (matcher.find()) {
                    Image = matcher.group(1);
                }
                tinTucs.add(new TinTuc(title, link, Image));
            }
            customAdapter = new CustomAdapter(getActivity(),android.R.layout.simple_list_item_1, tinTucs);
            listView.setAdapter(customAdapter);
        }
    }

    private String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
}