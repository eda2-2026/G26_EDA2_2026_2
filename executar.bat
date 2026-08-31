@echo off
setlocal EnableDelayedExpansion
chcp 65001 >nul

where javac >nul 2>nul
if errorlevel 1 (
    echo JDK 17 ou superior nao foi encontrado.
    echo Instale o JDK e tente novamente.
    pause
    exit /b 1
)

set "PASTA_BUILD=%TEMP%\geek-byte-brew-build"
set "LISTA_FONTES=%TEMP%\geek-byte-brew-fontes.txt"

if exist "%PASTA_BUILD%" rmdir /s /q "%PASTA_BUILD%"
mkdir "%PASTA_BUILD%"

> "%LISTA_FONTES%" (
    for /r "%~dp0br" %%F in (*.java) do (
        set "FONTE=%%~fF"
        echo "!FONTE:\=/!"
    )
    for /r "%~dp0testes" %%F in (*.java) do (
        set "FONTE=%%~fF"
        echo "!FONTE:\=/!"
    )
)

javac --release 17 -encoding UTF-8 -d "%PASTA_BUILD%" @"%LISTA_FONTES%"
if errorlevel 1 (
    echo Nao foi possivel compilar o projeto.
    pause
    exit /b 1
)

if /I "%~1"=="--somente-compilar" exit /b 0

start "" javaw -cp "%PASTA_BUILD%" br.edu.cafeteria.app.Main
exit /b 0
