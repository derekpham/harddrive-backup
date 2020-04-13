package com.derek.backuptool.dto

data class BackupToolArguments(
    val confFile: String,
    val actions: List<String>
)
