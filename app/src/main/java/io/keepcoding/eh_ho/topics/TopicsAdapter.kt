package io.keepcoding.eh_ho.topics

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.keepcoding.eh_ho.R
import io.keepcoding.eh_ho.data.Topic
import io.keepcoding.eh_ho.inflate
import kotlinx.android.synthetic.main.item_topic.view.*
import java.lang.IllegalArgumentException
import java.util.*

class TopicsAdapter(val topicClickListener: ((Topic) -> Unit)? = null) :
    RecyclerView.Adapter<TopicsAdapter.TopicHolder>() {

    private val topics = mutableListOf<Topic>()

    private val listener: ((View) -> Unit) = {
        if (it.tag is Topic) {
            val tag: Topic = it.tag as Topic
            topicClickListener?.invoke(it.tag as Topic)
        } else {
            throw IllegalArgumentException("Topic item view has not a Topic Data as a tag")
        }
    }

    override fun getItemCount(): Int {
//        Log.d(this::class.java.canonicalName, "Get item count")

        return topics.size
    }


    override fun onCreateViewHolder(list: ViewGroup, viewType: Int): TopicHolder {
        val contex = list.context
        val view = list.inflate(R.layout.item_topic)

        return TopicHolder(view)
    }

    override fun onBindViewHolder(holder: TopicHolder, position: Int) {
        val topic = topics[position]
        //     holder.itemView.findViewById<TextView>(R.id.label_topic).setText(topic.title)
        holder.topic = topic
        holder.itemView.setOnClickListener(listener)/*{
            //Log.d(this::class.java.canonicalName, "On Click")
        }*/

//        Log.d(this::class.java.canonicalName, "On bind view")
    }

    /*
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ??? {
        }

        override fun onBindViewHolder(holder: ???, position: Int) {
        }
    */
    fun setTopics(topics: List<Topic>) {
        this.topics.clear()
        this.topics.addAll(topics)
        notifyDataSetChanged()
    }

    inner class TopicHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var topic: Topic? = null
            set(value) {
                field = value
                itemView.tag = field

                field?.let {
                    itemView.labelTitle.text = it.title
                    itemView.labelPosts.text = it.posts.toString()
                    itemView.labelViews.text = it.views.toString()
                    setTimeOffset(it.getTimeOffset())
                }
//          itemView.findViewById<TextView>(R.id.label_topic).text = field?.title
//          itemView.findViewById<TextView>(R.id.label_topic).setText(field?.title)
            }

        private fun setTimeOffset(timeOffset: Topic.TimeOffset) {

            val quantityString = when (timeOffset.unit) {
                Calendar.YEAR -> R.plurals.years
                Calendar.MONTH -> R.plurals.months
                Calendar.DAY_OF_MONTH -> R.plurals.days
                Calendar.HOUR -> R.plurals.hours
                else -> R.plurals.minutes
            }

            if (timeOffset.amount == 0) {
                itemView.labelDate.text =
                    itemView.context.resources.getString(R.string.minutes_zero)
            } else {
                itemView.labelDate.text =
                    itemView.context.resources.getQuantityString(
                        quantityString,
                        timeOffset.amount,
                        timeOffset.amount
                    )
            }
        }
    }

}