package io.keepcoding.eh_ho.posts

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Post
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.inflate
import io.keepcoding.eh_ho.topics.TopicsAdapter
import kotlinx.android.synthetic.main.item_post.view.*

class PostsAdapter():  RecyclerView.Adapter<PostsAdapter.PostHolder>() {

    private val posts = mutableListOf<Post>()

    override fun getItemCount(): Int {

        return posts.size
    }

    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): PostHolder {
        val contex = list.context
        val view = list.inflate(R.layout.item_post)

        return PostHolder(view)
    }

    override fun onBindViewHolder(holder: PostHolder, position: Int) {
        val post = posts[position]
        holder.post = post
    }

    fun setPosts(posts: List<Post>) {
        this.posts.clear()
        this.posts.addAll(posts)
        notifyDataSetChanged()
    }


    inner class PostHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var post: Post? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.labelAuthor.text = it.author
                    itemView.labelDatePost.text = it.date.toString()
                    itemView.labelContent.text = it.content
                }
            }
    }
}