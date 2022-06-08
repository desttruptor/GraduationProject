package me.podlesnykh.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import me.podlesnykh.graduationproject.databinding.FragmentSearchTopHeadlinesBinding
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment

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
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupToolbar() {
        (activity as AppCompatActivity).setSupportActionBar(binding.searchToolbar)
    }

    companion object {
        fun newInstance() = SearchTopHeadlinesFragment()
        const val TAG = "SearchTopHeadlinesFragment"
    }
}