package com.derek.backuptool.service

import com.derek.backuptool.dto.BackupToolConfig
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrimaryBackupService @Inject constructor() : BackupToolService {
    override fun performService(backupToolConfig: BackupToolConfig) {
        backupToolConfig.originalPaths.forEach {
            executeRsync(it, backupToolConfig.primaryBackupDest, backupToolConfig.exclude)
        }

        println("Primary backup finished!")
    }

    private fun executeRsync(originalPath: String, backupDest: String, exclude: List<String>) {
        val command = mutableListOf("rsync", "-avz")
        command.addAll(exclude.map { "--exclude=$it" })
        command.add(originalPath)
        command.add(backupDest)

        println("Executing command: $command")
        val process = ProcessBuilder(command).start()
        BufferedReader(InputStreamReader(process.inputStream))
            .lines()
            .forEach { println(it) }

        val exitCode = process.waitFor()
        if (exitCode == 0) {
            println("Command succeeded: $command")
        } else {
            println("Command failed: $command")
            throw RuntimeException("Primary backup failed")
        }
    }
}
