package model

import org.jetbrains.compose.resources.DrawableResource

data class Country(val image: DrawableResource,val rating:Int,val countryName:String,val aboutCountry:String)
