package com.daniel.housetasker.data.database.settings

/*
   Data clase para devolver el flow con los datos de settings guardados
 */
data class SettingsModel(var text_size: Int, var dark_mode: Boolean, var notifications: Boolean)