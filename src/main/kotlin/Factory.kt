import backupclient.RsyncBackupClient
import com.github.fracpete.rsync4j.RSync
import configparser.ConfigParser
import configparser.ConfigValidator
import java.io.File
import kotlin.reflect.KClass

// would be nice to make all these methods return singletons somehow
object Factory {
    fun getRsync(): RSync {
        return RSync()
            .archive(true)
    }

    fun getRsyncOutputFile(): File {
        val newFile = File("rsync.out")
        if (!newFile.createNewFile()) {
            // race condition can break this
            newFile.delete()
            newFile.createNewFile()
        }

        return newFile
    }

    fun getRsyncBackupClient(): RsyncBackupClient {
        return RsyncBackupClient(
            getRsync(),
            getRsyncOutputFile()
        )
    }

    fun getConfigParser(): ConfigParser {
        return ConfigParser()
    }

    fun getConfigValidator(): ConfigValidator {
        return ConfigValidator()
    }
}
