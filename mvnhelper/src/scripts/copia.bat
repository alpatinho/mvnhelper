@echo off
set caminhoOrigem=%1%
set caminhoDestino=%2%
@echo on
copy /Y %caminhoOrigem%\*.exe %caminhoDestino%