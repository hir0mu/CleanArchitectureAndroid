package com.hiring.cleanarchitecture.view.list

import com.hiring.cleanarchitecture.databinding.ItemArticleBinding
import com.hiring.cleanarchitecture.domain.model.ArticleModel
import com.hiring.cleanarchitecture.util.SimpleItem

class ArticleItem(
    private var articleModel: ArticleModel,
    private val onCheckedChange: (ArticleModel) -> Unit
): SimpleItem<ItemArticleBinding>() {
    override fun bind(binding: ItemArticleBinding) {
        binding.article = articleModel
        binding.favorite.isChecked = articleModel.isFavorite
        binding.favorite.setOnClickListener {
            val old = articleModel.copy()
            articleModel = old.copy(isFavorite = !articleModel.isFavorite)
            onCheckedChange(old)
        }
    }

    override fun isSameAs(other: SimpleItem<*>): Boolean {
        return (other is ArticleItem) && other.articleModel.id == articleModel.id && other.articleModel.isFavorite == articleModel.isFavorite
    }

    override fun hasSameContentAs(other: SimpleItem<*>): Boolean {
        return (other is ArticleItem) && other.articleModel.id == articleModel.id
    }
}
