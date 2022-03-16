package com.hiring.cleanarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import com.hiring.cleanarchitecture.databinding.ActivityMainBinding
import com.hiring.cleanarchitecture.view.favorite.FavoriteListFragment
import com.hiring.cleanarchitecture.view.list.ArticleListFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    supportFragmentManager.beginTransaction()
      .add(binding.container.id, ArticleListFragment())
      .commit()

    binding.bottomNavigation.setOnItemSelectedListener { menu ->
      val fragment = when (menu.itemId) {
        R.id.navigation_search -> ArticleListFragment()
        R.id.navigation_favorite -> FavoriteListFragment()
        else -> null
      }
      fragment?.let {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        supportFragmentManager.beginTransaction()
          .replace(binding.container.id, it)
          .commit()
        true
      } ?: false
    }
  }
}
