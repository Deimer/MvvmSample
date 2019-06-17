package co.com.ceiba.mobile.pruebadeingreso.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil.Companion.R_string
import co.com.ceiba.mobile.pruebadeingreso.views.post.PostActivity
import kotlinx.android.synthetic.main.user_list_item.view.*

class UserRecyclerAdapter(
    private val users: List<UserModel>,
    private val context: Context): RecyclerView.Adapter<ViewHolderUser>() {

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderUser {
        return ViewHolderUser(LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolderUser, position: Int) {
        val user = users[position]
        holder.lblName.text = user.name
        holder.lblPhone.text = user.phone
        holder.lblEmail.text = user.email
        holder.btnViewPost.setOnClickListener {
            val intent = Intent(context, PostActivity::class.java)
            intent.putExtra(R_string(R.string.user_id), user.id)
            context.startActivity(intent)
        }
    }
}

class ViewHolderUser (view: View): RecyclerView.ViewHolder(view) {
    val lblName: TextView = view.name
    val lblPhone: TextView = view.phone
    val lblEmail: TextView = view.email
    val btnViewPost: Button = view.btn_view_post
}