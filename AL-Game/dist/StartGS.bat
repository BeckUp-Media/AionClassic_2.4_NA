@echo off
TITLE Aion 2.4 Classic - Game Emu Console
REM Set correct Path, im using Zulu8
SET PATH="..\Zulu8\bin"
@COLOR 0A

:START
CLS

echo.

echo Starting Aion 2.4 Classic Game Server.

echo.

REM -------------------------------------
REM Default parameters for a basic server.
java -Xms2048m -Xmx8192m -XX:MaxHeapSize=8192m -Xdebug -XX:MaxNewSize=48m -XX:NewSize=48m -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseConcMarkSweepGC -ea:./lib/AL-Commons.jar -cp ./lib/*;./lib/AL-Game.jar com.aionemu.gameserver.GameServer
REM -------------------------------------
SET CLASSPATH=%OLDCLASSPATH%

if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
if ERRORLEVEL 0 goto end

REM Restart...
:restart
echo.
echo Administrator Restart ...
echo.
goto start

REM Error...
:error
echo.
echo Server terminated abnormaly ...
echo.
goto end

REM End...
:end
echo.
echo Server terminated ...
echo.
pause
