from yaml import load
from yaml import Loader

import constants


class InvalidConfigError(ValueError):
    pass


class Parser:

    @staticmethod
    def str_to_backup_context(raw):
        context = load(raw, Loader=Loader)

        required_constants = {
            constants.HOME_DIR,
            constants.EXTERNAL_HARD_DRIVE_DEST,
            constants.BACKUP
        }
        for required_constant in required_constants:
            if required_constant not in context:
                raise InvalidConfigError('Config file needs to specify "{}". '
                                         'Please check out the dummy config for example.'
                                         .format(required_constant))

        source_dest_pairs = [
            next(iter(source_dest_dict.items())) for source_dest_dict in context[constants.BACKUP]
        ]
        context[constants.BACKUP] = source_dest_pairs
        return context
