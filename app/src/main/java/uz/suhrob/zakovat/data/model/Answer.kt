package uz.suhrob.zakovat.data.model

data class Answer(
    val question: String = "",
    val game: String = "",
    val team: String = "",
    val answer: String = "",
    val isRight: Int = 0,
)
