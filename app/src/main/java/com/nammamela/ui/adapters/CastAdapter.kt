package com.nammamela.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nammamela.R
import com.nammamela.data.models.CastMember
import com.nammamela.databinding.ItemCastMemberBinding

class CastAdapter : ListAdapter<CastMember, CastAdapter.CastViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = ItemCastMemberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CastViewHolder(private val binding: ItemCastMemberBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cast: CastMember) {
            binding.tvCastName.text = cast.name
            binding.tvCastRole.text = cast.role

            val ctx = binding.root.context
            val resId = ctx.resources.getIdentifier(cast.photoUrl, "drawable", ctx.packageName)
            Glide.with(ctx)
                .load(if (resId != 0) resId else R.drawable.ic_person_placeholder)
                .circleCrop()
                .into(binding.ivCastPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CastMember>() {
            override fun areItemsTheSame(a: CastMember, b: CastMember) = a.id == b.id
            override fun areContentsTheSame(a: CastMember, b: CastMember) = a == b
        }
    }
}
