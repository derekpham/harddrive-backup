package configparser

import exception.parser.ValidatorFailureException
import model.BackupConfig

/**
 * Validates a backup config object:
 * - the backup destination must be absolute
 * - the backup original directories must be absolute
 * - the backup destination directories must be relative
 */
class ConfigValidator {
    private val validatorFunctions: List<Pair<(BackupConfig) -> Boolean, String>> = arrayListOf(
        Pair(this::backupBasePathIsAbsolute, "Base path is not absolute"),
        Pair(this::backupPathsAreRelative, "Backup paths must be relative"),
        Pair(this::originPathsAreAbsolute, "Original paths are not absolute")
    )

    fun validate(backupConfig: BackupConfig) {
        validatorFunctions.forEach {
            if (!it.first.invoke(backupConfig)) {
                throw ValidatorFailureException(it.second, backupConfig)
            }
        }
    }

    private fun backupBasePathIsAbsolute(backupConfig: BackupConfig): Boolean {
        return isPathAbsolute(backupConfig.backupBasePath)
    }

    private fun backupPathsAreRelative(backupConfig: BackupConfig): Boolean {
        return backupConfig.originalToBackupDir.values.all {
            !isPathAbsolute(it)
        }
    }

    private fun originPathsAreAbsolute(backupConfig: BackupConfig): Boolean {
        return backupConfig.originalToBackupDir.keys.all {
            isPathAbsolute(it)
        }
    }

    // TODO: an actual implementation
    private fun isPathAbsolute(path: String): Boolean {
        return path.startsWith("/") || path.startsWith("~")
    }
}
