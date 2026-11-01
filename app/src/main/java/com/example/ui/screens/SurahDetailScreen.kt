package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.Ayat
import com.example.data.AyatStatus
import com.example.data.Surah
import com.example.ui.theme.IslamicGreen
import com.example.ui.theme.GoldAccent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SurahDetailScreen(
    surah: Surah,
    ayats: List<Ayat>,
    onBackClick: () -> Unit,
    onToggleAyatStatus: (Int) -> Unit,
    onStartPractice: () -> Unit
) {
    var isHafalanMode by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(surah.nameLatin, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("${surah.translation} • ${surah.totalAyat} Ayat", fontSize = 12.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.testTag("back_button")
                    ) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                },
                actions = {
                    IconButton(
                        onClick = { isHafalanMode = !isHafalanMode },
                        modifier = Modifier.testTag("toggle_hafalan_mode")
                    ) {
                        Icon(
                            imageVector = if (isHafalanMode) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = "Mode Sembunyikan Ayat",
                            tint = if (isHafalanMode) GoldAccent else MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.surface)
            )
        },
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .navigationBarsPadding()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = onStartPractice,
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .testTag("start_practice_btn"),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Icon(Icons.Default.School, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Latihan Hafalan (Flashcard)", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                if (surah.number != 9) {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(20.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
            }

            items(ayats) { ayat ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = when (ayat.status) {
                            AyatStatus.MUTQIN -> MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                            AyatStatus.PROSES -> GoldAccent.copy(alpha = 0.1f)
                            AyatStatus.BELUM -> MaterialTheme.colorScheme.surface
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("ayat_card_${ayat.ayatNumber}")
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "${ayat.ayatNumber}",
                                            color = Color.White,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = when (ayat.status) {
                                        AyatStatus.MUTQIN -> IslamicGreen
                                        AyatStatus.PROSES -> GoldAccent
                                        AyatStatus.BELUM -> Color.Gray.copy(alpha = 0.3f)
                                    },
                                    modifier = Modifier
                                        .clickable { onToggleAyatStatus(ayat.ayatNumber) }
                                        .testTag("ayat_status_toggle_${ayat.ayatNumber}")
                                ) {
                                    Text(
                                        text = when (ayat.status) {
                                            AyatStatus.MUTQIN -> "Mutqin (Lancar)"
                                            AyatStatus.PROSES -> "Dalam Proses"
                                            AyatStatus.BELUM -> "Belum"
                                        },
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (ayat.status == AyatStatus.BELUM) Color.DarkGray else Color.White,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            IconButton(
                                onClick = { onToggleAyatStatus(ayat.ayatNumber) }
                            ) {
                                Icon(
                                    imageVector = when (ayat.status) {
                                        AyatStatus.MUTQIN -> Icons.Default.CheckCircle
                                        AyatStatus.PROSES -> Icons.Default.HourglassTop
                                        AyatStatus.BELUM -> Icons.Default.RadioButtonUnchecked
                                    },
                                    contentDescription = "Status",
                                    tint = when (ayat.status) {
                                        AyatStatus.MUTQIN -> IslamicGreen
                                        AyatStatus.PROSES -> GoldAccent
                                        AyatStatus.BELUM -> Color.Gray
                                    }
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        if (isHafalanMode && ayat.status != AyatStatus.MUTQIN) {
                            Surface(
                                shape = RoundedCornerShape(12.dp),
                                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(60.dp)
                                    .clickable {
                                        // reveal temporarily
                                    }
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "Ayat Disembunyikan (Ketuk untuk Uji Hafalan)",
                                        fontSize = 13.sp,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                            }
                        } else {
                            Text(
                                text = ayat.arabicText,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.End,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = ayat.transliteration,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.primary,
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = ayat.translationText,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
