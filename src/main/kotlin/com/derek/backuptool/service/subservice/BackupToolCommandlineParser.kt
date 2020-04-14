package com.derek.backuptool.service.subservice

import com.derek.backuptool.dto.BackupToolArguments
import org.apache.commons.cli.CommandLineParser
import org.apache.commons.cli.DefaultParser
import org.apache.commons.cli.HelpFormatter
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.apache.commons.cli.ParseException
import org.apache.logging.log4j.LogManager
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.system.exitProcess

@Singleton
class BackupToolCommandlineParser @Inject constructor() {

    companion object {
        const val CONF_OPTION = "conf"
        const val ACTION_OPTION = "actions"
    }

    private val logger = LogManager.getLogger(BackupToolCommandlineParser::class.java)

    private val options: Options = Options()
    private val parser: CommandLineParser = DefaultParser()
    private val helpFormatter: HelpFormatter = HelpFormatter()

    init {
        options.addOption(
            option(
                opt = CONF_OPTION,
                description = "the YAML config file"
            )
        )
        options.addOption(
            option(
                opt = ACTION_OPTION,
                description = "Actions to be performed. One of primary-backup, stats, upload-to-s3",
                numArgs = Option.UNLIMITED_VALUES
            )
        )
    }

    fun parse(args: Array<String>, validActions: Set<String>): BackupToolArguments {
        try {
            val commandLine = parser.parse(options, args)
            if (!actionsAreValid(commandLine.getOptionValues(ACTION_OPTION), validActions)) {
                throw ParseException("Valid actions are $validActions")
            }
            return BackupToolArguments(
                confFile = commandLine.getOptionValue(CONF_OPTION),
                actions = commandLine.getOptionValues(ACTION_OPTION).toList()
            )
        } catch (e: ParseException) {
            logger.error(e)
            helpFormatter.printHelp("backup-tool", options)
            exitProcess(1)
        }
    }

    private fun actionsAreValid(actions: Array<String>, validActions: Set<String>): Boolean {
        return actions.toSet().minus(validActions).isEmpty()
    }

    private fun option(
        opt: String,
        hasArg: Boolean = true,
        description: String,
        required: Boolean = true,
        numArgs: Int = 1
    ): Option {
        return Option(opt, opt, hasArg, description).apply {
            isRequired = required
            args = numArgs
        }
    }
}
