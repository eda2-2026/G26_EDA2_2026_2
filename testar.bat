@echo off
setlocal
chcp 65001 >nul

call "%~dp0executar.bat" --somente-compilar
if errorlevel 1 exit /b 1

set "PASTA_BUILD=%TEMP%\geek-byte-brew-build"
set "JAVA_TEXTO=-Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -Dsun.stderr.encoding=UTF-8"

java %JAVA_TEXTO% -cp "%PASTA_BUILD%" testes.BuscaBinariaProdutosTest
if errorlevel 1 goto :erro
java %JAVA_TEXTO% -cp "%PASTA_BUILD%" testes.TelaBuscaProdutosTest
if errorlevel 1 goto :erro
java %JAVA_TEXTO% -cp "%PASTA_BUILD%" testes.BuscaBinariaClientesTest
if errorlevel 1 goto :erro
java %JAVA_TEXTO% -cp "%PASTA_BUILD%" testes.TabelaHashClientesTest
if errorlevel 1 goto :erro
java %JAVA_TEXTO% -cp "%PASTA_BUILD%" testes.TelaBuscaClientesTest
if errorlevel 1 goto :erro

echo.
echo Todos os testes foram aprovados.
pause
exit /b 0

:erro
echo.
echo Um teste falhou.
pause
exit /b 1
