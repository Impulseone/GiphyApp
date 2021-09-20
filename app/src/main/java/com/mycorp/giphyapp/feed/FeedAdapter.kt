
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mycorp.giphyapp.R
import com.mycorp.giphyapp.databinding.GifItemBinding
import com.mycorp.giphyapp.feed.FeedDataItem
import com.mycorp.giphyapp.feed.GifItem

class FeedAdapter(val items: MutableList<FeedDataItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_GIF = 101
    private val ITEM_NONE = 102

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_GIF -> GifViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.gif_item, parent, false)
            )
            else -> throw RuntimeException("unsupported type")
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (items[p1]) {
            is GifItem -> {
                (p0 as GifViewHolder).bindMessage(items[p1] as GifItem)
            }
            else -> throw RuntimeException("type not allowed")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is GifItem -> ITEM_GIF
            else -> ITEM_NONE
        }
    }

    inner class GifViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindMessage(message: GifItem) {
            GifItemBinding.bind(itemView).apply {
                gifView.setMedia(message.media)
                gifView.isBackgroundVisible = false
            }
        }
    }
}
