package co.com.ceiba.mobile.pruebadeingreso.views.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import co.com.ceiba.mobile.pruebadeingreso.R
import co.com.ceiba.mobile.pruebadeingreso.data.models.UserModel
import co.com.ceiba.mobile.pruebadeingreso.util.RUtil.Companion.R_string
import co.com.ceiba.mobile.pruebadeingreso.views.adapters.UserRecyclerAdapter
import com.google.android.material.snackbar.Snackbar
import com.jakewharton.rxbinding2.widget.RxTextView
import dmax.dialog.SpotsDialog
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity: AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var dialog: AlertDialog
    private var usersList: List<UserModel> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
    }

    private fun setupView() {
        setupDialogProgress()
        setupViewModel()
        initSubscriptions()
        setupRxFilterListener()
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
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getUsers()
    }

    private fun initSubscriptions() {
        mainViewModel.singleLiveEvent.observe(this, Observer {
            when(it) {
                is MainViewModel.ViewEvent.ResponseSuccess -> {
                    dialog.dismiss()
                    usersList = it.users
                    setupAdapterUsers(it.users)
                }
                is MainViewModel.ViewEvent.ResponseError -> {
                    dialog.dismiss()
                    Snackbar.make(recyclerViewSearchResults, it.errorMessage, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }

    @SuppressLint("CheckResult")
    private fun setupRxFilterListener() {
        val users = mutableListOf<UserModel>()
        RxTextView.textChanges(editTextSearch)
            .debounce(500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { text ->
                users.clear()
                usersList.forEach { user ->
                    if(user.name?.toLowerCase()?.contains(text.toString().toLowerCase())!!) {
                        users.add(user)
                    }
                }
                setupAdapterUsers(users)
            }
    }

    private fun setupAdapterUsers(users: List<UserModel>) {
        if(users.isNotEmpty()) {
            recyclerViewSearchResults.layoutManager = LinearLayoutManager(this)
            recyclerViewSearchResults.adapter = UserRecyclerAdapter(users, this)
        } else {
            Snackbar.make(recyclerViewSearchResults, R_string(R.string.list_is_empty), Snackbar.LENGTH_LONG).show()
        }
    }
}