package com.nammamela.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamela.data.models.Applause
import com.nammamela.databinding.ItemApplauseBinding
import java.text.SimpleDateFormat
import java.util.*

class ApplauseAdapter : ListAdapter<Applause, ApplauseAdapter.ApplauseViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApplauseViewHolder {
        val binding = ItemApplauseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ApplauseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApplauseViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ApplauseViewHolder(private val binding: ItemApplauseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(applause: Applause) {
            binding.tvFanName.text = "${applause.emoji} ${applause.fanName}"
            binding.tvMessage.text = applause.message
            val sdf = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
            binding.tvPostedAt.text = sdf.format(Date(applause.postedAt))
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Applause>() {
            override fun areItemsTheSame(a: Applause, b: Applause) = a.id == b.id
            override fun areContentsTheSame(a: Applause, b: Applause) = a == b
        }
    }
}
