import os
from typing import Dict

import constants


class RsyncBackupService:
    """
    Backup config is a dictionary with the following keys:
    constants.HOME_DIR -> str
    constants.EXTERNAL_HARD_DRIVE_DEST -> str
    constants.BACKUP -> [(str, str)]
    """
    def __init__(self, backup_config: Dict):
        self.backup_config = backup_config

    def run(self):
        for source, dest in self.backup_config[constants.BACKUP]:
            source_absolute = self._absolute_path(
                source,
                self.backup_config[constants.HOME_DIR],
            )
            dest_absolute = self._absolute_path(
                dest,
                self.backup_config[constants.EXTERNAL_HARD_DRIVE_DEST],
            )
            print('Backing {} to {}'.format(source_absolute, dest_absolute))

    def _absolute_path(self, path: str, possible_prepend: str) -> str:
        if not self._is_absolute(path):
            return possible_prepend + '/' + path
        return path

    def _is_absolute(self, path: str) -> bool:
        return os.path.abspath(path) == path  # might be buggy?
