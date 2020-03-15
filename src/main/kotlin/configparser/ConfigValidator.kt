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
        val backupBasePath = backupConfig.backupBasePath

        return backupBasePath.startsWith("/")
    }

    private fun backupPathsAreRelative(backupConfig: BackupConfig): Boolean {
        return backupConfig.originalToBackupDir.values.all {
            !it.startsWith("/")
        }
    }

    private fun originPathsAreAbsolute(backupConfig: BackupConfig): Boolean {
        return backupConfig.originalToBackupDir.keys.all {
            it.startsWith("/")
        }
    }
}
