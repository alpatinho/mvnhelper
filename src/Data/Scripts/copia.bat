@echo off
set DirOrigemExe=%1%
set DirDestinoExe=%2%
set DirExe = %3%
@echo on
del /y %DirExe%
copy /Y %DirOrigemExe% %DirDestinoExe%