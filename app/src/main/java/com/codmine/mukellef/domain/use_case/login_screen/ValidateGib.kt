package com.codmine.mukellef.domain.use_case.login_screen

import android.content.Context
import com.codmine.mukellef.R
import com.codmine.mukellef.domain.use_case.ValidationResult
import com.codmine.mukellef.domain.util.Constants.MAX_GIB_LENGTH
import com.codmine.mukellef.presentation.util.UiText
import javax.inject.Inject

class ValidateGib @Inject constructor() {

    fun execute(gib: String, context: Context): ValidationResult {
        if (gib.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(R.string.gib_blank).asString(context)
            )
        }
        if (gib.length >  MAX_GIB_LENGTH) {
            return ValidationResult(
                successful = false,
                errorMessage = UiText.StringResources(
                    R.string.max_gib_len_error,
                    MAX_GIB_LENGTH
                ).asString(context)
            )
        }
        return ValidationResult(
            successful = true
        )
    }
}