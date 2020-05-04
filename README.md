# google-search-cli

inspired by https://github.com/jarun/googler

query google from terminal. while querying, you can add search hits to a basket.
the url of all hits in this basket can be piped as argument to an application/script of your choice. search result pages get buffered in background. the query history can be browsed with: [ctrl+r, arrow-up, arrow-down] 

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

to install a prebuild linux-amd64 binary run:
```
curl -fsSL https://raw.githubusercontent.com/my5t3ry/google-search-cli/master/install-linux-amd64.sh | bash
```                    

to build and install a native-image run (maven needed):
```
./make-and-install.sh
```

to rebuild graal config run (maven needed):
```
./build-graal-config.sh
```                                       

screenshot:
![screenshot](https://raw.githubusercontent.com/my5t3ry/google-search-cli/master/doc/screenshot.png "Logo Title Text 1")
                          






