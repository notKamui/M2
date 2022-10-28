package com.notkamui.tp2.country

data class Rank(
    val position: Int,
    val maxPosition: Int,
) {
    val ratio = 1f - (position.toFloat() / maxPosition.toFloat())
}

sealed interface QuantitativeFact {
    val value: Int
    val rank: Rank
    val unit: String
}

class AreaFact(
    override val value: Int,
    override val rank: Rank,
) : QuantitativeFact {
    override val unit = "km²"
}

class PopulationFact(
    override val value: Int,
    override val rank: Rank,
) : QuantitativeFact {
    override val unit = "M"
}

class DensityFact(
    override val value: Int,
    override val rank: Rank,
) : QuantitativeFact {
    override val unit = "hab/km²"
}

class PerCapitalGDPFact(
    override val value: Int,
    override val rank: Rank,
) : QuantitativeFact {
    override val unit = "USD"
}