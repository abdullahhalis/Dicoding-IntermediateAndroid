package com.dicoding.mystory.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mystory.R
import com.dicoding.mystory.databinding.ActivityMainBinding
import com.dicoding.mystory.ui.addStory.AddStoryActivity
import com.dicoding.mystory.ui.map.MapsActivity
import com.dicoding.mystory.ui.welcome.WelcomeActivity
import com.dicoding.mystory.utils.StoryViewModelFactory
import com.dicoding.mystory.utils.showToast

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel: MainViewModel by viewModels { StoryViewModelFactory.getInstance(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        setListStory()
    }

    private fun init(){
        mainViewModel.getSession().observe(this){ user ->
            if(!user.isLogin){
                val intent = Intent(this, WelcomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                mainViewModel.clearRepository()
                startActivity(intent)
            }
            showToast(this, String.format(getString(R.string.welcome),user.name))
            if(user.token.isNotEmpty()){
                Log.d("token main activity", "token: ${user.token}")

            }
        }

        binding.fabAdd.setOnClickListener{
            val intent = Intent(this, AddStoryActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                mainViewModel.logout()
            }
            R.id.menu_language -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            }
            R.id.menu_map -> {
                startActivity(Intent(this, MapsActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListStory() {
        val listUserAdapter = ListAdapter()
        binding.apply {
            rvList.layoutManager = LinearLayoutManager(this@MainActivity)
            rvList.adapter = listUserAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter {
                    listUserAdapter.retry()
                },
                footer = LoadingStateAdapter {
                    listUserAdapter.retry()
                }
            )
        }
        mainViewModel.listStory.observe(this) {
            listUserAdapter.submitData(lifecycle, it)
        }
    }
}