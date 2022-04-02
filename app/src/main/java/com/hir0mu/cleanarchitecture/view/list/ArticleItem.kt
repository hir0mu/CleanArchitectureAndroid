package com.hir0mu.cleanarchitecture.view.list

import com.hir0mu.cleanarchitecture.databinding.ItemArticleBinding
import com.hir0mu.cleanarchitecture.domain.businessmodel.ArticleBusinessModel
import com.hir0mu.cleanarchitecture.util.SimpleItem

class ArticleItem(
    private var articleModel: ArticleBusinessModel,
    private val onItemClick: (ArticleBusinessModel) -> Unit,
    private val onCheckedChange: (ArticleBusinessModel) -> Unit
) : SimpleItem<ItemArticleBinding>() {
    override fun bind(binding: ItemArticleBinding) {
        binding.article = articleModel.article
        binding.favorite.isChecked = articleModel.isFavorite
        binding.favorite.setOnClickListener {
            val old = articleModel.copy()
            articleModel = old.copy(isFavorite = !articleModel.isFavorite)
            onCheckedChange(old)
        }
        binding.root.setOnClickListener { onItemClick(articleModel) }
    }

    override fun isSameAs(other: SimpleItem<*>): Boolean {
        return (other is ArticleItem) && other.articleModel.article.id == articleModel.article.id && other.articleModel.isFavorite == articleModel.isFavorite
    }

    override fun hasSameContentAs(other: SimpleItem<*>): Boolean {
        return (other is ArticleItem) && other.articleModel.article.id == articleModel.article.id
    }
}
