package me.lab.happyprimo.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.lab.happyprimo.databinding.ItemFeedBinding
import me.lab.happyprimo.data.models.FeedResponseModel.Item

class FeedAdapter(private val items: List<Item?>? ,private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<FeedAdapter.ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(item: Item )

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFeedBinding.inflate(inflater, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items?.get(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    override fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class ItemViewHolder(private val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Item) {


          binding.apply {
              textViewTitle.text = item.title?.titleData
              textViewCategory.text = "Cetegory : ${item.title!!.link!!.guid!!.category.toString()}"
              textViewCreateBy.text = "Create by: " + item.title!!.link!!.guid!!.dcCreator!!.dcCreatorData
              root.setOnClickListener {
                  itemClickListener.onItemClick(item)
              }
          }
        }
    }
}
