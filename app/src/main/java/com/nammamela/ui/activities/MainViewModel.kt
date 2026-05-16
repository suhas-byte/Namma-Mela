package com.nammamela.ui.activities

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.nammamela.data.models.*
import com.nammamela.data.repository.NammaMelaRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = NammaMelaRepository(application)

    val tonightsPlay = repo.tonightsPlay
    val allPlays = repo.allPlays

    fun getCastForPlay(playId: Int) = repo.getCastForPlay(playId)
    fun getSeatsForPlay(playId: Int) = repo.getSeatsForPlay(playId)
    fun getAvailableCount(playId: Int) = repo.getAvailableCount(playId)
    fun getApplauseForPlay(playId: Int) = repo.getApplauseForPlay(playId)

    fun reserveSeat(seat: Seat, name: String) = viewModelScope.launch {
        repo.reserveSeat(seat, name)
    }

    fun postApplause(playId: Int, fanName: String, message: String, emoji: String) = viewModelScope.launch {
        repo.postApplause(Applause(playId = playId, fanName = fanName, message = message, emoji = emoji))
    }

    fun insertPlay(play: Play) = viewModelScope.launch {
        val id = repo.insertPlay(play).toInt()
        repo.initSeatsForPlay(play.copy(id = id))
    }

    fun updatePlay(play: Play) = viewModelScope.launch {
        repo.updatePlay(play)
    }

    fun setActivePlay(id: Int) = viewModelScope.launch {
        repo.setActivePlay(id)
    }

    fun saveCast(playId: Int, members: List<CastMember>) = viewModelScope.launch {
        repo.replaceCast(playId, members)
    }
}
