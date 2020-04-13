# What is this?
This is a backup tool that is consisted of multiple backup features:
- Backup your files from your computer to your external hard drive
- Upload your external hard drive backup to a secondary backup location
(Amazon S3)
- Give analytics about backup: number of files, total space, backup cost

# Example config file
```yaml
original-paths:
- dir1
- dir2

primary-backup-dest: backup-dest

exclude:
- ignore_dir1
- ignore_dir2

dirs-to-tar-in-secondary-backup:
- tar_dir1
- tar_dir2
```

# Semantics
- Files will be backed up from your computer to your physical hard drive
using `rsync`. It will literally invoke an `rsync` command, with the
exact exclude options.
- You can choose to create tars for certain directories when they are
uploaded to secondary backup storage to save costs. These directories
ideally never change in content anymore. The tool will create a temporary
tarball of the folder, upload them to secondary backup location, and
remove the tarball locally.

# How to use the tool
- `backup-tool --conf conf.yaml --actions primary-backup upload-to-s3`<br/>
Backing up to your external hard drive and then upload to s3
`backup-tool --conf conf.yaml --actions stats`<br/>
Give stats about your primary backup location + costs to have them on s3
