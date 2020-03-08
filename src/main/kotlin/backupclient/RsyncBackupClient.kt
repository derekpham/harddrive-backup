package backupclient

import com.github.fracpete.rsync4j.RSync
import exception.RSyncFailureException
import model.BackupConfig
import java.io.File

class RsyncBackupClient(
    private val rSync: RSync,
    private val outputFile: File
) : BackupClient {

    override fun backup(backupConfig: BackupConfig) {
        backupConfig.originalToBackupDir.forEach {
            val absoluteBackupPath = "${backupConfig.backupPath}/${it.value}"
            backup(it.key, absoluteBackupPath)
        }
    }

    private fun backup(original: String, backupPath: String) {
        rSync.source(original).destination(backupPath)
        val output = rSync.execute()
        outputFile.writeText(output.stdOut)
        outputFile.writeText("Exit code: " + output.exitCode)
        if (output.exitCode > 0) {
            throw RSyncFailureException(original, backupPath)
        }
    }
}