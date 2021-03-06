package com.codmine.mukellef.presentation.document_screen

import com.codmine.mukellef.domain.model.documents.Document
import com.codmine.mukellef.domain.util.UiText

data class DocumentScreenDataState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val errorStatus: Boolean = false,
    val errorText: UiText? = null,
    val documents: List<Document> = emptyList()
)