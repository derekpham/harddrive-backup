package com.derek.backuptool.service.helperservice

import com.derek.backuptool.dto.BackupToolConfig
import com.derek.backuptool.exception.parser.IllFormedConfigException
import com.derek.backuptool.exception.parser.IllFormedPropertyValueException
import com.derek.backuptool.exception.parser.MissingPropertyException
import org.yaml.snakeyaml.Yaml
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RawConfigParser @Inject constructor() {

    fun parse(rawYaml: String): BackupToolConfig {
        val yaml = Yaml()
        val rawMap = try {
            yaml.load<Map<String, Any>>(rawYaml) ?: throw IllFormedConfigException(rawYaml)
        } catch (e: Exception) {
            throw IllFormedConfigException(rawYaml, e)
        }

        return BackupToolConfig(
            originalPaths = getOrThrow(rawMap, "original-paths"),
            primaryBackupDest = getOrThrow(rawMap, "primary-backup-dest"),
            exclude = getOrThrow(rawMap, "exclude"),
            dirsToTarInSecondaryBackup = getOrThrow(rawMap, "dirs-to-tar-in-secondary-backup")
        )
    }

    private inline fun <reified T> getOrThrow(rawMap: Map<String, Any>, property: String): T {
        val result = rawMap[property] ?: throw MissingPropertyException(property)

        if (result !is T) {
            throw IllFormedPropertyValueException(property)
        }

        return result
    }
}
