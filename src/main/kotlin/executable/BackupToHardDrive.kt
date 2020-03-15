package executable

import Factory
import executable.ExecutableUtil.option
import executable.ExecutableUtil.parse
import org.apache.commons.cli.Options
import java.io.File

object BackupToHardDrive {
    private const val CONFIG = "config"

    @JvmStatic
    fun main(args: Array<String>) {
        val options = Options()
        options.addOption(
            option(
                opt = CONFIG,
                hasArg = true,
                description = "The yaml backup config file. See example_conf.yaml.",
                required = true
            )
        )

        val cmd = parse(options, args)

        process(cmd.getOptionValue(CONFIG))
    }

    private fun process(fileName: String) {
        val yamlString = File(fileName).readText(Charsets.UTF_8)
        val backupConfig = Factory.getConfigParser().parse(yamlString)
        Factory.getConfigValidator().validate(backupConfig)
        Factory.getRsyncBackupClient().backup(backupConfig)
    }
}
