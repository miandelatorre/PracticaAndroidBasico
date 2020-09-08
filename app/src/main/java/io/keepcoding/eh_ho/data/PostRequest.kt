package io.keepcoding.eh_ho.data

import io.keepcoding.eh_ho.BuildConfig
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class PostRequest(
    method: Int,
    url: String,
    body: JSONObject?,
    private val username: String? = null,
    listener: (response: JSONObject?) -> Unit,
    errorListener: (errorResponse: VolleyError) -> Unit
    ) : JsonObjectRequest(
        method, url, body, listener, errorListener
) {
    override fun getHeaders(): MutableMap<String, String> {
        val headers = mutableMapOf<String, String>()
        headers["Content-Type"] = "application/json"
        headers["Accept"] = "application/json"
        headers["Api-Key"] = BuildConfig.DiscourseApiKey

/*        if (username != null)
            headers["Api-Username"] = username
*/

        username?.let {
            headers["Api-Username"] = it
        }
        
        return headers
    }
}