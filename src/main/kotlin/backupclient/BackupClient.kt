package backupclient

import model.BackupConfig

interface BackupClient {
    fun backup(backupConfig: BackupConfig)
}
