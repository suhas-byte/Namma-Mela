package com.nammamela.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nammamela.R
import com.nammamela.data.models.Applause
import com.nammamela.databinding.ActivityMainBinding
import com.nammamela.ui.adapters.ApplauseAdapter
import com.nammamela.ui.adapters.CastAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import android.widget.EditText
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var currentPlayId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupUI()
        observeData()
    }

    private fun setupUI() {
        binding.btnBookSeat.setOnClickListener {
            if (currentPlayId != -1) {
                startActivity(Intent(this, SeatMapActivity::class.java).apply {
                    putExtra("play_id", currentPlayId)
                })
            }
        }

        binding.btnLeaveApplause.setOnClickListener {
            showApplauseDialog()
        }
    }

    private fun observeData() {
        viewModel.tonightsPlay.observe(this) { play ->
            if (play != null) {
                currentPlayId = play.id
                binding.groupNoPlay.visibility = View.GONE
                binding.groupPlayDetails.visibility = View.VISIBLE

                binding.tvPlayTitle.text = play.title
                binding.tvPlayDuration.text = "⏱ ${play.duration}"
                binding.tvVenue.text = "📍 ${play.venue}"
                binding.tvShowTime.text = "🕖 ${play.showDate} • ${play.showTime}"
                binding.tvPlayDescription.text = play.description

                // Load poster
                val resId = resources.getIdentifier(play.posterUrl, "drawable", packageName)
                if (resId != 0) {
                    Glide.with(this).load(resId).into(binding.ivPoster)
                } else {
                    Glide.with(this).load(play.posterUrl).placeholder(R.drawable.ic_theater_masks).into(binding.ivPoster)
                }

                // Cast
                val castAdapter = CastAdapter()
                binding.rvCast.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                binding.rvCast.adapter = castAdapter
                viewModel.getCastForPlay(play.id).observe(this) { cast ->
                    castAdapter.submitList(cast)
                }

                // Available seats count
                viewModel.getAvailableCount(play.id).observe(this) { count ->
                    binding.tvAvailableSeats.text = "🎭 $count seats available"
                }

                // Applause wall
                val applauseAdapter = ApplauseAdapter()
                binding.rvApplause.layoutManager = LinearLayoutManager(this)
                binding.rvApplause.adapter = applauseAdapter
                viewModel.getApplauseForPlay(play.id).observe(this) { applause ->
                    applauseAdapter.submitList(applause)
                    binding.tvNoApplause.visibility = if (applause.isEmpty()) View.VISIBLE else View.GONE
                }

            } else {
                binding.groupNoPlay.visibility = View.VISIBLE
                binding.groupPlayDetails.visibility = View.GONE
            }
        }
    }

    private fun showApplauseDialog() {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(60, 40, 60, 20)
        }
        val etName = EditText(this).apply { hint = "Your name" }
        val etMessage = EditText(this).apply {
            hint = "Leave your applause message..."
            minLines = 3
        }
        layout.addView(etName)
        layout.addView(etMessage)

        MaterialAlertDialogBuilder(this)
            .setTitle("👏 Fan Wall")
            .setView(layout)
            .setPositiveButton("Post") { _, _ ->
                val name = etName.text.toString().trim()
                val msg = etMessage.text.toString().trim()
                if (name.isNotEmpty() && msg.isNotEmpty() && currentPlayId != -1) {
                    viewModel.postApplause(currentPlayId, name, msg, "👏")
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_manager -> {
                startActivity(Intent(this, ManagerActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
