package io.keepcoding.eh_ho.posts

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.PostsRepo
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.data.TopicsRepo
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.topics.TopicsAdapter
import io.keepcoding.eh_ho.topics.TopicsFragment
import kotlinx.android.synthetic.main.fragment_posts.*
import kotlinx.android.synthetic.main.fragment_topics.*
import java.lang.IllegalArgumentException

class PostsFragment : Fragment() {

    var postsInteractionListener: PostsInteractionListener? = null

    val postsAdapter: PostsAdapter by lazy {
        val adapter = PostsAdapter()
        adapter
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is PostsInteractionListener)
            postsInteractionListener = context
        else
            throw IllegalArgumentException("Context doesn't implement ${PostsInteractionListener::class.java.canonicalName}")

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return container?.inflate(R.layout.fragment_posts)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postsAdapter.setPosts(PostsRepo.posts)

        listPosts.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        listPosts.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        listPosts.adapter = postsAdapter

    }

    override fun onResume() {
        super.onResume()
        this.postsInteractionListener?.loadPosts(this.postsAdapter)
    }

    override fun onDetach() {
        super.onDetach()
        postsInteractionListener = null
    }

    interface PostsInteractionListener {
        fun loadPosts(postsAdapter: PostsAdapter)
    }

}