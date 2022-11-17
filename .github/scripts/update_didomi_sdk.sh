#!/bin/bash

#----------------------------------------------------------
# Update Android SDK version (latest from repos)
#----------------------------------------------------------

# Update android SDK Version
androidVersion=$(curl -s 'https://search.maven.org/solrsearch/select?q=didomi' | sed -n 's|.*"latestVersion":"\([^"]*\)".*|\1|p')
if [[ ! $androidVersion =~ ^[0-9]+.[0-9]+.[0-9]+$ ]]; then
  echo "Error while getting android SDK version ($androidVersion)"
  exit 1
fi

echo "Android SDK last version is $androidVersion"

sed -i~ -e "s|io.didomi.sdk:android:[0-9]\{1,2\}.[0-9]\{1,2\}.[0-9]\{1,2\}|io.didomi.sdk:android:$androidVersion|g" app/build.gradle.kts || exit 1

# Cleanup backup files
find . -type f -name '*~' -delete
