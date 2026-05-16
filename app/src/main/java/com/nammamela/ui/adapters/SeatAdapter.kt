package com.nammamela.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nammamela.data.models.Seat
import com.nammamela.data.models.SeatStatus
import com.nammamela.databinding.ItemSeatBinding

class SeatAdapter(private val onSeatClick: (Seat) -> Unit) :
    ListAdapter<Seat, SeatAdapter.SeatViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        val binding = ItemSeatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SeatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SeatViewHolder(private val binding: ItemSeatBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(seat: Seat) {
            binding.tvSeatLabel.text = seat.label

            val bgRes = when (seat.status) {
                SeatStatus.AVAILABLE -> com.nammamela.R.drawable.bg_seat_available
                SeatStatus.RESERVED -> com.nammamela.R.drawable.bg_seat_reserved
                SeatStatus.FRONT_ROW -> com.nammamela.R.drawable.bg_seat_frontrow
            }
            binding.root.setBackgroundResource(bgRes)

            binding.root.setOnClickListener { onSeatClick(seat) }
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Seat>() {
            override fun areItemsTheSame(a: Seat, b: Seat) = a.id == b.id
            override fun areContentsTheSame(a: Seat, b: Seat) = a == b
        }
    }
}
