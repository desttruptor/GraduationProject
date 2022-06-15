package me.podlesnykh.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.htmlEncode
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentSearchEverythingBinding
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment
import me.podlesnykh.graduationproject.presentation.common.utils.NewsApiUtils
import me.podlesnykh.graduationproject.presentation.models.EverythingSearchSettingsModel
import java.text.SimpleDateFormat
import java.util.*

class SearchEverythingFragment : BaseFragment() {
    private var _binding: FragmentSearchEverythingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchEverythingBinding.inflate(inflater, container, false)
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
        val languagesArray = resources.getStringArray(R.array.languages)
        val languagesArrayAdapter =
            ArrayAdapter(requireContext(), R.layout.search_dropdown_list_item, languagesArray)
        binding.searchLanguageDropDownTextView.setAdapter(languagesArrayAdapter) // результаты при поиске получать из этого текствью
        binding.searchDateFromEditText.setOnClickListener(::onCreateDatePicker)
        binding.searchDateToEditText.setOnClickListener(::onCreateDatePicker)
        binding.launchSearchButton.setOnClickListener(::onSearchButtonPressed)
    }

    private fun onSearchButtonPressed(view: View) {
        val queries = binding.searchQueriesTextInput.editText?.text.toString()
        val searchIn = binding.searchInChipGroup.checkedChipIds.map {
            when (it) {
                binding.titleChip.id -> true
                binding.descriptionChip.id -> true
                binding.contentChip.id -> true
                else -> false
            }
        }.let { NewsApiUtils.searchIn(it) }
        val domainsInc =
            binding.searchDomainsTextInput.editText?.text
                ?.replace(Regex(" "), "")
                .toString()
        val domainsExc =
            binding.searchExcludeDomainsTextInput.editText?.text
                ?.replace(Regex(" "), "")
                .toString()
        val fromDate = binding.searchDateFromTextInput.editText?.text.toString()
        val toDate = binding.searchDateToTextInput.editText?.text.toString()
        val language = binding.searchLanguageDropDownTextView.text.toString()
        val sortBy = when (binding.searchSortByChipGroup.checkedChipId) {
            binding.relevancyChip.id -> resources.getString(R.string.relevancy).lowercase()
            binding.popularityChip.id -> resources.getString(R.string.popularity).lowercase()
            binding.publishedAtChip.id -> resources.getString(R.string.publishedat).lowercase()
            else -> resources.getString(R.string.publishedat).lowercase()
        }

        val searchModel = EverythingSearchSettingsModel(
            keyword = queries.ifEmpty { null }?.htmlEncode(),
            searchIn = searchIn.ifEmpty { null },
            sources = null,
            domains = domainsInc.ifEmpty { null },
            excludeDomains = domainsExc.ifEmpty { null },
            from = fromDate.ifEmpty { null },
            to = toDate.ifEmpty { null },
            language = language.ifEmpty { null },
            sortBy = sortBy.ifEmpty { null },
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

    private fun onCreateDatePicker(view: View) {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .setTitleText(getString(R.string.select_date))
            .setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            .build()
        datePicker.addOnPositiveButtonClickListener {
            val date = Date(it)
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            val formattedDate = formatter.format(date)
            when (view) {
                binding.searchDateFromEditText -> binding.searchDateFromEditText.setText(
                    formattedDate,
                    TextView.BufferType.EDITABLE
                )
                binding.searchDateToEditText -> binding.searchDateToEditText.setText(
                    formattedDate,
                    TextView.BufferType.EDITABLE
                )
            }
        }
        datePicker.show(parentFragmentManager, null)
    }

    companion object {
        fun newInstance() = SearchEverythingFragment()
    }
}