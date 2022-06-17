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

open class EntityManager<T : Entity>(
    private val dataFolderPath: String,
    private val kSerializer: KSerializer<T>,
    private val defaultData: (String) -> T? = { null },
) {

    private val data = mutableMapOf<String, T>()

    private val json = Json {
        prettyPrint = true
        serializersModule += BukkitSerialModule
        encodeDefaults = true
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    init {
        loadEntityFile()
    }

    fun createDefault(key: String) : T {
        return create(defaultData(key) ?: throw Exception("해당 객체는 기본값으로 생성 할 수 없습니다."))
    }

    fun create(entity: T): T {
        data[entity.primaryKey] = entity
        saveEntityFile(entity)
        return entity
    }

    fun update(entity: T) {
        data[entity.primaryKey] = entity
        saveEntityFile(entity)
    }

    fun delete(entity: T) {
        data.remove(entity.primaryKey)
        deleteEntityFile(entity)
    }

    fun deleteByKey(key: String) {
        delete(getSafety(key))
    }

    fun get(key: String): T? {
        return data[key]
    }

    fun getSafety(key: String): T {
        if (data[key] == null) {
            create(defaultData(key) ?: throw Exception("기본값이 초기화 되어있지 않습니다."))
        }
        return data[key]!!
    }

    fun getList(): List<T> {
        return data.values.toList()
    }

    private fun loadEntityFile() {
        val folder = File("${plugin.dataFolder.path}/$dataFolderPath")
        if (!folder.exists()) {
            folder.mkdirs()
        }
        (folder.listFiles() ?: arrayOf<File>())
            .filter { it.extension == "json" }
            .forEach { dataFile ->
                val fileInputStream = FileInputStream(dataFile)
                val entity = json.decodeFromStream(kSerializer, fileInputStream)
                data[entity.primaryKey] = entity
            }
    }

    private fun deleteEntityFile(entity: T) {
        File(plugin.dataFolder.path + "/" + dataFolderPath, entity.fileName).delete()
    }

    private fun saveEntityFile(entity: T) {
        val folder = File("${plugin.dataFolder.path}/$dataFolderPath")
        FileOutputStream(File(folder, entity.fileName)).also {
            it.write(json.encodeToString(kSerializer, entity).toByteArray())
            it.flush()
        }.close()
    }

    private fun saveDataFiles() {
        data.values.forEach { saveEntityFile(it) }
    }

}