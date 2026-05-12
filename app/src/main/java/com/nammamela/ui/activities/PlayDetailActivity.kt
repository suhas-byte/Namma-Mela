package com.nammamela.ui.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.nammamela.R
import com.nammamela.databinding.ActivityPlayDetailBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.nammamela.ui.adapters.CastAdapter

class PlayDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayDetailBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val playId = intent.getIntExtra("play_id", -1)
        if (playId == -1) { finish(); return }

        // Cast list
        val castAdapter = CastAdapter()
        binding.rvCastDetail.layoutManager = LinearLayoutManager(this)
        binding.rvCastDetail.adapter = castAdapter
        viewModel.getCastForPlay(playId).observe(this) { cast ->
            castAdapter.submitList(cast)
        }

        // Seat booking
        binding.btnGoToSeats.setOnClickListener {
            startActivity(Intent(this, SeatMapActivity::class.java).apply {
                putExtra("play_id", playId)
            })
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }
}
