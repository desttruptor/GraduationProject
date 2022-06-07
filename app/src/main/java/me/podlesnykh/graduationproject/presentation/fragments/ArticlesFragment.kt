package me.podlesnykh.graduationproject.presentation.fragments

import android.content.Context
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
import com.google.android.material.snackbar.Snackbar
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentArticlesBinding
import me.podlesnykh.graduationproject.di.application.AppComponent
import me.podlesnykh.graduationproject.presentation.activities.MainActivity
import me.podlesnykh.graduationproject.presentation.adapters.ArticlesListAdapter
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment
import me.podlesnykh.graduationproject.presentation.viewmodels.ArticlesViewModel

class ArticlesFragment : BaseFragment() {

    private lateinit var viewModel: ArticlesViewModel
    private lateinit var appComponent: AppComponent

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!

    private val articlesListAdapter = ArticlesListAdapter(::onWatchFullArticleClicked).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent = (activity as MainActivity).appComponent
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        setupToolbar()
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]
        appComponent.inject(viewModel)
        viewModel.articlesListLiveData.observe(viewLifecycleOwner) {
            articlesListAdapter.submitList(it)
        }
        viewModel.errorDialogLiveData.observe(viewLifecycleOwner) {
            Snackbar.make(binding.articlesBottomNavbar, it, 5000).show()
        }
        viewModel.getArticles()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.articlesToolbar.title = this.getString(R.string.everything)
        binding.articlesToolbar.setTitleTextColor(
            ResourcesCompat.getColor(
                this.resources,
                R.color.white,
                Resources.getSystem().newTheme()
            )
        )
        (activity as AppCompatActivity).setSupportActionBar(binding.articlesToolbar)
    }

    private fun setupRecyclerView() {
        binding.articlesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.articlesRecyclerView.adapter = articlesListAdapter
    }

    private fun onWatchFullArticleClicked(view: View) {
    }

    companion object {
        fun newInstance() = ArticlesFragment()
    }
}