package com.nammamela.ui.activities

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nammamela.data.models.Seat
import com.nammamela.data.models.SeatStatus
import com.nammamela.databinding.ActivitySeatMapBinding
import com.nammamela.ui.adapters.SeatAdapter

class SeatMapActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySeatMapBinding
    private val viewModel: MainViewModel by viewModels()
    private var playId: Int = -1
    private var selectedSeat: Seat? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySeatMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        title = "Seat Map"

        playId = intent.getIntExtra("play_id", -1)
        if (playId == -1) { finish(); return }

        setupSeatGrid()
        setupLegend()
        binding.btnConfirmBooking.setOnClickListener { confirmBooking() }
    }

    private fun setupSeatGrid() {
        val seatAdapter = SeatAdapter { seat ->
            if (seat.status == SeatStatus.AVAILABLE || seat.status == SeatStatus.FRONT_ROW) {
                selectedSeat = seat
                binding.tvSelectedSeat.text = "Selected: Row ${seat.rowLabel} • Seat ${seat.seatNumber}"
                binding.btnConfirmBooking.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Seat ${seat.label} is already reserved.", Toast.LENGTH_SHORT).show()
            }
        }

        // 10 seats per row + 1 label column
        binding.rvSeatMap.layoutManager = GridLayoutManager(this, 10)
        binding.rvSeatMap.adapter = seatAdapter

        // Add row labels as section headers via SpanSizeLookup
        viewModel.getSeatsForPlay(playId).observe(this) { seats ->
            seatAdapter.submitList(seats)
        }
    }

    private fun setupLegend() {
        // Legend is static in XML layout
    }

    private fun confirmBooking() {
        val seat = selectedSeat ?: return
        val input = EditText(this).apply { hint = "Enter your name" }

        MaterialAlertDialogBuilder(this)
            .setTitle("🎭 Reserve Seat ${seat.label}")
            .setMessage("Row ${seat.rowLabel}, Seat ${seat.seatNumber}")
            .setView(input)
            .setPositiveButton("Confirm") { _, _ ->
                val name = input.text.toString().trim()
                if (name.isEmpty()) {
                    Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                    return@setPositiveButton
                }
                viewModel.reserveSeat(seat, name)
                binding.tvSelectedSeat.text = "✅ Seat ${seat.label} reserved for $name!"
                binding.btnConfirmBooking.visibility = View.GONE
                selectedSeat = null
                Toast.makeText(this, "Seat ${seat.label} reserved successfully!", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
