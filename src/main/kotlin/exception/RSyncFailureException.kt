package exception

class RSyncFailureException(original: String, backupPath: String)
    : Exception("rsync failed. original: $original. backupPath: $backupPath")