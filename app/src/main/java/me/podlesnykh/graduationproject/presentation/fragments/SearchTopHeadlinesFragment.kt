package me.podlesnykh.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentSearchTopHeadlinesBinding
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment
import me.podlesnykh.graduationproject.presentation.common.utils.NewsApiUtils.encodeUrl
import me.podlesnykh.graduationproject.presentation.models.TopHeadlinesSearchSettingsModel

class SearchTopHeadlinesFragment : BaseFragment() {
    private var _binding: FragmentSearchTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchTopHeadlinesBinding.inflate(inflater, container, false)
        setupToolbar()
        setupControls()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.searchToolbar)
        binding.searchToolbar.setNavigationOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    private fun setupControls() {
        val countriesList = resources.getStringArray(R.array.countries)
        val countriesListAdapter =
            ArrayAdapter(requireContext(), R.layout.search_dropdown_list_item, countriesList)
        binding.searchCountryTextView.setAdapter(countriesListAdapter)
        val categoriesList = resources.getStringArray(R.array.categories)
        val categoriesListAdapter =
            ArrayAdapter(requireContext(), R.layout.search_dropdown_list_item, categoriesList)
        binding.searchCategoryTextView.setAdapter(categoriesListAdapter)
        binding.launchSearchButton.setOnClickListener(::onSearchButtonPressed)
    }

    private fun onSearchButtonPressed(view: View) {
        val keywords = binding.searchQueriesTextInput.editText?.text.toString()
        val country = binding.searchCountryDropDownMenu.editText?.text.toString()
        val category = binding.searchCategoryDropDownMenu.editText?.text.toString()

        val searchModel = TopHeadlinesSearchSettingsModel(
            country = country.ifEmpty { null },
            category = category.ifEmpty { null },
            sources = null,
            q = keywords.ifEmpty { null }?.encodeUrl(),
            pageSize = null,
            page = null
        )

        parentFragmentManager.popBackStack()
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.activity_main_fragment_container,
                ArticlesFragment.newInstance(searchModel),
                ArticlesFragment.TAG
            )
            .commit()
    }

    companion object {
        fun newInstance() = SearchTopHeadlinesFragment()
    }
}