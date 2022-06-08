package me.podlesnykh.graduationproject.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.ArticlesListItemBinding
import me.podlesnykh.graduationproject.presentation.models.ArticleModel

/**
 * [RecyclerView.Adapter] for list of articles displayed in fragment
 */
class ArticlesListAdapter(
    private val onWatchFullArticleClickCallback: (View) -> Unit
) : RecyclerView.Adapter<ArticlesListAdapter.ArticlesListViewHolder>() {

    private var displayedList: List<ArticleModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ArticlesListViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.articles_list_item, parent, false)
    )

    override fun onBindViewHolder(holder: ArticlesListViewHolder, position: Int) =
        holder.bind(displayedList[position])

    override fun getItemCount() = displayedList.size

    fun submitList(newList: List<ArticleModel>) =
        DiffUtil.calculateDiff(ArticlesListDiffUtilsCallback(displayedList, newList)).also {
            displayedList = newList
            it.dispatchUpdatesTo(this)
        }

    inner class ArticlesListViewHolder(recyclerListItem: View) :
        RecyclerView.ViewHolder(recyclerListItem) {
        private val recyclerListItemBinding = ArticlesListItemBinding.bind(recyclerListItem)
        private var isExpandableBlockVisible = false
        private val expandableBlockVisibility
            get() = if (isExpandableBlockVisible) View.VISIBLE else View.GONE
        private val animationOnExpand =
            RotateAnimation(
                0f,
                180f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                duration = 200
                interpolator = LinearInterpolator()
                repeatCount = 0
                fillAfter = true
            }

        private val animationOnCollapse =
            RotateAnimation(
                180f,
                0f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
            ).apply {
                duration = 160
                interpolator = LinearInterpolator()
                repeatCount = 0
                fillAfter = true
            }

        fun bind(articleModel: ArticleModel) {
            with(recyclerListItemBinding) {
                Glide.with(articleItemImageView.context)
                    .load(articleModel.urlToImage)
                    .into(articleItemImageView)
                articleItemTitleTextView.text = articleModel.title
                articleItemDetailsTextView.text = articleModel.description
                articleItemExpandButton.setImageDrawable(
                    AppCompatResources.getDrawable(
                        articleItemExpandButton.context,
                        R.drawable.ic_baseline_keyboard_arrow_down_24
                    )
                )
                articleItemExpandButton.setOnClickListener(::changeBlockVisibility)
                recyclerListItemBinding.articleItemDetailsScrollView.visibility =
                    expandableBlockVisibility
                articleItemSeeDetailsButton.setOnClickListener(onWatchFullArticleClickCallback)
            }
        }

        private fun changeBlockVisibility(view: View) {
            isExpandableBlockVisible = !isExpandableBlockVisible
            recyclerListItemBinding.articleItemDetailsScrollView.visibility =
                expandableBlockVisibility
            view.startAnimation(
                if (isExpandableBlockVisible)
                    animationOnExpand
                else
                    animationOnCollapse
            )
        }
    }

    inner class ArticlesListDiffUtilsCallback(
        private val oldList: List<ArticleModel>,
        private val newList: List<ArticleModel>
    ) : DiffUtil.Callback() {
        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList[oldItemPosition].url == newList[newItemPosition].url

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldList.containsAll(newList) && newList.containsAll(oldList)
    }
}