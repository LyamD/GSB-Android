package fr.durandlyam.gsb.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "visite_table")
data class Visite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @Json(name = "dateMission")
    val date: String,
    val motif: String,
    val bilan: String,
    @Json(name = "utilisateurs_nom")
    val utilisateursNom: String,
    @Json(name = "visiteur_nom")
    val visiteurNom: String

)