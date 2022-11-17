package com.didomi.sampleappkotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import io.didomi.sdk.Didomi
import io.didomi.sdk.events.ConsentChangedEvent
import io.didomi.sdk.events.EventListener

class MainActivity : AppCompatActivity() {

    private var interstitialAd: InterstitialAd? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonPurposesPreferences = findViewById<AppCompatButton>(R.id.didomi_button_preferences_purposes)
        val buttonVendorsPreferences = findViewById<AppCompatButton>(R.id.didomi_button_preferences_vendors)
        val buttonWebView = findViewById<AppCompatButton>(R.id.didomi_button_web_view)
        val buttonAd = findViewById<AppCompatButton>(R.id.didomi_button_ad)

        buttonPurposesPreferences.setOnClickListener { showPurposesPreferences() }
        buttonVendorsPreferences.setOnClickListener { showVendorsPreferences() }
        buttonWebView.setOnClickListener { showWebView() }
        buttonAd.setOnClickListener { showAd() }

        Didomi.getInstance().setupUI(this)

        //loadAd()
        prepareAd()
    }

    private fun showPurposesPreferences() {
        val didomi = Didomi.getInstance()
        didomi.onReady {
            didomi.showPreferences(this)
        }
    }

    private fun showVendorsPreferences() {
        val didomi = Didomi.getInstance()
        didomi.onReady {
            didomi.showPreferences(this, "vendors")
        }
    }

    private fun showWebView() {
        val intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
    }

    private fun showAd() {
        if (interstitialAd != null) {
            interstitialAd?.show(this)
        } else {
            Log.d(TAG, "The interstitial ad wasn't ready yet.")
            loadAd()
        }
    }

    private fun loadAd() {
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(
            this,
            "ca-app-pub-3940256099942544/1033173712",
            adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.d(TAG, adError.message)
                    interstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    Log.d(TAG, "Ad was loaded.")
                    this@MainActivity.interstitialAd = interstitialAd

                    interstitialAd.fullScreenContentCallback =
                        object : FullScreenContentCallback() {
                            override fun onAdDismissedFullScreenContent() {
                                Log.d(TAG, "Ad was dismissed.")
                            }

                            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                                Log.d(TAG, "Ad failed to show.")
                            }

                            override fun onAdShowedFullScreenContent() {
                                Log.d(TAG, "Ad showed fullscreen content.")
                                this@MainActivity.interstitialAd = null
                                loadAd()
                            }
                        }
                }
            })
    }

    /**
     * Will reset / preload Ad after each consent change
     * Consent allows Ads: the ad cache will be prepared (ad is displayed on first click after reject -> accept)
     * Consent rejects Ads: the ad cache will be purged (no ad on first click after reject)
     */
    private fun prepareAd() {
        val didomi = Didomi.getInstance()
        didomi.onReady {
            loadAd()

            val didomiEventListener = object : EventListener() {
                override fun consentChanged(event: ConsentChangedEvent) {
                    // The consent status of the user has changed
                    loadAd()
                }
            }
            didomi.addEventListener(didomiEventListener as EventListener)
        }
    }
}