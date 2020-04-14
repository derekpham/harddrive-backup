package com.derek.backuptool.dto

data class BackupToolConfig(
    val originalPaths: List<String>,
    val primaryBackupDest: String,
    val exclude: List<String>,
    val dirsToTarInSecondaryBackup: List<String>
)
