package io.github.donghune.api.manager

import io.github.donghune.api.plugin
import io.github.donghune.api.serialization.BukkitSerialModule
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.modules.plus
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

open class ConfigManager<T : Config>(
    private val dataFolderPath: String,
    private val kSerializer: KSerializer<T>,
    private val defaultData: T,
) {

    private var data: T = defaultData

    private val json = Json {
        prettyPrint = true
        serializersModule += BukkitSerialModule
        encodeDefaults = true
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    val folder = File("${plugin.dataFolder.path}/$dataFolderPath")
    val file = File("$folder", data.fileName)

    init {
        loadConfigFile()
    }

    fun update(config: T) {
        data = config
        saveConfigFile(data)
    }

    fun get(): T {
        return data
    }

    private fun loadConfigFile() {
        val file = File("${plugin.dataFolder.path}/$dataFolderPath", data.fileName)
        if (!file.exists()) {
            folder.mkdirs()
            file.createNewFile()
            saveConfigFile(defaultData)
        }
        val fileInputStream = FileInputStream(file)
        val config = json.decodeFromStream(kSerializer, fileInputStream)
        data = config
    }

    private fun saveConfigFile(data: T) {
        val file = File(/* parent = */ "${plugin.dataFolder.path}/$dataFolderPath", /* child = */ data.fileName)
        FileOutputStream(file).also {
            it.write(json.encodeToString(kSerializer, data).toByteArray())
            it.flush()
        }.close()
    }

}