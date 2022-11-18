package com.notkamui.tp2.country

sealed class MainComponent

class CountryComponent(val country: Country) : MainComponent()

object FlagsComponent : MainComponent()