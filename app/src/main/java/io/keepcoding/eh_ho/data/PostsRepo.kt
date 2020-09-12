package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R

object PostsRepo {
    val posts: MutableList<Post> = mutableListOf()

    fun getPosts(
        context: Context,
        topicId: String,
        onSuccess: (List<Post>) -> Unit,
        onError: (RequestError) -> Unit
    ){

        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getPosts(topicId),
            null,
            {
                val list = Post.parsePostsList(it)
                onSuccess(list)
            },
            {
                it.printStackTrace()
                val requestError =
                    if (it is NetworkError)
                        RequestError(it, messageResId = R.string.error_not_internet)
                    else
                        RequestError(it)
                onError(requestError)
            }
        )

        ApiRequestQueue
            .getRequestQueue(context)
            .add(request)

    }

}