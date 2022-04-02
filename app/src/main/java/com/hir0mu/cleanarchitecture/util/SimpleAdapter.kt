package com.hir0mu.cleanarchitecture.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.atomic.AtomicLong

abstract class SimpleItem<CELL : ViewDataBinding> {
    companion object {
        private val ID_COUNTER = AtomicLong(0)
    }

    private val id: Long = ID_COUNTER.decrementAndGet()

    abstract fun bind(binding: CELL)

    open fun isSameAs(other: SimpleItem<*>): Boolean {
        return other.id == id
    }

    open fun hasSameContentAs(other: SimpleItem<*>): Boolean {
        return other == this
    }
}

class SimpleAdapter<CELL : ViewDataBinding>(
    @LayoutRes private val cellLayout: Int
) : RecyclerView.Adapter<SimpleAdapter.ViewHolder<CELL>>() {
    private val items: MutableList<SimpleItem<CELL>> = mutableListOf()

    fun updateItems(newItems: List<SimpleItem<CELL>>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = items.size

            override fun getNewListSize(): Int = newItems.size

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return newItems[newItemPosition].hasSameContentAs(items[oldItemPosition])
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return newItems[newItemPosition].isSameAs(items[oldItemPosition])
            }
        })
        items.clear()
        items.addAll(newItems)
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder<CELL> {
        val view = LayoutInflater.from(parent.context)
            .inflate(cellLayout, parent, false)
        val binding = DataBindingUtil.bind<CELL>(view) ?: throw IllegalStateException()
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder<CELL>, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder<CELL : ViewDataBinding>(
        private val binding: CELL
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SimpleItem<CELL>) {
            item.bind(binding)
        }
    }
}
