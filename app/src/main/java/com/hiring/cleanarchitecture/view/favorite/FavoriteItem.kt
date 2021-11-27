package com.hiring.cleanarchitecture.view.favorite

import com.hiring.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.util.SimpleItem

class FavoriteItem(
    private val article: ArticleModel
): SimpleItem<ItemFavoriteArticleBinding>() {
    override fun bind(binding: ItemFavoriteArticleBinding) {
        binding.article = article
    }
}
