package tw.brad.mywebtest2;

import android.app.ProgressDialog;
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
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new UIHandler();
        mesg = findViewById(R.id.mesg);
        webView = findViewById(R.id.webview);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);



        initWebView();
    }

    private void initWebView(){
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.addJavascriptInterface(new MyJS(), "sakura");

        webView.setWebViewClient(new MyWebViewClient());

//        webView.loadUrl("file:///android_asset/brad01.html");
        webView.loadUrl("https://www.sakura.com.tw");
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog.show();
            Log.v("brad", "StartPage:" + url);
            if (url.equals("https://www.sakura.com.tw/customer")){
                webView.loadUrl("https://www.bradchao.com");
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressDialog.dismiss();
            Log.v("brad", "FinishPage:" + url);
        }
    }


    public class MyJS {
        @JavascriptInterface
        public void myTest1(String myname2){
            Log.v("brad", "Welcome " + myname2);
            myname = myname2;
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
