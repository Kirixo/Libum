package com.project.libum.presentation.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.project.libum.R
import com.project.libum.databinding.FragmentSearchBinding
import com.project.libum.presentation.view.custom.BookView
import com.project.libum.presentation.viewmodel.MainActivityModel
import com.project.libum.presentation.viewmodel.SearchViewModel

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val searchViewModel: SearchViewModel by viewModels()
    private val mainActivityModel: MainActivityModel by activityViewModels<MainActivityModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.actionField.listStyleChangerButton.setOnClickListener {
            it.isActivated = !it.isActivated
            searchViewModel.changeBookStyleByActivated(it.isActivated)
        }

        searchViewModel.bookStyle.observe(viewLifecycleOwner){
            changeStateOfBookStyleButton()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun changeStateOfBookStyleButton(){
        when(searchViewModel.bookStyle.value){
            BookView.BookDisplayStyle.WIDE -> setStyleButtonWideIcon()
            BookView.BookDisplayStyle.SLIM ->  setStyleButtonSlimIcon()
            null -> setStyleButtonWideIcon()
        }
    }

    private fun setStyleButtonWideIcon(){
        val drawable = ResourcesCompat.getDrawable(
            requireContext().resources,
            R.drawable.ic_standart_list_24dp,
            requireContext().theme
        )

        binding.actionField.listStyleChangerButton.setImageDrawable(drawable)
    }

    private fun setStyleButtonSlimIcon(){
        val drawable = ResourcesCompat.getDrawable(
            requireContext().resources,
            R.drawable.ic_block_list_24dp,
            requireContext().theme
        )

        binding.actionField.listStyleChangerButton.setImageDrawable(drawable)
    }

}