from typing import Dict
from yaml import load
from yaml import Loader

import constants


class InvalidConfigError(ValueError):
    pass


class Parser:

    @staticmethod
    def validate_config(config):
        required_constants = {
            constants.HOME_DIR,
            constants.EXTERNAL_HARD_DRIVE_DEST,
            constants.BACKUP
        }
        for required_constant in required_constants:
            if required_constant not in config:
                raise InvalidConfigError('Config file needs to specify "{}". '
                                         'Please check out the dummy config for example.'
                                         .format(required_constant))

        if not config[constants.HOME_DIR].startswith('/'):
            raise InvalidConfigError('Home directory must be absolute')
        if not config[constants.EXTERNAL_HARD_DRIVE_DEST].startswith('/'):
            raise InvalidConfigError('Destination directory must be absolute')

    @staticmethod
    def cleanup_dirs(config):
        if config[constants.HOME_DIR].endswith('/'):
            config[constants.HOME_DIR] = config[constants.HOME_DIR][:-1]
        if config[constants.EXTERNAL_HARD_DRIVE_DEST].endswith('/'):
            config[constants.EXTERNAL_HARD_DRIVE_DEST] = \
                config[constants.EXTERNAL_HARD_DRIVE_DEST][:-1]

    @staticmethod
    def str_to_backup_context(raw: str) -> Dict:
        config = load(raw, Loader=Loader)
        Parser.validate_config(config)

        source_dest_pairs = [
            next(iter(source_dest_dict.items())) for source_dest_dict in config[constants.BACKUP]
        ]
        config[constants.BACKUP] = source_dest_pairs

        Parser.cleanup_dirs(config)
        return config
