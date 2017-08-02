package android.familydoctor.Activity;

import android.content.Intent;
import android.familydoctor.R;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ViewWeb extends AppCompatActivity {

    WebView webView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_web);
        webView = (WebView)findViewById(R.id.web);
        Intent intent =getIntent();
        String linker ;
        linker = intent.getStringExtra("link");
        webView.loadUrl(linker);
        webView.setWebViewClient(new WebViewClient());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }
}
