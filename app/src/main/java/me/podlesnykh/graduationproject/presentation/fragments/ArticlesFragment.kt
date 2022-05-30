package me.podlesnykh.graduationproject.presentation.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentArticlesBinding
import me.podlesnykh.graduationproject.presentation.adapters.ArticlesListAdapter
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.viewmodels.ArticlesViewModel

class ArticlesFragment : BaseFragment() {

    private lateinit var viewModel: ArticlesViewModel

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private val articlesListAdapter = ArticlesListAdapter(::onWatchFullArticleClicked).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        setupToolbar()
        setupRecyclerView()
        articlesListAdapter.submitList(listOf(
            ArticleModel(
                publishedAt = "",
                author = "",
                urlToImage = "https://sun9-west.userapi.com/sun9-53/s/v1/ig2/d6TITJxbzn-26rusSCxEUDfTz2p47KDAS3c8pAiZ6-u6imO09rYPokW3E3YCcCYeB-BnOq-QcUfK2_m99lxOF-Es.jpg?size=453x604&quality=96&type=album",
                description = "мужик суицид",
                source = "xz",
                title = "не стреляй пж",
                url = "",
                content = "jopa"
            ),
            ArticleModel(
                publishedAt = "",
                author = "",
                urlToImage = "https://sun9-west.userapi.com/sun9-68/s/v1/ig2/JUqNUtMookeZvlKMRppbKnUaFY6JCfFMWMiqfArdHtE9hvecSkbI7WCV-fVvralPR4qFlEPxM_5IznudXiJFByfy.jpg?size=600x600&quality=95&type=album",
                description = "потеря",
                source = "xz",
                title = "отгрызи яйца)",
                url = "",
                content = "jopa"
            ),
        ))
        viewModel = ViewModelProvider(this).get(ArticlesViewModel::class.java)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.articlesToolbar.title = this.getString(R.string.everything)
        binding.articlesToolbar.setTitleTextColor(ResourcesCompat.getColor(this.resources, R.color.white, Resources.getSystem().newTheme()))
        (activity as AppCompatActivity).setSupportActionBar(binding.articlesToolbar)
    }

    private fun setupRecyclerView() {
        binding.articlesRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.articlesRecyclerView.adapter = articlesListAdapter
    }

    private fun onWatchFullArticleClicked(view: View) {
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }
}