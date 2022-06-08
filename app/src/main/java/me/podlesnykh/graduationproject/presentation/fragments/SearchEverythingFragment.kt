package me.podlesnykh.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentSearchEverythingBinding
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment
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
        binding.searchDateFromTextInput.setOnClickListener(::onCreateDatePicker)
        binding.searchDateToTextInput.setOnClickListener(::onCreateDatePicker)
        binding.searchDateFromTextInput.editText?.setOnClickListener(::onCreateDatePicker)
        binding.searchDateToTextInput.editText?.setOnClickListener(::onCreateDatePicker)
        binding.launchSearchButton.setOnClickListener(::onSearchButtonPressed)
    }

    private fun onSearchButtonPressed(view: View) {

    }

    private fun onCreateDatePicker(view: View) {
        val datePicker = MaterialDatePicker.Builder.datePicker().apply {
            setTitleText(getString(R.string.select_date))
            setInputMode(MaterialDatePicker.INPUT_MODE_TEXT)
            setSelection(MaterialDatePicker.todayInUtcMilliseconds())
        }.build()
        datePicker.apply {
            show(this@SearchEverythingFragment.requireActivity().supportFragmentManager, null)
            addOnPositiveButtonClickListener {
                val date = Date(it)
                val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }
                val formattedDate = formatter.format(date)

                when (view) {
                    binding.searchDateFromTextInput -> {
                        binding.searchDateFromTextInput.editText?.setText(
                            formattedDate,
                            TextView.BufferType.EDITABLE
                        )
                    }
                    binding.searchDateToTextInput -> {
                        binding.searchDateToTextInput.editText?.setText(
                            formattedDate,
                            TextView.BufferType.EDITABLE
                        )
                    }
                }
            }
        }
    }

    private fun insertDateToCaller(view: View, utcTime: Long) {

    }

    companion object {
        fun newInstance() = SearchEverythingFragment()
    }
}