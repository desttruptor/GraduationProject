package me.podlesnykh.graduationproject.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.podlesnykh.graduationproject.databinding.FragmentSearchEverythingBinding
import me.podlesnykh.graduationproject.presentation.common.fragments.BaseFragment

class SearchEverythingFragment : BaseFragment() {

    private var _binding: FragmentSearchEverythingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchEverythingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = SearchEverythingFragment()
        const val TAG = "SearchEverythingFragment"
    }
}