package com.derek.backuptool.service

import com.derek.backuptool.dto.BackupToolConfig

interface BackupToolService {

    fun performService(backupToolConfig: BackupToolConfig)
}
