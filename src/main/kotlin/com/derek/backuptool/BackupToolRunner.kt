package com.derek.backuptool

import com.derek.backuptool.service.parser.BackupToolCommandlineParser
import com.derek.backuptool.service.parser.RawConfigParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupToolRunner @Inject constructor(
    private val commandlineParser: BackupToolCommandlineParser,
    private val rawConfigParser: RawConfigParser
) {

    fun run(args: Array<String>) {
        val backupToolArguments = commandlineParser.parse(args)
        println(backupToolArguments)
    }
}
