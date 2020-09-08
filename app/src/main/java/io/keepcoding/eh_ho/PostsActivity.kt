package io.keepcoding.eh_ho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import kotlinx.android.synthetic.main.activity_posts.*

const val EXTRA_TOPIC_ID = "TOPIC_ID"

class PostsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts)


//        if (intent.getStringExtra(EXTRA_TOPIC_ID) != null)
            val topicId: String = intent.getStringExtra(EXTRA_TOPIC_ID) ?:""
            val topic: Topic? = TopicsRepo.getTopic(topicId)

            topic?.let {
                labelTitle.text = it.title
            }

/*
            if(topic != null) {
                labelTitle.text = topic.title
            }
*/
//        labelTitle.text = topic?.title


//        Log.d(this::class.java.canonicalName, intent.getStringExtra("TOPIC_ID"))
    }
}