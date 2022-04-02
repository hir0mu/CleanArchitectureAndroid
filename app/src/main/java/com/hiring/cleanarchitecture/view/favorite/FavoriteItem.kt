package com.hiring.cleanarchitecture.view.favorite

import com.hiring.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hiring.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hiring.cleanarchitecture.util.SimpleItem

class FavoriteItem(
    private val model: ArticleBusinessModel,
    private val onItemClick: (ArticleBusinessModel) -> Unit
) : SimpleItem<ItemFavoriteArticleBinding>() {
    override fun bind(binding: ItemFavoriteArticleBinding) {
        binding.article = model.article
        binding.root.setOnClickListener { onItemClick(model) }
    }

    override fun isSameAs(other: SimpleItem<*>): Boolean {
        return (other is FavoriteItem) && other.model.article.id == model.article.id && other.model.isFavorite == model.isFavorite
    }

    override fun hasSameContentAs(other: SimpleItem<*>): Boolean {
        return (other is FavoriteItem) && other.model.article.id == model.article.id
    }
}
