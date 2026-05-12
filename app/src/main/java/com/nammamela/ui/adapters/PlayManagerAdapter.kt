package com.nammamela.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamela.data.models.Play
import com.nammamela.databinding.ItemPlayManagerBinding

class PlayManagerAdapter(
    private val onSetActive: (Play) -> Unit,
    private val onDelete: (Play) -> Unit
) : ListAdapter<Play, PlayManagerAdapter.PlayViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayViewHolder {
        val binding = ItemPlayManagerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlayViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlayViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class PlayViewHolder(private val binding: ItemPlayManagerBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(play: Play) {
            binding.tvPlayTitle.text = play.title
            binding.tvPlayMeta.text = "${play.showDate} • ${play.showTime} • ${play.venue}"
            binding.tvActiveStatus.text = if (play.isActive) "🟢 Tonight's Play" else "⚪ Scheduled"

            binding.btnSetTonightsPlay.setOnClickListener { onSetActive(play) }
            binding.btnDeletePlay.setOnClickListener { onDelete(play) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Play>() {
            override fun areItemsTheSame(a: Play, b: Play) = a.id == b.id
            override fun areContentsTheSame(a: Play, b: Play) = a == b
        }
    }
}
