@ECHO off
TITLE Aion 2.4 Classic - Login Server Console
REM Set correct Path, im using Zulu8
SET PATH="..\Zulu8\bin"
@COLOR 0C

:START
CLS

echo.

echo Starting Aion 2.4 Classic Login Server.

echo.

REM -------------------------------------
REM Default parameters for a basic server.
java -cp ./lib/*;AL-Login.jar com.aionemu.loginserver.LoginServer
REM -------------------------------------
SET CLASSPATH=%OLDCLASSPATH%

if ERRORLEVEL 2 goto restart
IF ERRORLEVEL 1 GOTO ERROR
IF ERRORLEVEL 0 GOTO END

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
