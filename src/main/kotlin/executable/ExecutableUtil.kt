package executable

import org.apache.commons.cli.CommandLine
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException
import kotlin.system.exitProcess

object ExecutableUtil {

    fun option(opt: String, hasArg: Boolean, description: String, required: Boolean): Option {
        return Option(opt, opt, hasArg, description).apply {
            isRequired = required
        }
    }

    fun parse(options: Options, args: Array<String>): CommandLine {
        val parser = DefaultParser()
        val helpFormatter = HelpFormatter()

        try {
            return parser.parse(options, args)
        } catch (e: ParseException) {
            println(e.message)
            helpFormatter.printHelp("harddrive-backup", options)
            exitProcess(1)
        }
    }
}