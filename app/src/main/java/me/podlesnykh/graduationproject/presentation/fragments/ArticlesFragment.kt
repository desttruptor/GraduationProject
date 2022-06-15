package me.podlesnykh.graduationproject.presentation.fragments

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
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
import me.podlesnykh.graduationproject.presentation.models.ArticleModel
import me.podlesnykh.graduationproject.presentation.models.EverythingSearchSettingsModel
import me.podlesnykh.graduationproject.presentation.models.TopHeadlinesSearchSettingsModel
import me.podlesnykh.graduationproject.presentation.viewmodels.ArticlesViewModel

class ArticlesFragment : BaseFragment() {

    private lateinit var viewModel: ArticlesViewModel

    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private var isFabOpen = false
    private var everythingSearchModel: EverythingSearchSettingsModel? = null
    private var topHeadlinesSearchModel: TopHeadlinesSearchSettingsModel? = null
    private val displayedArticlesList = mutableListOf<ArticleModel>()

    private val articlesListAdapter = ArticlesListAdapter(::onWatchFullArticleClicked).apply {
        stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.ALLOW
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        setupToolbar(R.string.everything)
        setupRecyclerView()
        setupFab()

        setupViewModel()
        appComponent.inject(viewModel)

        setupArgumentsAndLoadNews()
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        isFabOpen = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupArgumentsAndLoadNews() {
        val everythingArguments = arguments?.getSerializable(EVERYTHING_SETTINGS_MODEL_KEY)
        val topHeadlinesArguments = arguments?.getSerializable(TOP_HEADLINES_MODEL_KEY)
        if (everythingArguments != null) {
            everythingSearchModel = everythingArguments as? EverythingSearchSettingsModel
                ?: error("Error cast everything search model")
            setupToolbar(R.string.everything)
            viewModel.getEverythingArticlesForce(everythingSearchModel!!)
        } else if (topHeadlinesArguments != null) {
            topHeadlinesSearchModel = topHeadlinesArguments as? TopHeadlinesSearchSettingsModel
                ?: error("Error cast top headlines search model")
            setupToolbar(R.string.top_headlines)
            viewModel.getTopHeadlinesArticlesForce(topHeadlinesSearchModel!!)
        } else {
            viewModel.getEverythingArticlesFromDb()
        }
    }

    private fun setupToolbar(titleRes: Int) {
        binding.articlesToolbar.title = context?.getString(titleRes)
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
        viewModel.showProgressLiveData.observe(viewLifecycleOwner, ::showProgress)
        viewModel.articlesListLiveData.observe(viewLifecycleOwner, ::onDataLoaded)
        viewModel.errorSnackBarLiveData.observe(viewLifecycleOwner, ::showSnackBar)
        viewModel.errorDialogLiveData.observe(viewLifecycleOwner, ::showErrorDialog)
        viewModel.nothingFoundNotification.observe(viewLifecycleOwner, ::showErrorDialog)
    }

    private fun setupFab() {
        this.FabOnClickListener().also {
            binding.searchFloatingActionButton.setOnClickListener(it)
            binding.searchTopHeadlinesButton.setOnClickListener(it)
            binding.searchEverythingButton.setOnClickListener(it)
        }
    }

    private fun onEndOfListReached() {
        if (arguments != null) {
            Log.v("endOfList", "End of list reached")
            if (everythingSearchModel != null) {
                viewModel.getEverythingArticlesForceNextPage(everythingSearchModel!!)
            } else {
                viewModel.getTopHeadlinesArticlesForceNextPage(topHeadlinesSearchModel!!)
            }
        }
    }

    private fun onWatchFullArticleClicked(view: View, url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        }
        startActivity(intent)
    }

    private fun showSnackBar(message: String) =
        Snackbar.make(binding.articlesRecyclerView, message, 5000).show()

    private fun showErrorDialog(message: String) =
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.server_error)
            .setMessage(message)
            .setPositiveButton("Ok") { dialogInterface, _ -> dialogInterface.dismiss() }
            .create()
            .show()

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

    private fun onDataLoaded(data: List<ArticleModel>) {
        displayedArticlesList.addAll(data)
        articlesListAdapter.submitList(displayedArticlesList)
    }

    private fun showProgress(show: Boolean) {
        binding.articlesProgressBar.visibility = if (show) View.VISIBLE else View.GONE
    }

    inner class FabOnClickListener : View.OnClickListener {
        override fun onClick(view: View?) {
            when (view?.id) {
                binding.searchFloatingActionButton.id -> animateFab()
                binding.searchEverythingButton.id -> openSearchQueryFragment(
                    SearchEverythingFragment.newInstance()
                )
                binding.searchTopHeadlinesButton.id -> openSearchQueryFragment(
                    SearchTopHeadlinesFragment.newInstance()
                )
            }
        }

        private fun openSearchQueryFragment(fragmentInstance: BaseFragment) {
            parentFragmentManager.beginTransaction()
                .replace(
                    R.id.activity_main_fragment_container,
                    fragmentInstance
                )
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance() = ArticlesFragment()

        fun newInstance(model: EverythingSearchSettingsModel): ArticlesFragment {
            val bundle = Bundle()
            bundle.putSerializable(EVERYTHING_SETTINGS_MODEL_KEY, model)
            val fragment = ArticlesFragment()
            fragment.arguments = bundle
            return fragment
        }

        fun newInstance(model: TopHeadlinesSearchSettingsModel): ArticlesFragment {
            val bundle = Bundle()
            bundle.putSerializable(TOP_HEADLINES_MODEL_KEY, model)
            val fragment = ArticlesFragment()
            fragment.arguments = bundle
            return fragment
        }

        private const val EVERYTHING_SETTINGS_MODEL_KEY = "settingsModel"
        private const val TOP_HEADLINES_MODEL_KEY = "topHeadlinesModel"

        const val TAG = "articlesFragment"
    }
}