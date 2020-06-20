package com.fd.guf.features.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import com.fd.guf.R
import com.fd.guf.databinding.ActivitySplashBinding
import com.fd.guf.features.searchUser.SearchUsersActivity


class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        binding.tvSearch.setOnClickListener {
            openSearchPage()
        }

        Handler().postDelayed({
            if (!isDestroyed) {
                openSearchPage()
            }
        }, 500)
    }

    private fun openSearchPage() {
        val intent = Intent(this, SearchUsersActivity::class.java)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this, binding.tvSearch, getString(R.string.search)
        )
        startActivity(intent, options.toBundle())
    }

}