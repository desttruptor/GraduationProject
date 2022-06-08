package me.podlesnykh.graduationproject.presentation.fragments

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentArticlesBinding
import me.podlesnykh.graduationproject.presentation.adapters.ArticlesListAdapter
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment
import me.podlesnykh.graduationproject.presentation.viewmodels.ArticlesViewModel

class ArticlesFragment : BaseFragment() {

    private lateinit var viewModel: ArticlesViewModel

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private var isFabOpen = false

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
        setupFab()
        setupViewModel()
        appComponent.inject(viewModel)
        viewModel.getArticles()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        binding.articlesToolbar.title = context?.getString(R.string.everything)
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
        binding.articlesRecyclerView.addOnScrollListener(
            object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (!recyclerView.canScrollVertically(1)) {
                        onEndOfListReached()
                    }
                }
            }
        )
    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]
        viewModel.articlesListLiveData.observe(viewLifecycleOwner, articlesListAdapter::submitList)
        viewModel.errorDialogLiveData.observe(viewLifecycleOwner, ::showSnackBar)
    }

    private fun setupFab() {
        this.FabOnClickListener().also {
            binding.searchFloatingActionButton.setOnClickListener(it)
            binding.searchTopHeadlinesButton.setOnClickListener(it)
            binding.searchEverythingButton.setOnClickListener(it)
        }
    }

    private fun onEndOfListReached() {
        TODO("Implement next page loading")
    }

    private fun onWatchFullArticleClicked(view: View) {
        TODO("Implement new intent sending")
    }

    private fun showSnackBar(message: String) =
        Snackbar.make(binding.articlesRecyclerView, message, 5000).show()

    private fun animateFab() {
        isFabOpen = !isFabOpen
        if (isFabOpen) {
            val animOpen = AnimationUtils.loadAnimation(context, R.anim.fab_open_animation)
            binding.searchFloatingActionButton.shrink()
            binding.searchFloatingActionButton.setIconResource(R.drawable.ic_baseline_close_24)
            binding.searchTopHeadlinesButton.startAnimation(animOpen)
            binding.searchEverythingButton.startAnimation(animOpen)
            binding.searchTopHeadlinesButton.isClickable = isFabOpen
            binding.searchEverythingButton.isClickable = isFabOpen
        } else {
            val animClose = AnimationUtils.loadAnimation(context, R.anim.fab_close_animation)
            binding.searchFloatingActionButton.extend()
            binding.searchFloatingActionButton.setIconResource(R.drawable.ic_baseline_search_24)
            binding.searchTopHeadlinesButton.startAnimation(animClose)
            binding.searchEverythingButton.startAnimation(animClose)
            binding.searchTopHeadlinesButton.isClickable = isFabOpen
            binding.searchEverythingButton.isClickable = isFabOpen
        }
    }

    inner class FabOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            when (view?.id) {
                binding.searchFloatingActionButton.id -> {
                    animateFab()
                }
                binding.searchTopHeadlinesButton.id -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.activity_main_fragment_container,
                            SearchEverythingFragment.newInstance(),
                            SearchEverythingFragment.TAG
                        )
                        .addToBackStack(SearchEverythingFragment.TAG)
                        .commit()
                }
                binding.searchEverythingButton.id -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.activity_main_fragment_container,
                            SearchTopHeadlinesFragment.newInstance(),
                            SearchTopHeadlinesFragment.TAG
                        )
                        .addToBackStack(SearchTopHeadlinesFragment.TAG)
                        .commit()
                }
            }
        }
    }

    companion object {
        fun newInstance() = ArticlesFragment()
        const val TAG = "ArticlesFragment"
    }
}