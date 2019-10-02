echo "`hostname` `date +%d-%m-%y,%H:%M` ` sar 1 60 |tail -1 `" >> /home/hadoop/logs/`date +%m-%y`-cpu-sar.txt &
