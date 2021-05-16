package uz.suhrob.zakovat.data.model

data class Game(
    val title: String = "",
    val time: Long = 0L,
    val status: Int = 0, // 0-upcoming, 1-ongoing, -1-ended
)
