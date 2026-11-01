package com.example.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Ayat
import com.example.data.AyatStatus
import com.example.data.QuranRepository
import com.example.data.Surah
import com.example.data.AiChatMessage
import com.example.data.MessageSender
import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import java.util.UUID

private const val TAG = "QuranViewModel"

sealed interface Screen {
    data object Home : Screen
    data class SurahDetail(val surah: Surah) : Screen
    data class Practice(val surah: Surah) : Screen
    data object Quiz : Screen
    data object Statistics : Screen
    data object AiMentor : Screen
    data object Setoran : Screen
}

sealed interface AiUiState {
    data object Idle : AiUiState
    data object Loading : AiUiState
    data class Success(val responseText: String) : AiUiState
    data class Error(val message: String) : AiUiState
}

class QuranViewModel : ViewModel() {

    private val _currentScreen = MutableStateFlow<Screen>(Screen.Home)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _surahs = MutableStateFlow(QuranRepository.surahs)
    val surahs: StateFlow<List<Surah>> = _surahs.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _currentAyats = MutableStateFlow<List<Ayat>>(emptyList())
    val currentAyats: StateFlow<List<Ayat>> = _currentAyats.asStateFlow()

    private val _streakDays = MutableStateFlow(7)
    val streakDays: StateFlow<Int> = _streakDays.asStateFlow()

    private val _memorizedAyatTotal = MutableStateFlow(17)
    val memorizedAyatTotal: StateFlow<Int> = _memorizedAyatTotal.asStateFlow()

    private val _aiMessages = MutableStateFlow<List<AiChatMessage>>(
        listOf(
            AiChatMessage(
                id = UUID.randomUUID().toString(),
                sender = MessageSender.AI,
                text = "Assalamu'alaikum! Saya AI Quran Mentor Anda. Tanyakan apa saja seputar hafalan, hukum tajwid, tafsir ayat, atau motivasi menghafal Al-Quran."
            )
        )
    )
    val aiMessages: StateFlow<List<AiChatMessage>> = _aiMessages.asStateFlow()

    private val _aiState = MutableStateFlow<AiUiState>(AiUiState.Idle)
    val aiState: StateFlow<AiUiState> = _aiState.asStateFlow()

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun selectSurah(surah: Surah) {
        _currentAyats.value = QuranRepository.getAyatsForSurah(surah.number)
        _currentScreen.value = Screen.SurahDetail(surah)
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun toggleAyatStatus(ayatNumber: Int) {
        _currentAyats.update { list ->
            list.map { ayat ->
                if (ayat.ayatNumber == ayatNumber) {
                    val nextStatus = when (ayat.status) {
                        AyatStatus.BELUM -> AyatStatus.PROSES
                        AyatStatus.PROSES -> AyatStatus.MUTQIN
                        AyatStatus.MUTQIN -> AyatStatus.BELUM
                    }
                    ayat.copy(status = nextStatus)
                } else {
                    ayat
                }
            }
        }
    }

    fun sendAiMessage(prompt: String) {
        if (prompt.isBlank()) return
        val userMsg = AiChatMessage(
            id = UUID.randomUUID().toString(),
            sender = MessageSender.USER,
            text = prompt
        )
        _aiMessages.update { it + userMsg }
        _aiState.value = AiUiState.Loading

        viewModelScope.launch {
            try {
                val model = try {
                    Firebase.ai.generativeModel("gemini-3.8-flash")
                } catch (e: Exception) {
                    null
                }

                if (model == null) {
                    val fallbackAnswer = "Wa'alaikumussalam. Tips menghafal Al-Quran:\n1. Niatkan ikhlas karena Allah.\n2. Baca berulang-ulang ayat yang ingin dihafal (metode talaqqi/takrir).\n3. Pahami arti dan maknanya agar lebih mudah melekat di hati.\n4. Istiqomah dengan target harian (misal 5 ayat per hari)."
                    val aiMsg = AiChatMessage(
                        id = UUID.randomUUID().toString(),
                        sender = MessageSender.AI,
                        text = fallbackAnswer
                    )
                    _aiMessages.update { it + aiMsg }
                    _aiState.value = AiUiState.Success(fallbackAnswer)
                    return@launch
                }

                val enhancedPrompt = "Bertindaklah sebagai Ustadz dan Mentor Tahfidz Al-Quran yang bijak, memotivasi, dan berpengetahuan luas. Jawablah pertanyaan berikut dalam bahasa Indonesia dengan ramah dan penuh hikmah: $prompt"
                val response = withTimeout(60_000L) {
                    model.generateContent(enhancedPrompt)
                }
                val answer = response.text ?: "Maaf, tidak dapat memuat jawaban saat ini."
                val aiMsg = AiChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = MessageSender.AI,
                    text = answer
                )
                _aiMessages.update { it + aiMsg }
                _aiState.value = AiUiState.Success(answer)
            } catch (e: Exception) {
                Log.e(TAG, "AI generation failed", e)
                val fallbackAnswer = "Alhamdulillah, untuk menghafal Al-Quran kuncinya adalah konsistensi, membaca berulang kali, dan mengulang hafalan (murajaah) setiap hari. Tetap semangat!"
                val errorMsg = AiChatMessage(
                    id = UUID.randomUUID().toString(),
                    sender = MessageSender.AI,
                    text = fallbackAnswer
                )
                _aiMessages.update { it + errorMsg }
                _aiState.value = AiUiState.Success(fallbackAnswer)
            }
        }
    }
}
