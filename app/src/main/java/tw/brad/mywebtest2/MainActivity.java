package tw.brad.mywebtest2;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;
    private TextView mesg;
    private String myname = "";
    private UIHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();
        mesg = findViewById(R.id.mesg);
        webView = findViewById(R.id.webview);
        initWebView();
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyJS(), "sakura");

        webView.loadUrl("file:///android_asset/brad01.html");
//        webView.loadUrl("https://www.sakura.com.tw");
    }

    public class MyJS {
        @JavascriptInterface
        public void myTest1(String myname){
            Log.v("brad", "Welcome " + myname);
            MainActivity.this.myname = myname;
            handler.sendEmptyMessage(0);
        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mesg.setText("Welcome " + myname);
        }
    }


}
