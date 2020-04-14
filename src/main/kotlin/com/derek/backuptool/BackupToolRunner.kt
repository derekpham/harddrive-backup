package com.derek.backuptool

import com.derek.backuptool.service.BackupToolCommandlineParser
import com.derek.backuptool.service.backuptoolaction.PrimaryBackupService
import com.derek.backuptool.service.backuptoolaction.S3BackupService
import com.derek.backuptool.service.backuptoolaction.StatisticsService
import com.derek.backuptool.service.parser.RawConfigParser
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackupToolRunner @Inject constructor(
    private val commandlineParser: BackupToolCommandlineParser,
    private val rawConfigParser: RawConfigParser,
    private val primaryBackupService: PrimaryBackupService,
    private val s3BackupService: S3BackupService,
    private val statisticsService: StatisticsService
) {

    private val actionConfiguration = mapOf(
        "primary-backup" to primaryBackupService,
        "stats" to statisticsService,
        "upload-to-s3" to s3BackupService
    )

    fun run(args: Array<String>) {
        val backupToolArguments = commandlineParser.parse(args, actionConfiguration.keys)
        println(backupToolArguments)
    }
}
