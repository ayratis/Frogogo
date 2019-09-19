package com.ayratis.frogogo.system

import com.ayratis.frogogo.R
import com.ayratis.frogogo.data.ValidationError
import java.io.IOException
import javax.inject.Inject

class ErrorHandler @Inject constructor(
    private val resourceManager: ResourceManager
) {

    fun proceed(error: Throwable, messageListener: (String?) -> Unit = {}) {
        when (error) {
            is IOException -> messageListener(resourceManager.getString(R.string.network_error))
            is ValidationError -> messageListener(error.localizedMessage)
            else -> messageListener(resourceManager.getString(R.string.unknown_error))
        }
    }
}