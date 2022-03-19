package com.hiring.cleanarchitecture.view.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hiring.cleanarchitecture.R
import com.hiring.cleanarchitecture.databinding.FragmentArticleListBinding
import com.hiring.cleanarchitecture.databinding.ItemArticleBinding
import com.hiring.cleanarchitecture.ext.hideKeyboard
import com.hiring.cleanarchitecture.ext.setVisible
import com.hiring.cleanarchitecture.ext.setupToolbar
import com.hiring.cleanarchitecture.ext.showErrorSnackBar
import com.hiring.cleanarchitecture.util.SimpleAdapter
import com.hiring.cleanarchitecture.view.detail.ArticleDetailFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleListFragment : Fragment() {
    private val viewModel: ArticleListViewModel by viewModels()
    private lateinit var binding: FragmentArticleListBinding
    private val adapter = SimpleAdapter<ItemArticleBinding>(R.layout.item_article)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentArticleListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar(binding.toolbar, R.string.title_article_list)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.articleList.adapter = adapter
        val manager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.articleList.layoutManager = manager
        binding.articleList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val itemCount = viewModel.articleCount?.takeIf { it != 0 } ?: return
                if (manager.findLastVisibleItemPosition() > itemCount - 2) {
                    viewModel.fetchArticles()
                }
            }
        })
        binding.indicator.hide()

        binding.searchBar.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    v.hideKeyboard()
                    viewModel.fetchArticles(shouldReset = true)
                    true
                }
                else -> false
            }
        }

        viewModel.setup()
        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            adapter.updateItems(articles.map { article ->
                ArticleItem(
                    article,
                    onItemClick = {
                        parentFragmentManager.beginTransaction()
                            .addToBackStack(null)
                            .replace(R.id.container, ArticleDetailFragment.newInstance(it.id))
                            .commit()
                    },
                    onCheckedChange = {
                        viewModel.toggleFavorite(it)
                    }
                )
            })
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            when (it.execution) {
                ArticleListViewModel.FetchArticleListExecution -> binding.indicator.setVisible(it.value)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            when (it.execution) {
                ArticleListViewModel.FetchArticleListExecution -> {
                    showErrorSnackBar(it)
                }
            }
        }
    }
}
