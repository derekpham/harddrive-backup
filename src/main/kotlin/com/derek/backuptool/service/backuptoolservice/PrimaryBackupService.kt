package com.derek.backuptool.service.backuptoolservice

import com.derek.backuptool.dto.BackupToolConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrimaryBackupService @Inject constructor() : BackupToolService {
    override fun performService(backupToolConfig: BackupToolConfig) {
        TODO("Not yet implemented")
    }
}
