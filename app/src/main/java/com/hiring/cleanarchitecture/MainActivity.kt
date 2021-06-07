package com.hiring.cleanarchitecture

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.hiring.cleanarchitecture.databinding.ActivityMainBinding
import com.hiring.cleanarchitecture.view.list.ArticleListFragment

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

    supportFragmentManager.beginTransaction()
      .add(binding.container.id, ArticleListFragment())
      .commit()
  }
}
