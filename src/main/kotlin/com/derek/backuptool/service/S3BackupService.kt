package com.derek.backuptool.service

import com.derek.backuptool.dto.BackupToolConfig
import com.derek.backuptool.service.BackupToolService
import javax.inject.Inject

class S3BackupService @Inject constructor() : BackupToolService {
    override fun performService(backupToolConfig: BackupToolConfig) {
        TODO("Not yet implemented")
    }
}
