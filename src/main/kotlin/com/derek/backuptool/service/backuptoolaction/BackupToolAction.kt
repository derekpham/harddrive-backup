package com.derek.backuptool.service.backuptoolaction

import com.derek.backuptool.dto.BackupToolConfig

interface BackupToolAction {

    fun performAction(backupToolConfig: BackupToolConfig)
}
