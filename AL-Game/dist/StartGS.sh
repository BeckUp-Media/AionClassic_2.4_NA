#!/bin/bash

echo "Starting Aion 2.4 Classic Game Server."

/usr/lib/jvm/zulu8/bin/java -Xmx8g -ea -cp ./lib/*:./lib/AL-Game.jar com.aionemu.gameserver.GameServer

if [ $? -eq 2 ]; then
echo "Administrator Restart..."
bash $0
elif [ $? -eq 1 ]; then
echo "Server terminated abnormally..."
else
echo "Server terminated..."
fi
