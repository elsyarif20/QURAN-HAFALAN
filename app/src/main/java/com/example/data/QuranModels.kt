package com.example.data

data class Surah(
    val number: Int,
    val nameArabic: String,
    val nameLatin: String,
    val translation: String,
    val totalAyat: Int,
    val revelationType: String, // Makkiyah / Madaniyah
    val description: String
)

data class Ayat(
    val surahNumber: Int,
    val ayatNumber: Int,
    val arabicText: String,
    val translationText: String,
    val transliteration: String,
    var status: AyatStatus = AyatStatus.BELUM // BELUM, PROSES, MUTQIN
)

enum class AyatStatus {
    BELUM,
    PROSES,
    MUTQIN
}

data class HafalanProgress(
    val surahNumber: Int,
    val surahName: String,
    val memorizedCount: Int,
    val totalAyat: Int,
    val status: String // "Belum", "Dalam Proses", "Mutqin"
)

data class QuizQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String
)

data class AiChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: Long = System.currentTimeMillis()
)

enum class MessageSender {
    USER, AI
}
