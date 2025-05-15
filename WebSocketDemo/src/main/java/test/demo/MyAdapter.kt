package test.demo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import test.demo.MainActivity.Companion.SENDER_CLIENT
import test.demo.MainActivity.Companion.SENDER_SERVER
import test.demo.MainActivity.Companion.SENDER_SYSTEM

class MyAdapter(val activity: MainActivity) : RecyclerView.Adapter<MyAdapter.BubbleViewHolder>() {

    // 列表实时内容
    private val contents: ArrayList<BubbleData> = arrayListOf()

    // 列表大小，避免实时求
    private var size = 0

    fun addContent(data: BubbleData) {
        contents.add(data)
        size++
        notifyItemInserted(size - 1)
        activity.binding.recyclerView.smoothScrollToPosition(size - 1) //自动滚动到底部
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BubbleViewHolder {
        return when (viewType) {
            SENDER_SERVER -> BubbleViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.bubble_server, parent, false)
            )
            SENDER_CLIENT -> BubbleViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.bubble_client, parent, false)
            )
            else -> BubbleViewHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.bubble_system, parent, false)
            )
        }
    }

    override fun getItemCount(): Int = size

    override fun onBindViewHolder(holder: BubbleViewHolder, position: Int) {
        holder.applyData(contents[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            contents[position].sender == SENDER_SERVER -> SENDER_SERVER
            contents[position].sender == SENDER_CLIENT -> SENDER_CLIENT
            else -> SENDER_SYSTEM
        }
    }


    class BubbleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content: AppCompatTextView = itemView.findViewById(R.id.content)
        fun applyData(data: BubbleData) {
            content.text = data.strContent
        }
    }

}