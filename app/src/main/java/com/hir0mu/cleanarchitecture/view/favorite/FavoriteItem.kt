package com.hir0mu.cleanarchitecture.view.favorite

import com.hir0mu.cleanarchitecture.databinding.ItemFavoriteArticleBinding
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.util.SimpleItem

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
