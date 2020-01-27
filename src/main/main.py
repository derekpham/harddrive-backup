#!/usr/bin/python

import sys
import argparse

from config_parser import ConfigParser
from backup_service import RsyncBackupService


def get_args():
    arg_parser = argparse.ArgumentParser(description='Backs up files and folders using RSync '
                                                     'and potentially some cloud storage '
                                                     '(like S3 Glacier?)')
    arg_parser.add_argument('backup_config_file', metavar='C', type=str,
                            help='name of your backup config')
    arg_parser.add_argument('--dry-run', dest='dry_run', action='store', default=False,
                            help='if passed will just print out the input and output paths')
    return arg_parser.parse_args()


def main():
    args = get_args()
    print(args)
    """raw_config = open(config_path).read()
    backup_config = ConfigParser.str_to_backup_context(raw_config)
    print(backup_config)

    RsyncBackupService(backup_config).run()"""


if __name__ == '__main__':
    main()
