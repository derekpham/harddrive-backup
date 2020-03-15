package exception.parser

import model.BackupConfig

class ValidatorFailureException(reason: String, config: BackupConfig) :
    Exception("Failed to validate config: $reason. Config: $config")
