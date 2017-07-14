package android.familydoctor.Fragment;

/**
 * Created by ASUS on 27/05/2017.
 */

import android.familydoctor.Adapter.RSS_Adapter;
import android.familydoctor.Adapter.RSS_Item;
import android.familydoctor.R;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.*;
import java.net.*;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.util.ArrayList;
import java.util.List;

public class TinTucSucKhoe extends Fragment {

    private List<RSS_Item> listItem_Rss = new ArrayList<>();

    private RSS_Adapter adapter_RSS;
    private RecyclerView recyclerView_RSS;
    WebView wv;
    LinearLayout lineWebView;


    private String mFeedTitle;
    private String mFeedLink;
    private String mFeedDescription;
    private String mImg_url;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab3_sukien_nongnghiep, container, false);


//        //Sự kiện 1
//        String url="https://scontent.fsgn4-1.fna.fbcdn.net/v/t1.0-9/18813258_1500198210031718_550385503683032580_n.jpg?oh=492d852960305f62a04cb5bfb9bfea6f&oe=59E77057";
//        imageView_Sukien1 = (ImageView) rootView.findViewById(R.id.imgView_SuKien1);
//        Picasso.with(this.getActivity()).load(url).into(imageView_Sukien1);
//
//        cardViewSuKien1= (CardView) rootView.findViewById(R.id.card_view_SuKien1);
//        cardViewSuKien1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setData(Uri.parse("http://startupwheel.vn"));
//                startActivity(intent);
//            }
//        });
//
//        //Sự Kiện 2



        //RSS


        Button dong_webview= (Button) rootView.findViewById(R.id.dong_web_view);
        lineWebView = (LinearLayout) rootView.findViewById(R.id.line_web_view);
        wv = (WebView) rootView.findViewById(R.id.web_view);



        recyclerView_RSS = (RecyclerView) rootView.findViewById(R.id.rss_tintuc);
//        adapter_RSS = new RSS_Adapter(listItem_Rss,this.getContext());
        recyclerView_RSS.setLayoutManager(new LinearLayoutManager(getActivity()));


        dong_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_RSS.setVisibility(View.VISIBLE);
                lineWebView.setVisibility(View.GONE);

            }
        });


        //Đọc RSS
        new FetchFeedTask().execute((Void) null);


//        even();
        return rootView;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


//    public void even(){
//
//        adapter_RSS.setOnItemClickListenerRSS(new RSS_Adapter.ClickListenerRSS() {
//            @Override
//            public void onItemClick(View v, int position) {
//                wv.loadData(listItem_Rss.get(position).getLink(), "text/html; charset=utf-8", null);
//
//                wv.setVisibility(View.VISIBLE);
//            }
//        });
//
//    }


    //Lấy Dữ liệu
    public List<RSS_Item> parseFeed(InputStream inputStream) throws XmlPullParserException, IOException {
        String title = null;
        String link = null;
        String description = null;
        String img_url = null;
        boolean isItem = false;
        List<RSS_Item> items = new ArrayList<>();

        try {
            XmlPullParser xmlPullParser = Xml.newPullParser();
            xmlPullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            xmlPullParser.setInput(inputStream, null);

            xmlPullParser.nextTag();
            while (xmlPullParser.next() != XmlPullParser.END_DOCUMENT) {
                int eventType = xmlPullParser.getEventType();

                String name = xmlPullParser.getName();
                if(name == null)
                    continue;

                if(eventType == XmlPullParser.END_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = false;
                    }
                    continue;
                }

                if (eventType == XmlPullParser.START_TAG) {
                    if(name.equalsIgnoreCase("item")) {
                        isItem = true;
                        continue;
                    }
                }

                Log.d("MainActivity", "Parsing name ==> " + name);
                String result = "";
                if (xmlPullParser.next() == XmlPullParser.TEXT) {
                    result = xmlPullParser.getText();
                    xmlPullParser.nextTag();
                }


                if (name.equalsIgnoreCase("image")){
                    img_url = result;

                }else if (name.equalsIgnoreCase("title")) {
                    title = result;
                } else if (name.equalsIgnoreCase("link")) {
                    link = result;
                } else if (name.equalsIgnoreCase("description")) {
                    description = result;
                }

                if (title != null && link != null && description != null) {
                    if(isItem) {
                        RSS_Item item = new RSS_Item(title, img_url , description,link);
                        items.add(item);
                    }
                    else {
                        mFeedTitle = title;
                        mFeedLink = link;
                        mFeedDescription = description;
                        mImg_url = img_url;
                    }

                    title = null;
                    link = null;
                    description = null;
                    img_url = null;
                    isItem = false;
                }
            }

            return items;
        } finally {
            inputStream.close();
        }
    }

    private class FetchFeedTask extends AsyncTask<Void, Void, Boolean> {

        private String urlLink;

        @Override
        protected void onPreExecute() {

            mFeedTitle = null;
            mFeedLink = null;
            mFeedDescription = null;
            mImg_url = null;
            urlLink = "http://nongnghiep.vn/rss/khuyen-nong-7.rss" ;


        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (TextUtils.isEmpty(urlLink))
                return false;

            try {



                URL url = new URL(urlLink);
                InputStream inputStream = url.openConnection().getInputStream();
                listItem_Rss = parseFeed(inputStream);
                return true;
            } catch (IOException e) {
                Log.e("RSS", "Error", e);
            } catch (XmlPullParserException e) {
                Log.e("RSS", "Error", e);
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {

            if (success) {

//                RSS_Item rss_item = new RSS_Item(mFeedTitle,)
//                listItem_Rss.add()
//                mFeedTitleTextView.setText("Feed Title: " + mFeedTitle);
//                mFeedDescriptionTextView.setText("Feed Description: " + mFeedDescription);
//                mFeedLinkTextView.setText("Feed Link: " + mFeedLink);
                // Fill RecyclerView


                recyclerView_RSS.setAdapter(new RSS_Adapter(listItem_Rss,getContext()));




            } else {
                Toast.makeText(getActivity(),
                        "Chưa kết nối Internet",
                        Toast.LENGTH_LONG).show();
            }
        }
    }


}
