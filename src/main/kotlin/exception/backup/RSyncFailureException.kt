package exception.backup

class RSyncFailureException(original: String, backupPath: String)
    : Exception("rsync failed. original: $original. backupPath: $backupPath")