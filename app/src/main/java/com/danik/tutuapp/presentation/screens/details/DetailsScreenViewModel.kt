package com.danik.tutuapp.presentation.screens.details

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danik.tutuapp.domain.model.Train
import com.danik.tutuapp.domain.use_cases.UseCases
import com.danik.tutuapp.util.Constants.TRAIN_ID_ARGUMENT_KEY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val useCases: UseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _selectedTrain = MutableStateFlow<Train?>(null)
    val selectedTrain: StateFlow<Train?> = _selectedTrain

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val trainId = savedStateHandle.get<Int>(TRAIN_ID_ARGUMENT_KEY)
            _selectedTrain.value = trainId?.let {
                useCases.getSelectedTrainUseCase(trainId = trainId)
            }

        }

    }

    private val _uiEvent = MutableSharedFlow<UiState>()
    val uiEvent: SharedFlow<UiState> = _uiEvent.asSharedFlow()

    private val _colorPalette = mutableStateOf<Map<String, String>>(mapOf())
    val colorPalette: State<Map<String, String>> = _colorPalette

    fun setPalette(colors: Map<String, String>) {
        _colorPalette.value = colors
    }

    fun startUiEvent() {
        viewModelScope.launch {
            _uiEvent.emit(UiState.GenerateColorPalette)
        }
    }

}


sealed class UiState() {
    object GenerateColorPalette : UiState()
}