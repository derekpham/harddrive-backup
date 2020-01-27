#!/usr/bin/python

import sys

from config_parser import ConfigParser
from backup_service import RsyncBackupService


def main():
    config_path = sys.argv[1]
    raw_config = open(config_path).read()
    backup_config = ConfigParser.str_to_backup_context(raw_config)
    print(backup_config)

    RsyncBackupService(backup_config).run()


if __name__ == '__main__':
    main()
