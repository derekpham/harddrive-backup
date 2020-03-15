package executable

import kotlin.system.exitProcess
import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException

object BackupToHardDrive {
    val CONFIG = "config"

    @JvmStatic
    fun main(args: Array<String>) {
        val options = Options()

        val configOption = Option(
            CONFIG,
            true,
            "The yaml backup config file. See example_conf.yaml."
        )
        configOption.isRequired = true
        options.addOption(configOption)

        val parser = DefaultParser()
        val helpFormatter = HelpFormatter()
        val cmd: CommandLine
        try {
            cmd = parser.parse(options, args)
        } catch (e: ParseException) {
            println(e.message)
            helpFormatter.printHelp("harddrive-backup", options)
            exitProcess(1)
        }

        println(cmd.getOptionValue(CONFIG))
    }
}
