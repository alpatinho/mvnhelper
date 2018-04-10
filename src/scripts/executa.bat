@echo off
set setbanco=%1%
set Banco=%2%
set DirExecucao=%3%
set DirExeExecucao=%4%
set agencia=%5%
@echo on
call cd %DirExecucao%
call %setbanco% %Banco%
timeout 3 > NUL
call r:\exes\te.exe %DirExecucao% %DirExeExecucao% %agencia%