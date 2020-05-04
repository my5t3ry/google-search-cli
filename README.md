# google-search-cli

the idea behind this application is to search for one or multiple topics. while searching, you can add search hits to a basket.
the url of all selected hits can be piped as argument to an application/script of your choice. following search result pages get buffered in background. the query history can be browsed with: [ctrl+r, arrow-up, arrow-down] 

a local configuration file can be placed at $HOME/.google-search-cli/config.properties

the default configuration is:
```
open-url-command=chromium
open-url-command.silent=/home/my5t3ry/tools/sh/openurlsilent.sh
command.exit=e
command.help=h,help
command.next=n
command.previous=p
command.clear-basket=c
command.open-basket=o
command.open-basket-silent=os
```                          

make and install is currently only supported for linux. to build and install as native-image run:
```
./make-and-install.sh
```                         

a prebuild linux-amd64 binary can be downloaded:
```
wget https://github.com/my5t3ry/google-search-cli/raw/master/dist/linux-amd64/google-search-cli
```





