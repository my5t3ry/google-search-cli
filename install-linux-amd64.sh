#!/usr/bin/env bash
rm google-search-cli
wget https://github.com/my5t3ry/google-search-cli/raw/master/dist/linux-amd64/google-search-cli
chmod +x google-search-cli
sudo cp google-search-cli /usr/local/bin/
