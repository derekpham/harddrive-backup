import json
from json import JSONDecodeError
import constants
import sys


class Parser:

    @staticmethod
    def str_to_backup_context(raw):
        try:
            context = json.loads(raw)
        except JSONDecodeError as e:
            print('Not a valid json. No operation performed.')
            raise e

        if constants.HOME_DIR not in context \
                or constants.EXTERNAL_HARD_DRIVE_DEST not in context \
                or constants.BACKUP not in context:
            print('Not a valid config file.')
            sys.exit(1)

        return context
