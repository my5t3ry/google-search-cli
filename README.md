# google-search-cli

inspired by https://github.com/jarun/googler

query google from terminal. manipulate your queries: [ctrl+r, arrow-up, arrow-down]. consume your basket. 

a local configuration file can be placed at $HOME/.google-search-cli/config.properties

the default configuration is:
```
open-url-command=qutebrowser
open-url-command.silent=qutebrowser --target=tab-silent
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
                
download jar (jre 11 needed):
[jar](https://github.com/my5t3ry/google-search-cli/raw/master/dist/jar/google-search-cli.jar)

to build and install a native-image on linux run (maven needed):
```
./make-and-install.sh
```

to rebuild graal config run (maven needed):
```
./build-graal-config.sh
```                                       

screenshot:
![screenshot](https://raw.githubusercontent.com/my5t3ry/google-search-cli/master/doc/screenshot.png "Logo Title Text 1")
                          






