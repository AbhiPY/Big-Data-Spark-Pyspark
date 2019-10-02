echo "`hostname` `date +%d%m%y,%H:%M` ` sar -r 1 59 |tail -1 `" >> /home/hadoop/logs/`date +%m-%y`-memory-sar.txt &
