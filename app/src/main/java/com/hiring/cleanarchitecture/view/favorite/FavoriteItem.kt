package com.hiring.cleanarchitecture.view.favorite

import com.hiring.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.util.SimpleItem
import com.hiring.cleanarchitecture.view.list.ArticleItem

class FavoriteItem(
    private val article: ArticleModel,
    private val onItemClick: (ArticleModel) -> Unit
): SimpleItem<ItemFavoriteArticleBinding>() {
    override fun bind(binding: ItemFavoriteArticleBinding) {
        binding.article = article
        binding.root.setOnClickListener { onItemClick(article) }
    }

    override fun isSameAs(other: SimpleItem<*>): Boolean {
        return (other is FavoriteItem) && other.article.id == article.id && other.article.isFavorite == article.isFavorite
    }

    override fun hasSameContentAs(other: SimpleItem<*>): Boolean {
        return (other is FavoriteItem) && other.article.id == article.id
    }
}
