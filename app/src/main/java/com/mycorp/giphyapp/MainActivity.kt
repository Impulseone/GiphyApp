package com.mycorp.giphyapp

import FeedAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import com.mycorp.giphyapp.databinding.ActivityMainBinding
import com.mycorp.giphyapp.feed.FeedDataItem
import com.mycorp.giphyapp.feed.GifItem
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    val apiKey = BuildConfig.giphy_api_key
    var settings = GPHSettings(gridType = GridType.waterfall, theme = GPHTheme.Light, stickerColumnCount = 3)
    var contentType = GPHContentType.gif
    var feedAdapter: FeedAdapter? = null
    var gifItems = ArrayList<FeedDataItem>()

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Giphy.configure(this, apiKey, verificationMode = true)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        initFab()
        setupFeed()
    }

    private fun initFab(){
        activityMainBinding.fab.setOnClickListener{
            val dialog = GiphyDialogFragment.newInstance(settings.copy(selectedContentType = contentType))
            dialog.gifSelectionListener = getGifSelectionListener()
            dialog.show(supportFragmentManager, "gifs_dialog")
        }
    }

    private fun getGifSelectionListener() = object : GiphyDialogFragment.GifSelectionListener {
        override fun onGifSelected(media: Media, searchTerm: String?, selectedContentType: GPHContentType) {
            Timber.d(TAG, "onGifSelected")
            gifItems.add(GifItem(media))
            feedAdapter?.notifyItemInserted(gifItems.size - 1)
            contentType = selectedContentType
        }

        override fun onDismissed(selectedContentType: GPHContentType) {
            Timber.d(TAG, "onDismissed")
            contentType = selectedContentType
        }

        override fun didSearchTerm(term: String) {
            Timber.d(TAG, "didSearchTerm $term")
        }
    }

    private fun setupFeed() {
        feedAdapter = FeedAdapter(gifItems)
        activityMainBinding.messageFeed.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this)
        activityMainBinding.messageFeed.adapter = feedAdapter
    }
}