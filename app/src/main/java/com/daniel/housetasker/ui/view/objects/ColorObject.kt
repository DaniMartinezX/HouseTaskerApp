package com.daniel.housetasker.ui.view.objects

class ColorObject(var name: String, var hex: String, var contrastHex:String)
{
    val hesHash : String = "#$hex"
    val contrastHexHash : String = "#$contrastHex"
}