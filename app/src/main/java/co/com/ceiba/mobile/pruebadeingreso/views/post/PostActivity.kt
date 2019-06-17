package co.com.ceiba.mobile.pruebadeingreso.views.post

import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.models.PostModel
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_post.*
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil.Companion.R_string
import co.com.ceiba.mobile.pruebadeingreso.views.adapters.PostRecyclerAdapter

class PostActivity: AppCompatActivity() {

    private lateinit var postViewModel: PostViewModel
    private lateinit var dialog: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)
        setupView()
    }

    private fun setupView() {
        setupDialogProgress()
        setupViewModel()
        initSubscriptions()
    }

    private fun setupDialogProgress() {
        dialog = SpotsDialog.Builder()
            .setContext(this)
            .setMessage(R.string.generic_message_progress)
            .setCancelable(false)
            .build()
            .apply {
                show()
            }
    }

    private fun setupViewModel() {
        val userId = intent.getIntExtra(R_string(R.string.user_id), 0)
        postViewModel = ViewModelProviders.of(this).get(PostViewModel::class.java)
        postViewModel.getUser(userId)
        postViewModel.getPosts(userId)
    }

    private fun initSubscriptions() {
        postViewModel.singleLiveEvent.observe(this, Observer {
            when(it) {
                is PostViewModel.ViewEvent.ResponseSuccess -> {
                    dialog.dismiss()
                    setupAdapterUsers(it.posts)
                }
                is PostViewModel.ViewEvent.ResponseError -> {
                    dialog.dismiss()
                    Snackbar.make(recyclerViewPostsResults, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
                is PostViewModel.ViewEvent.ResponseUser -> {
                    setupInfoUser(it.user)
                }
            }
        })
    }

    private fun setupInfoUser(user: UserModel) {
        name.text = user.name
        phone.text = user.phone
        email.text = user.email
    }

    private fun setupAdapterUsers(posts: List<PostModel>) {
        recyclerViewPostsResults.layoutManager = LinearLayoutManager(this)
        recyclerViewPostsResults.adapter = PostRecyclerAdapter(posts, this)
    }
}