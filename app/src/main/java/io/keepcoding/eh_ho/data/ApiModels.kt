package io.keepcoding.eh_ho.data

import org.json.JSONObject

data class SignInModel(val username: String, val password: String)

data class SignUpModel(
    val username: String,
    val email: String,
    val password: String
){
    fun toJson(): JSONObject {
        return JSONObject() // "{}"
            .put("name", username) // {"name": ""username}
            .put("username", username)
            .put("email", email)
            .put("password", password)
            .put("active", true)
            .put("approved", true)
    }
}

data class CreateTopicModel (
    var title: String,
    var content: String
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("title", title)
            .put("raw", content)
    }
}

data class CreatePostModel (
    var topic_id: Integer,
    var content: String
) {
    fun toJson(): JSONObject {
        return JSONObject()
            .put("topic_id", topic_id)
            .put("raw", content)
    }
}