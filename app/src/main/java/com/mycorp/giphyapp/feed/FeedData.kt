package com.mycorp.giphyapp.feed

import com.giphy.sdk.core.models.Media

open class FeedDataItem(val author: Author)
class GifItem(val media: Media, author: Author) : FeedDataItem(author)

enum class Author {
    Me,
}
