package com.annti.binlistapp.presentation.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.annti.binlistapp.domain.CardUseCase
import com.annti.binlistapp.entity.CardInfoEntity
import com.annti.binlistapp.utils.LoadState
import com.annti.binlistapp.utils.Request
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val cardUseCase: CardUseCase
): ViewModel(){

    val loadState = MutableLiveData<LoadState>()

    private val _card = MutableStateFlow<List<CardInfoEntity>>(emptyList())
    val card: StateFlow<List<CardInfoEntity>> = _card

    val cardNumberErrorLiveData = MutableLiveData<CardNumberError>()

    private val inputValidator by lazy {
        InputValidator(cardNumberErrorLiveData)
    }

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()

    fun getCardInfo(numberCard: String){
        if (inputValidator.isFieldValid(numberCard)){
            viewModelScope.launch(Dispatchers.IO) {
                cardUseCase.getCardInfo(numberCard).collect { requestState ->
                    when (requestState) {
                        is Request.Loading -> {
                            loadState.postValue(LoadState.LOADING)
                        }
                        is Request.Success -> {
                            loadState.postValue(LoadState.SUCCESS)
                        }
                        is Request.Error -> {
                            loadState.postValue(LoadState.ERROR)
                            _error.send(requestState.message)
                        }
                    }
                }
            }
        }
    }

    fun getResetCard(){
        cardUseCase.getResetCard()
            .onEach {
                _card.value = it
            }
            .launchIn(viewModelScope)
    }

    fun delete(card:CardInfoEntity){
        viewModelScope.launch {
            cardUseCase.delete(card)
        }

    }

}