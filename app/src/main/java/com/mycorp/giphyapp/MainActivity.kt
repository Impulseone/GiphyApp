package com.mycorp.giphyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment

class MainActivity : AppCompatActivity() {

    val apiKey = BuildConfig.giphy_api_key
    var settings = GPHSettings(gridType = GridType.waterfall, theme = GPHTheme.Light, stickerColumnCount = 3)
    var contentType = GPHContentType.gif

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Giphy.configure(this, apiKey, verificationMode = true)
        val dialog = GiphyDialogFragment.newInstance(settings.copy(selectedContentType = contentType))
        dialog.show(supportFragmentManager, "gifs_dialog")
    }
}