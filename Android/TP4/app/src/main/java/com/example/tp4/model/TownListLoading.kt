package com.example.tp4.model

sealed interface TownListLoading

data class TownListProgress(val townNumber: Int): TownListLoading

data class TownListResult(val townList: List<Town>): TownListLoading