package fr.durandlyam.gsb


data class Credentials(
    val email : String,
    val password : String
)

data class User(
    val id: Int,
    val email: String,
    val nom: String,
    val prenom: String,
    val api_token: String
)

data class Practiciens(
    val id: Int,
    val nom: String,
    val prenom: String
)