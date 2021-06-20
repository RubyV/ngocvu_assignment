package com.ngocvu.example.view.ui.character

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.ngocvu.example.R
import com.ngocvu.example.databinding.FragmentCharacterBinding
import com.ngocvu.example.view.adapter.CharacterAdapter
import com.ngocvu.example.view.state.ViewState
import com.ngocvu.example.viewmodel.CharacterViewModel


import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CharacterFragment : Fragment() {

    private lateinit var binding: FragmentCharacterBinding
    private val characterAdapter by lazy { CharacterAdapter() }
    private val viewModel by viewModels<CharacterViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.charactersRv.adapter = characterAdapter
        viewModel.queryCharactersList()
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.charactersList.observe(viewLifecycleOwner) { response ->
            when (response) {
                is ViewState.Loading -> {
                    binding.charactersRv.visibility = View.GONE
                    binding.charactersFetchProgress.visibility = View.VISIBLE
                }
                is ViewState.Success -> {
                    if (response.value?.data?.characters?.results?.size == 0) {
                        characterAdapter.submitList(emptyList())
                        binding.charactersFetchProgress.visibility = View.GONE
                        binding.charactersRv.visibility = View.GONE
                        binding.charactersEmptyText.visibility = View.VISIBLE
                    } else {
                        binding.charactersRv.visibility = View.VISIBLE
                        binding.charactersEmptyText.visibility = View.GONE
                    }
                    val results = response.value?.data?.characters?.results
                    characterAdapter.submitList(results)
                    binding.charactersFetchProgress.visibility = View.GONE
                }
                is ViewState.Error -> {
                    characterAdapter.submitList(emptyList())
                    binding.charactersFetchProgress.visibility = View.GONE
                    binding.charactersRv.visibility = View.GONE
                    binding.charactersEmptyText.visibility = View.VISIBLE
                }
            }
        }
    }

}