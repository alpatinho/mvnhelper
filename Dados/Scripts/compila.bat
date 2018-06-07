@echo off
set setBanco=%1%
set banco=%2%
set caminho=%3%
set cExe=%4%
set agencia=%5%
@echo on
call cd %setBanco%
call %banco% %caminho% %cExe% %agencia%