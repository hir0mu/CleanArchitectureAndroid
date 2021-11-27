package com.hiring.cleanarchitecture.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.databinding.FragmentFavoriteListBinding
import com.hiring.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hiring.cleanarchitecture.ext.setupToolbar
import com.hiring.cleanarchitecture.util.SimpleAdapter
import org.koin.androidx.scope.ScopeFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class FavoriteListFragment: ScopeFragment() {
    private val viewModel: FavoriteListViewModel by viewModel()
    private lateinit var binding: FragmentFavoriteListBinding
    private val adapter = SimpleAdapter<ItemFavoriteArticleBinding>(R.layout.item_favorite_article)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar, R.string.title_favorite_list)

        binding.articleList.adapter = adapter
        binding.articleList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        viewModel.favorites.observe(viewLifecycleOwner) { articles ->
            adapter.updateItems(articles.map { article ->
                FavoriteItem(article)
            })
        }

        viewModel.error.observe(viewLifecycleOwner) {
            Timber.d("error: ${it.error}")
        }

        viewModel.fetchFavorites()
    }
}
