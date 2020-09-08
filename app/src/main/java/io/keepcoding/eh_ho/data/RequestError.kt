package io.keepcoding.eh_ho.data

import com.android.volley.VolleyError

class RequestError (
    val volleyError: VolleyError? = null,
    val message: String? = null,
    // Identificador de una cadena de texto que vive en los recursos
    val messageResId: Int? = null // R.string.error_not_found
)