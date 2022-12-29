package com.annti.binlistapp.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.annti.binlistapp.R
import com.annti.binlistapp.databinding.FragmentMainBinding
import com.annti.binlistapp.entity.CardInfoEntity
import com.annti.binlistapp.presentation.main.MainFragmentDirections.Companion.actionMainFragmentToInfoFragment
import com.annti.binlistapp.presentation.main.adapter.CardInfoAdapter
import com.annti.binlistapp.presentation.main.adapter.ItemSelected
import com.annti.binlistapp.utils.LoadState
import com.annti.binlistapp.utils.textChangedFlow
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main), ItemSelected {

    private lateinit var binding: FragmentMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private val cardAdapter = CardInfoAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRecyclerView()
        observeLoadState()
        observeCardNumberError()
        getResetCard()
        searchCard()
    }

    private fun searchCard() {
        viewLifecycleOwner.lifecycleScope.launch {
            binding.txtEditNumber.textChangedFlow()
                .collectLatest { numberCard ->
                    viewModel.getCardInfo(numberCard)
                }
        }
    }

    private fun getResetCard() {
        viewModel.getResetCard()
        lifecycleScope.launchWhenStarted {
            viewModel.card.collect {
                cardAdapter.submitList(it)
            }
        }
    }

    private fun setRecyclerView() {
        with(binding.listCard) {
            adapter = cardAdapter
            setHasFixedSize(false)
        }
    }

    private fun observeLoadState() {
        lifecycleScope.launchWhenStarted {
            viewModel.loadState.observe(viewLifecycleOwner){ state ->
                when (state) {
                    LoadState.LOADING -> {
                        binding.progressBar.isVisible = true
                        binding.txtInputNumber.isVisible = false
                    }
                    LoadState.ERROR -> {
                        binding.progressBar.isVisible = false
                        binding.txtInputNumber.isVisible = true
                        hideKeyboard(binding.txtEditNumber)
                        binding.txtEditNumber.text?.clear()
                    }
                    LoadState.SUCCESS -> {
                        binding.progressBar.isVisible = false
                        binding.txtInputNumber.isVisible = true
                        binding.txtEditNumber.text?.clear()
                        hideKeyboard(binding.txtEditNumber)
                    }
                    else -> {
                        //do nothing
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope
            .launchWhenStarted {
                viewModel.error
                    .collect {
                        Snackbar.make(
                            binding.root,
                            it,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
            }
    }

    private fun hideKeyboard(view: View) {
        val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun observeCardNumberError() {
        viewModel.cardNumberErrorLiveData.observe(viewLifecycleOwner) { loginError ->
            when (loginError) {
                CardNumberError.EMPTY -> {
                    binding.txtInputNumber.error = getString(R.string.card_number_empty_error)
                }
                CardNumberError.NOT_VALID -> {
                    binding.txtInputNumber.error = getString(R.string.card_number_not_valid_error)
                }
                CardNumberError.VALID -> {
                    binding.txtInputNumber.error = null
                }
                else -> {
                    //do nothing
                }
            }
        }
    }

    override fun navigateToDetails(card: CardInfoEntity) {
        val action =
            actionMainFragmentToInfoFragment(card)
        findNavController().navigate(action)
    }
}