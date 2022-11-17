package com.didomi.sampleappkotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import io.didomi.sdk.Didomi

class WebViewActivity : AppCompatActivity() {

    companion object {
        private const val URL = "https://didomi.github.io/webpage-for-sample-app-webview/?didomiConfig.notice.enable=false"
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val webView = findViewById<WebView>(R.id.didomi_web_view)

        /** The notice should automatically get hidden in the web view as consent is passed from the
         *  mobile app to the website. However, it might happen that the notice gets displayed for a
         *  very short time before being hidden. You can disable the notice in your web view to make
         *  sure that it never shows by appending didomiConfig.notice.enable=false to the query
         *  string of the URL that you are loading **/
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                /** Inject consent information into the WebView **/
                val didomi = Didomi.getInstance()
                didomi.onReady {
                    val didomiJavaScriptCode = didomi.getJavaScriptForWebView()
                    webView.evaluateJavascript(didomiJavaScriptCode) {}
                }
            }
        }
        webView.loadUrl(URL)
    }
}
