# jabackup
Java application that creates differential but continuous backups.

### Description
**jabackup** is a java application that implements a new kind of backup. It  works similar to a differential backup, which creates a full backup and compares the following backups with the  first one, but instead, it compares every following backup with the previous one (e.g instead of comparing backup #1, #2, #3... with backup #0, it will compare backup #1 with backup #0, #2 with #1, #3 with #2, etc). To preserve redundance, it will also create a full backup every X backups.
