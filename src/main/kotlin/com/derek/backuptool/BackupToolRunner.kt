package com.derek.backuptool

import com.derek.backuptool.service.subservice.BackupToolCommandlineParser
import com.derek.backuptool.service.PrimaryBackupService
import com.derek.backuptool.service.S3BackupService
import com.derek.backuptool.service.StatisticsService
import com.derek.backuptool.service.subservice.RawConfigParser
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupToolRunner @Inject constructor(
    private val commandlineParser: BackupToolCommandlineParser,
    private val rawConfigParser: RawConfigParser,
    primaryBackupService: PrimaryBackupService,
    s3BackupService: S3BackupService,
    statisticsService: StatisticsService
) {

    private val actionConfiguration = mapOf(
        "primary-backup" to primaryBackupService,
        "stats" to statisticsService,
        "upload-to-s3" to s3BackupService
    )

    fun run(args: Array<String>) {
        val backupToolArguments = commandlineParser.parse(args, actionConfiguration.keys)
        val rawYaml = File(backupToolArguments.confFile).readText(Charsets.UTF_8)
        val backupToolConfig = rawConfigParser.parse(rawYaml)
        println(backupToolConfig)
        backupToolArguments.actions.forEach {
            println("********** Start task: $it **********")
            actionConfiguration[it]?.performService(backupToolConfig)
            println("********** End task: $it **********")
        }
    }
}
