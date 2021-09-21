package com.example.birthdayreminder

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_tile.view.*
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class PersonAdapter(
    private val friends: List<Person>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<PersonAdapter.PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.user_tile, parent, false)
        return PersonViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val currentPerson = friends[position]
//        if (context != null) {
//            Glide.with(context).load("https://picsum.photos/62").centerCrop().placeholder(R.drawable.ic_account2).into(holder.displayImage)
//        }

        holder.displayImage.setImageResource(currentPerson.imageResource)
        holder.name.text = currentPerson.name

        holder.date.text = when (currentPerson.date.dayOfMonth) {
            1 or 21 -> "${currentPerson.date.dayOfMonth}st " + currentPerson.date.month.toString()
                .substring(0, 3)
            2 or 22 -> "${currentPerson.date.dayOfMonth}nd " + currentPerson.date.month.toString()
                .substring(0, 3)
            3 or 23 -> "${currentPerson.date.dayOfMonth}rd " + currentPerson.date.month.toString()
                .substring(0, 3)
            else -> "${currentPerson.date.dayOfMonth}th " + currentPerson.date.month.toString()
                .substring(0, 3)
        }

        var diffInDays: Long = ChronoUnit.DAYS.between(LocalDate.now(), currentPerson.date)
        while (diffInDays < 0) diffInDays += 365
        holder.daysLeft.text = getDays(diffInDays)
    }

    override fun getItemCount() = friends.size

    inner class PersonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val displayImage: ImageView = itemView.iv_display_picture
        val name: TextView = itemView.tv_name
        val daysLeft: TextView = itemView.tv_days_left
        val date: TextView = itemView.tv_date
        val notify: ImageView = itemView.iv_notify

        init {
            itemView.setOnClickListener(this)
//            itemView.iv_notify.setOnClickListener {
//                if() notify.setImageResource()
//            }
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) listener.onItemClick(position)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private fun getDays(diffInDays: Long) = when (diffInDays) {
        in 0 until 10 -> "  $diffInDays days left"
        in 10 until 100 -> " $diffInDays days left"
        else -> "$diffInDays days left"
    }
}