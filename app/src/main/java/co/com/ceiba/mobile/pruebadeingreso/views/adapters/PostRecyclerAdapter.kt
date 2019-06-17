package co.com.ceiba.mobile.pruebadeingreso.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.models.PostModel
import kotlinx.android.synthetic.main.post_list_item.view.*

class PostRecyclerAdapter(
    private val posts: List<PostModel>,
    private val context: Context): RecyclerView.Adapter<ViewHolderPost>() {

    override fun getItemCount(): Int {
        return posts.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderPost {
        return ViewHolderPost(LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderPost, position: Int) {
        val post = posts[position]
        holder.lblName.text = post.title
        holder.lblPhone.text = post.body
    }
}

class ViewHolderPost (view: View): RecyclerView.ViewHolder(view) {
    val lblName: TextView = view.title
    val lblPhone: TextView = view.body
}