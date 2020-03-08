package model

/**
 * @param backupBasePath: the absolute path to the backup path (usually the directory of your external hard drive)
 * @param originalToBackupDir: map of original paths to their relative backup paths
 */
data class BackupConfig(val backupBasePath: String,
                        val originalToBackupDir: Map<String, String>)