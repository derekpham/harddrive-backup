package configparser

import exception.parser.IllFormedConfigException
import exception.parser.IllFormedPropertyValueException
import exception.parser.MissingPropertyException
import model.BackupConfig
import org.yaml.snakeyaml.Yaml

/**
 * Parses yaml string into a backup config object.
 */
class ConfigParser {
    fun parse(rawConfig: String): BackupConfig {
        val yaml = Yaml()
        val rawMap = try {
            yaml.load<Map<String, Any>>(rawConfig) ?: throw IllFormedConfigException(rawConfig)
        } catch (e: Exception) {
            throw IllFormedConfigException(rawConfig, e)
        }

        val backupBasePath = getOrThrow<String>(rawMap, Constants.BACKUP_BASE_PATH)
        val originalToBackupDir = getOrThrow<Map<String, String>>(rawMap, Constants.BACKUP)

        return BackupConfig(backupBasePath, originalToBackupDir)
    }

    private inline fun <reified T> getOrThrow(rawMap: Map<String, Any>, property: String): T {
        val result = rawMap[property] ?: throw MissingPropertyException(property)

        if (result !is T) {
            throw IllFormedPropertyValueException(property)
        }

        return result
    }
}
