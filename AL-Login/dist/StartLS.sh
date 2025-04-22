#!/bin/bash

echo "Starting Aion 2.4 Classic Login Server."

/usr/lib/jvm/zulu8/bin/java -Xms64m -Xmx128m -ea -Xbootclasspath/p:./lib/jsr166-1.7.0.jar -cp ./lib/*:AL-Login.jar com.aionemu.loginserver.LoginServer

if [ $? -eq 2 ]; then
echo "Administrator Restart..."
bash $0
elif [ $? -eq 1 ]; then
echo "Server terminated abnormally..."
else
echo "Server terminated..."
fi
