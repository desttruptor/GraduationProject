package me.podlesnykh.graduationproject.presentation.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.chip.Chip
import me.podlesnykh.graduationproject.R
import me.podlesnykh.graduationproject.databinding.FragmentSourcesDialogBinding
import me.podlesnykh.graduationproject.di.application.AppComponent
import me.podlesnykh.graduationproject.presentation.activities.MainActivity
import me.podlesnykh.graduationproject.presentation.models.SourceModel
import me.podlesnykh.graduationproject.presentation.viewmodels.SourcesDialogFragmentViewModel

class SourcesDialogFragment : DialogFragment() {

    private var _binding: FragmentSourcesDialogBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SourcesDialogFragmentViewModel
    protected lateinit var appComponent: AppComponent

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent = (activity as MainActivity).appComponent
    }

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
            .setMessage(resources.getString(R.string.pick_sources))
            .setPositiveButton("Ok") { dialog, button -> passSources() }
            .setNeutralButton("Fetch sources") { dialog, button -> fetchSources() }
            .setNegativeButton("Close") { dialog, _ -> dialog.dismiss() }
        val inflater = activity?.layoutInflater
        _binding = FragmentSourcesDialogBinding.inflate(inflater!!, null, false)
        viewModel = ViewModelProvider(this)[SourcesDialogFragmentViewModel::class.java]
        viewModel.sourcesListLiveData.observe(viewLifecycleOwner, ::setListOfSources)
        appComponent.inject(viewModel)
        builder.setView(binding.root)
        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListOfSources(list: List<SourceModel>) {
        list.forEach {
            binding.sourcesSelectSourcesChipGroup.addView(
                Chip(context).apply {
                    text = it.name
                }
            )
        }
    }

    private fun fetchSources() {

    }

    private fun passSources() {

    }
}