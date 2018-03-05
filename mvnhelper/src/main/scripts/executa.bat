set setbanco1=%1%
set setbanco2=%2%
set caminhoExecucao=%3%
set caminhoComExecutavel=%4%
set agencia=%5%
call cd %caminhoExecucao%
call %setbanco1% %setbanco2% 
timeout 3 > NUL
call r:\exes\te.exe %caminhoExecucao% %caminhoComExecutavel% %agencia% 