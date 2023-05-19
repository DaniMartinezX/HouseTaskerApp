package com.daniel.housetasker.ui.view.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.lifecycleScope
import com.daniel.housetasker.data.database.settings.SettingsModel
import com.daniel.housetasker.databinding.FragmentSettingsBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")    //Singleton con el delegado "by"


class SettingsFragment : Fragment() {

    companion object{
        const val TEXT_SIZE = "text_size"
        const val KEY_DARK_MODE = "key_dark_mode"
        const val KEY_NOTIFICATIONS = "key_notifications"
    }

    private var _binding: FragmentSettingsBinding? = null
    private var firstTime:Boolean = true

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSettingsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            getSettings().filter { firstTime }.collect { settingsModel ->
                if (settingsModel != null) {
                    binding.switchNotifications.isChecked = settingsModel.notifications
                    binding.switchDarkMode.isChecked = settingsModel.dark_mode
                    binding.rsTextSize.setValues(settingsModel.text_size.toFloat())
                    firstTime = false
                }
            }
        }

        binding.rsTextSize.addOnChangeListener { _, value, _ ->
            val textSizeBase = 16f // Tamaño de fuente base
            val newSize = textSizeBase * value

            lifecycleScope.launch {
                saveTextSize(value.toInt())
                // Llamada al método para aumentar el tamaño de fuente de todos los TextView
                increaseTextSizeForViews(binding.root, newSize)
            }
        }

        binding.switchDarkMode.setOnCheckedChangeListener { _, value ->
            lifecycleScope.launch {
                saveOptions(KEY_DARK_MODE, value)
            }
        }

        binding.switchNotifications.setOnCheckedChangeListener { _, value ->
            lifecycleScope.launch {
                saveOptions(KEY_NOTIFICATIONS, value)
            }
        }
    }


    fun increaseTextSizeForViews(rootView: View, newSize: Float) {
        if (rootView is TextView) {
            rootView.setTextSize(TypedValue.COMPLEX_UNIT_SP, newSize)
        }

        if (rootView is ViewGroup) {
            val childCount = rootView.childCount
            for (i in 0 until childCount) {
                val child = rootView.getChildAt(i)
                increaseTextSizeForViews(child, newSize)
            }
        }
    }




    private suspend fun saveTextSize(value: Int){
        requireContext().dataStore.edit {
            it[intPreferencesKey(TEXT_SIZE)] = value
        }
    }

    private suspend fun saveOptions(key:String, value: Boolean){
        requireContext().dataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    private fun getSettings(): Flow<SettingsModel?> {
        return requireContext().dataStore.data.map { preferences ->
            SettingsModel(
                text_size = preferences[intPreferencesKey(TEXT_SIZE)] ?: 1,
                dark_mode = preferences[booleanPreferencesKey(KEY_DARK_MODE)] ?: false,
                notifications = preferences[booleanPreferencesKey(KEY_NOTIFICATIONS)] ?: false
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Liberar referencias y recursos del binding
        _binding = null
    }
}