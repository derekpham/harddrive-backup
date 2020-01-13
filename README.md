Backs up files using rsync as backend

## How to run
- run `make`, which will output executable `backup`
- run `./backup path-to-your-config-file`
- add this command to startup file, cron, ... to backup files regularly

## Config file format
YAML format

```YAML
external-hard-drive-dest: path-to-your-external-hard-drive
home-dir: path-to-your-home-dir

backup:
  - src1: dest1
  - src1: dest2
  ...
``` 

If your source path is relative,
the path used will be `home-dir + sourcePath`\
If your destination path is relative,
 the path used will be `external-hard-drive-dest + destPath`

