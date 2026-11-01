package com.example.data

object QuranRepository {

    val surahs = listOf(
        Surah(1, "الفاتحة", "Al-Fatihah", "Pembukaan", 7, "Makkiyah", "Surah pembukaan Al-Quran yang wajib dibaca dalam setiap rakaat salat."),
        Surah(2, "البقرة", "Al-Baqarah", "Sapi Betina", 286, "Madaniyah", "Surah terpanjang dalam Al-Quran yang memuat banyak hukum Islam dan Ayat Kursi."),
        Surah(36, "يس", "Ya-Sin", "Ya Sin", 83, "Makkiyah", "Jantung Al-Quran yang sering dibaca untuk ketenangan hati dan keutamaan syafaat."),
        Surah(55, "الرحمن", "Ar-Rahman", "Yang Maha Pemurah", 78, "Madaniyah", "Pengingat akan nikmat-nikmat Allah yang luar biasa dengan refrein 'Maka nikmat Tuhanmu yang manakah yang kamu dustakan?'"),
        Surah(56, "الواقعة", "Al-Waqiah", "Hari Kiamat", 96, "Makkiyah", "Surah yang dianjurkan dibaca setiap malam untuk melapangkan rezeki dan ketenangan jiwa."),
        Surah(67, "الملك", "Al-Mulk", "Kerajaan", 30, "Makkiyah", "Penyelamat dari siksa kubur bagi yang membacanya setiap malam."),
        Surah(78, "النبأ", "An-Naba'", "Berita Besar", 40, "Makkiyah", "Surah pembuka Juz Amma tentang kebangkitan dan hari akhir."),
        Surah(112, "الإخلاص", "Al-Ikhlas", "Kurnia / Keesaan Allah", 4, "Makkiyah", "Pernyataan tauhid murni yang pahalanya setara dengan sepertiga Al-Quran."),
        Surah(113, "الفلق", "Al-Falaq", "Waktu Subuh", 5, "Makkiyah", "Perlindungan kepada Allah dari kejahatan makhluk dan kegelapan malam."),
        Surah(114, "الناس", "An-Nas", "Manusia", 6, "Makkiyah", "Perlindungan kepada Allah dari bisikan setan dan kejahatan jin serta manusia.")
    )

    fun getAyatsForSurah(surahNumber: Int): List<Ayat> {
        return when (surahNumber) {
            1 -> listOf(
                Ayat(1, 1, "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ", "Dengan nama Allah Yang Maha Pengasih, Maha Penyayang.", "Bismillaahir-rahmanir-rahim"),
                Ayat(1, 2, "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ", "Segala puji bagi Allah, Tuhan seluruh alam.", "Alhamdu lillahi rabbil 'alamin"),
                Ayat(1, 3, "الرَّحْمَٰنِ الرَّحِيمِ", "Yang Maha Pengasih, Maha Penyayang.", "Ar-rahmanir-rahim"),
                Ayat(1, 4, "مَالِكِ يَوْمِ الدِّينِ", "Pemilik hari pembalasan.", "Maliki yawmid-din"),
                Ayat(1, 5, "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ", "Hanya kepada-Mulah kami menyembah dan hanya kepada-Mulah kami mohon pertolongan.", "Iyyaka na'budu wa iyyaka nasta'in"),
                Ayat(1, 6, "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ", "Tunjukilah kami jalan yang lurus,", "Ihdinas-siratal-mustaqim"),
                Ayat(1, 7, "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ", "(yaitu) jalan orang-orang yang telah Engkau beri nikmat kepadanya; bukan (jalan) mereka yang dimurkai, dan bukan (pula jalan) mereka yang sesat.", "Siratal-lazina an'amta 'alaihim ghairil-maghdubi 'alaihim wa lad-dallin")
            )
            112 -> listOf(
                Ayat(112, 1, "قُلْ هُوَ اللَّهُ أَحَدٌ", "Katakanlah (Muhammad), 'Dialah Allah, Yang Maha Esa.'", "Qul huwallahu ahad"),
                Ayat(112, 2, "اللَّهُ الصَّمَدُ", "Allah tempat meminta segala sesuatu.", "Allahu-samad"),
                Ayat(112, 3, "لَمْ يَلِدْ وَلَمْ يُولَدْ", "(Allah) tidak beranak dan tidak pula diperanakkan.", "Lam yalid wa lam yulad"),
                Ayat(112, 4, "وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ", "Dan tidak ada sesuatu yang setara dengan Dia.", "Wa lam yakun lahu kufuwan ahad")
            )
            113 -> listOf(
                Ayat(113, 1, "قُلْ أَعُوذُ بِرَبِّ الْفَلَقِ", "Katakanlah, 'Aku berlindung kepada Tuhan yang menguasai subuh (fajar),'", "Qul a'udhu birabbil-falaq"),
                Ayat(113, 2, "مِن شَرِّ مَا خَلَقَ", "dari kejahatan (makhluk yang) He ciptakan,", "Min sharri ma khalaq"),
                Ayat(113, 3, "وَمِن شَرِّ غَاسِقٍ إِذَا وَقَبَ", "dan dari kejahatan malam apabila telah gelap gulita,", "Wa min sharri ghasiqin idha waqab"),
                Ayat(113, 4, "وَمِن شَرِّ النَّفَّاثَاتِ فِي الْعُقَدِ", "dan dari kejahatan (perempuan-perempuan) penyihir yang meniup pada buhul-buhulnya,", "Wa min sharrin-naffathati fil-'uqad"),
                Ayat(113, 5, "وَمِن شَرِّ حَاسِدٍ إِذَا حَسَدَ", "dan dari kejahatan orang yang dengki apabila dia dengki.'", "Wa min sharri hasidin idha hasad")
            )
            114 -> listOf(
                Ayat(114, 1, "قُلْ أَعُوذُ بِرَبِّ النَّاسِ", "Katakanlah, 'Aku berlindung kepada Tuhan manusia,'", "Qul a'udhu birabbin-nas"),
                Ayat(114, 2, "مَلِكِ النَّاسِ", "Raja manusia,", "Malikin-nas"),
                Ayat(114, 3, "إِلَٰهِ النَّاسِ", "Sembahan manusia,", "Ilahin-nas"),
                Ayat(114, 4, "مِن شَرِّ الْوَسْواسِ الْخَنَّاسِ", "dari kejahatan (bisikan) setan yang bersembunyi,", "Min sharril-waswasil-khannas"),
                Ayat(114, 5, "الَّذِي يُوَسْوِسُ فِي صُدُورِ النَّاسِ", "yang membisikkan (kejahatan) ke dalam dada manusia,", "Alladhi yuwaswisu fi sudurin-nas"),
                Ayat(114, 6, "مِنَ الْجِنَّةِ وَالنَّاسِ", "dari ( golongan) jin dan manusia.'", "Min-al-jinnati wan-nas")
            )
            else -> {
                // Generate sample ayats for other surahs for full app demonstration
                val surah = surahs.find { it.number == surahNumber } ?: surahs[0]
                val count = minOf(surah.totalAyat, 10)
                List(count) { index ->
                    Ayat(
                        surahNumber = surahNumber,
                        ayatNumber = index + 1,
                        arabicText = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ ۝ هَٰذَا مِثَالٌ لِلْآيَةِ ${index + 1} مِنْ سُورَةِ ${surah.nameArabic}",
                        translationText = "Terjemahan ayat ke-${index + 1} dari ${surah.nameLatin} (${surah.translation}).",
                        transliteration = "Bismillah... Ayat ${index + 1} of ${surah.nameLatin}"
                    )
                }
            }
        }
    }

    val sampleQuizQuestions = listOf(
        QuizQuestion(
            1,
            "Surah apakah yang disebut sebagai Jantung Al-Quran (Qalbul Quran)?",
            listOf("Al-Baqarah", "Ya-Sin", "Ar-Rahman", "Al-Mulk"),
            1,
            "Ya-Sin dikenal sebagai Jantung Al-Quran berdasarkan hadis Nabi Muhammad SAW."
        ),
        QuizQuestion(
            2,
            "Berapa jumlah ayat dalam Surah Al-Fatihah?",
            listOf("5 ayat", "6 ayat", "7 ayat", "8 ayat"),
            2,
            "Surah Al-Fatihah terdiri dari 7 ayat yang selalu dibaca dalam setiap salat."
        ),
        QuizQuestion(
            3,
            "Surah manakah yang menjadi penyelamat dari siksa kubur jika dibaca setiap malam?",
            listOf("Al-Mulk", "Al-Waqiah", "Al-Kahfi", "An-Naba'"),
            0,
            "Surah Al-Mulk memiliki keutamaan sebagai syafaat dan pelindung dari azab kubur."
        )
    )
}
