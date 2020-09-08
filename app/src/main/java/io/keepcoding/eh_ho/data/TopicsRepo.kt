package io.keepcoding.eh_ho.data

import android.content.Context
import com.android.volley.NetworkError
import com.android.volley.Request
import com.android.volley.ServerError
import com.android.volley.toolbox.JsonObjectRequest
import io.keepcoding.eh_ho.R
import org.json.JSONObject

object TopicsRepo {
    val topics: MutableList<Topic> = mutableListOf()

    fun getTopics(
        context: Context,
        onSuccess: (List<Topic>) -> Unit,
        onError: (RequestError) -> Unit
    ){
        val request = JsonObjectRequest(
            Request.Method.GET,
            ApiRoutes.getTopics(),
            null,
            {
                val list = Topic.parseTopicsList(it)
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
/*    get() {
        if(field.isEmpty())
            field.addAll(createDummyTopics())
        return field
    }
*/

    fun addTopic(
        context: Context,
        model: CreateTopicModel,
        onSuccess: (CreateTopicModel) -> Unit,
        onError: (RequestError) -> Unit
    ){
        val username = UserRepo.getUsername(context)
        val request = PostRequest(
            Request.Method.POST,
            ApiRoutes.createTopic(),
            model.toJson(),
            username,
            {
                onSuccess(model)
            },
            {
                it.printStackTrace()

                val requestError =
                    if (it is ServerError && it.networkResponse.statusCode == 422) {
                        val body = String(it.networkResponse.data, Charsets.UTF_8)
                        val jsonError = JSONObject(body)
                        val errors = jsonError.getJSONArray("errors")
                        var errorMessage = ""

                        for (i in 0 until errors.length()) {
                            errorMessage += "${errors[i]}"
                        }

                        RequestError(it, message = errorMessage)
                    }
//                        RequestError(it, messageResId =  R.string.error_duplicated_topic)
                    else if (it is NetworkError)
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

    fun getTopic(id: String): Topic? = topics.find { it.id == id }

    fun addTopic(title: String, content: String) {
        topics.add(
            Topic(
                title = title
//                content = content
            )
        )
    }

/*    fun createDummyTopics(count: Int = 20): List<Topic> =
        (0..count).map {
            Topic(
                title = "Title $it"
//                content = "Content $it"
            )
        }
 */
/*
    fun createDummyTopics(count: Int = 10): List<Topic> {
    /*
        val dummies = mutableListOf<Topic>()

        for (i in 0..count) {
            val topic = Topic(
                title = "Topic $i",
                content = "Content $i"
            )
            dummies.add(topic)
        }

        return dummies
     */

        return (0..count).map {
            Topic(
                title = "Title $it",
                content = "Content $it"
            )
        }

    }
 */
}