@echo off
set caminhoComponente=%1%
set opcaoCommit=%2%
cd %caminhoComponente%
mvn %opcaoCommit%