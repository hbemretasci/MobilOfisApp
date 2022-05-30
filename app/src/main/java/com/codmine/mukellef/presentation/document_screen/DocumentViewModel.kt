package com.codmine.mukellef.presentation.document_screen

import android.content.Context
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codmine.mukellef.data.local.AppSettings
import com.codmine.mukellef.domain.model.documents.Document
import com.codmine.mukellef.domain.use_case.document_screen.GetDocuments
import com.codmine.mukellef.domain.use_case.document_screen.PostDocumentReadingInfo
import com.codmine.mukellef.domain.use_case.splash_screen.GetUserLoginData
import com.codmine.mukellef.domain.util.Resource
import com.codmine.mukellef.domain.util.downloadFile
import com.codmine.mukellef.domain.util.fileExist
import com.codmine.mukellef.domain.util.showFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DocumentViewModel @Inject constructor(
    private val getDocuments: GetDocuments,
    private val postDocumentReadingInfo: PostDocumentReadingInfo,
    private val getUserLoginData: GetUserLoginData
): ViewModel() {

    private val _dataState = mutableStateOf(DocumentScreenDataState())
    val dataState: State<DocumentScreenDataState> = _dataState

    private val _readingDocumentState = mutableStateOf(ReadingDocumentState())
    val readingDocumentState: State<ReadingDocumentState> = _readingDocumentState

    private val _appSettings = mutableStateOf(AppSettings())

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun onEvent(event: DocumentEvent, context: Context) {
        when(event) {
            is DocumentEvent.LoadData -> {
                getAppSettings(context)
                getDocumentList()
            }
            is DocumentEvent.LoadDocuments -> {
                getDocumentList()
            }
            is DocumentEvent.Refresh -> {
                refresh()
            }
            is DocumentEvent.ShowAndReadDocument -> {
                if (event.document.readingTime.isEmpty()) {
                    postReadingInfo(event.document)
                }

                if (event.document.documentName.isNotEmpty()) {
                    showDocument(_appSettings.value.gib,event.document, context)
                }

                refresh()

            }
        }
    }

    private fun showDocument(gib: String, document: Document, context: Context) {
        if (!fileExist(document.documentName, context)) {
            downloadFile(gib, document.documentName, context)
        } else {
            showFile(document.documentName, context)
        }
    }

    private fun getDocumentList() {
        getDocuments(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, _appSettings.value.user, _appSettings.value.accountant
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _dataState.value = DocumentScreenDataState(documents = result.data ?: emptyList() )
                }
                is Resource.Error -> {
                    _dataState.value = DocumentScreenDataState(error = result.message ?: "Beklenmeyen hata.")
                }
                is Resource.Loading -> {
                    _dataState.value = DocumentScreenDataState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun postReadingInfo(document: Document) {
        postDocumentReadingInfo(
            _appSettings.value.gib, _appSettings.value.vk, _appSettings.value.password, document.id
        ).onEach { result ->
            when(result) {
                is Resource.Success -> {
                    _readingDocumentState.value = ReadingDocumentState(readingDocument = result.data)
                }
                is Resource.Error -> {
                    _readingDocumentState.value = ReadingDocumentState(error = result.message ?: "Beklenmeyen hata.")
                }
                is Resource.Loading -> {
                    _readingDocumentState.value = ReadingDocumentState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun refresh() {
        viewModelScope.launch {
            _isRefreshing.emit(true)
            getDocumentList()
            _isRefreshing.emit(false)
        }
    }

    private fun getAppSettings(context: Context) {
        getUserLoginData(context).onEach { result ->
            _appSettings.value = result
        }.launchIn(viewModelScope)
    }

}