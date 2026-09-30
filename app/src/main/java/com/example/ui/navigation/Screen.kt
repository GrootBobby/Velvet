package com.example.ui.navigation

sealed class Screen(val route: String, val title: String) {
    data object MonBar : Screen("mon_bar", "Mon Bar")
    data object Recettes : Screen("recettes", "Recettes")
    data object JeuxParty : Screen("jeux_party", "Jeux Party")
    data object Profil : Screen("profil", "Profil")
    data class Detail(val cocktailId: Long) : Screen("detail/$cocktailId", "Cocktail Detail")
    data object Friends : Screen("friends", "Amis & Classement")
}
