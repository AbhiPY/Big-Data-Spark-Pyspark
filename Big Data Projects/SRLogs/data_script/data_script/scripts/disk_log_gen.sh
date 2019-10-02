echo "`hostname` `date +%d%m%y,%H:%M` `df -h |head -3|tail -1 `" >> /home/hadoop/logs/`date +%m-%y`-disk-sar.txt &
