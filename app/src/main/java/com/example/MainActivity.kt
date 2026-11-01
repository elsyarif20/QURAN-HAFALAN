package com.example

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.QuranViewModel
import com.example.ui.viewmodel.Screen
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.appCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureAppCheck(this, intent)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val viewModel: QuranViewModel = viewModel()
                val currentScreen by viewModel.currentScreen.collectAsState()
                val surahs by viewModel.surahs.collectAsState()
                val searchQuery by viewModel.searchQuery.collectAsState()
                val currentAyats by viewModel.currentAyats.collectAsState()
                val streakDays by viewModel.streakDays.collectAsState()
                val memorizedTotal by viewModel.memorizedAyatTotal.collectAsState()
                val aiMessages by viewModel.aiMessages.collectAsState()
                val aiState by viewModel.aiState.collectAsState()

                when (val screen = currentScreen) {
                    is Screen.Home -> {
                        HomeScreen(
                            surahs = surahs,
                            searchQuery = searchQuery,
                            onSearchChanged = viewModel::updateSearchQuery,
                            onSurahClick = viewModel::selectSurah,
                            onNavigateQuiz = { viewModel.navigateTo(Screen.Quiz) },
                            onNavigateStats = { viewModel.navigateTo(Screen.Statistics) },
                            onNavigateAi = { viewModel.navigateTo(Screen.AiMentor) },
                            onNavigateSetoran = { viewModel.navigateTo(Screen.Setoran) },
                            streakDays = streakDays,
                            memorizedTotal = memorizedTotal
                        )
                    }
                    is Screen.SurahDetail -> {
                        SurahDetailScreen(
                            surah = screen.surah,
                            ayats = currentAyats,
                            onBackClick = { viewModel.navigateTo(Screen.Home) },
                            onToggleAyatStatus = viewModel::toggleAyatStatus,
                            onStartPractice = { viewModel.navigateTo(Screen.Practice(screen.surah)) }
                        )
                    }
                    is Screen.Practice -> {
                        MemorizationPracticeScreen(
                            surah = screen.surah,
                            ayats = currentAyats,
                            onBackClick = { viewModel.navigateTo(Screen.SurahDetail(screen.surah)) }
                        )
                    }
                    is Screen.Quiz -> {
                        QuizScreen(
                            onBackClick = { viewModel.navigateTo(Screen.Home) }
                        )
                    }
                    is Screen.Statistics -> {
                        StatisticsScreen(
                            streakDays = streakDays,
                            memorizedTotal = memorizedTotal,
                            onBackClick = { viewModel.navigateTo(Screen.Home) }
                        )
                    }
                    is Screen.AiMentor -> {
                        AiMentorScreen(
                            messages = aiMessages,
                            aiState = aiState,
                            onSendMessage = viewModel::sendAiMessage,
                            onBackClick = { viewModel.navigateTo(Screen.Home) }
                        )
                    }
                    is Screen.Setoran -> {
                        SetoranScreen(
                            surahs = surahs,
                            onBackClick = { viewModel.navigateTo(Screen.Home) }
                        )
                    }
                }
            }
        }
    }

    private fun configureAppCheck(context: Context, intent: Intent?) {
        try {
            if (FirebaseApp.getApps(context).isNotEmpty()) {
                val firebaseAppCheck = Firebase.appCheck
                firebaseAppCheck.installAppCheckProviderFactory(
                    DebugAppCheckProviderFactory.getInstance()
                )
                Log.d(TAG, "App Check initialized with Debug provider")
            } else {
                Log.d(TAG, "App Check skipped: FirebaseApp not initialized")
            }
        } catch (e: Exception) {
            Log.d(TAG, "App Check skipped", e)
        }
    }
}
