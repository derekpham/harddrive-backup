import constants


class RsyncBackupService:

    """
    Backup config is a dictionary with the following keys:
    constants.HOME_DIR -> str
    constants.EXTERNAL_HARD_DRIVE_DEST -> str
    constants.BACKUP -> [(str, str)]
    """
    def __init__(self, backup_config):
        self.backup_config = backup_config

    def run(self):
        for source, dest in self.backup_config[constants.BACKUP]:
            print('Backing up {} to {}'.format(source, dest))
