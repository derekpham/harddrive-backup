package com.derek.backuptool.service.backuptoolservice

import com.derek.backuptool.dto.BackupToolConfig

interface BackupToolService {

    fun performService(backupToolConfig: BackupToolConfig)
}
