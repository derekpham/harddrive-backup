package configparser

import exception.parser.IllFormedConfigException
import exception.parser.IllFormedPropertyValueException
import exception.parser.MissingPropertyException
import model.BackupConfig
import org.yaml.snakeyaml.Yaml

class ConfigParser {
    companion object {
        const val BACKUP_BASE_PATH = "backup-base-path"
        const val BACKUP = "backup"
    }

    fun parse(rawConfig: String): BackupConfig {
        val yaml = Yaml()
        val rawMap = try {
            yaml.load<Map<String, Any>>(rawConfig) ?: throw IllFormedConfigException(rawConfig)
        } catch (e: Exception) {
            throw IllFormedConfigException(rawConfig, e)
        }

        val backupBasePath = getOrThrow<String>(rawMap, BACKUP_BASE_PATH)
        val originalToBackupDir = getOrThrow<Map<String, String>>(rawMap, BACKUP)

        return BackupConfig(backupBasePath, originalToBackupDir)
    }

    private inline fun<reified T> getOrThrow(rawMap: Map<String, Any>, property: String): T {
        val result = rawMap[property] ?: throw MissingPropertyException(property)

        if (result !is T) {
            throw IllFormedPropertyValueException(property)
        }

        return result
    }
}