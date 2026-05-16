package com.nammamela.ui.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammamela.data.models.CastMember
import com.nammamela.data.models.Play
import com.nammamela.databinding.ActivityManagerBinding
import com.nammamela.ui.adapters.PlayManagerAdapter

class ManagerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityManagerBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityManagerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Manager Portal"

        setupPlayList()
        setupAddPlayForm()
    }

    private fun setupPlayList() {
        val adapter = PlayManagerAdapter(
            onSetActive = { play -> viewModel.setActivePlay(play.id) },
            onDelete = { play ->
                com.google.android.material.dialog.MaterialAlertDialogBuilder(this)
                    .setTitle("Delete Play")
                    .setMessage("Remove '${play.title}'?")
                    .setPositiveButton("Delete") { _, _ -> /* viewModel.deletePlay(play) */ }
                    .setNegativeButton("Cancel", null)
                    .show()
            }
        )
        binding.rvPlays.layoutManager = LinearLayoutManager(this)
        binding.rvPlays.adapter = adapter
        viewModel.allPlays.observe(this) { plays ->
            adapter.submitList(plays)
            binding.tvNoPlays.visibility = if (plays.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun setupAddPlayForm() {
        binding.btnAddPlay.setOnClickListener {
            val title = binding.etPlayTitle.text.toString().trim()
            val desc = binding.etPlayDesc.text.toString().trim()
            val duration = binding.etDuration.text.toString().trim()
            val venue = binding.etVenue.text.toString().trim()
            val date = binding.etDate.text.toString().trim()
            val time = binding.etTime.text.toString().trim()
            val actor = binding.etActor.text.toString().trim()
            val comedian = binding.etComedian.text.toString().trim()
            val singer = binding.etSinger.text.toString().trim()

            if (title.isEmpty() || venue.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "Please fill title, venue, and date.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val play = Play(
                title = title,
                description = desc,
                duration = duration.ifEmpty { "2 hrs" },
                venue = venue,
                showDate = date,
                showTime = time.ifEmpty { "7:00 PM" },
                posterUrl = "poster_default",
                isActive = false
            )
            viewModel.insertPlay(play)
            clearForm()
            Toast.makeText(this, "Play '$title' added!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearForm() {
        binding.etPlayTitle.text?.clear()
        binding.etPlayDesc.text?.clear()
        binding.etDuration.text?.clear()
        binding.etVenue.text?.clear()
        binding.etDate.text?.clear()
        binding.etTime.text?.clear()
        binding.etActor.text?.clear()
        binding.etComedian.text?.clear()
        binding.etSinger.text?.clear()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
