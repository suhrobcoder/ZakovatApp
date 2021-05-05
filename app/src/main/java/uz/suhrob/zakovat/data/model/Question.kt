package uz.suhrob.zakovat.data.model

data class Question(
    val title: String = "",
    val answers: List<Answer> = listOf(),
)
