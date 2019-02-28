@echo off
chcp 65001
choice /c abc
echo %errorlevel%
if %errorlevel% == 1 ( 
  echo a
)
if %errorlevel%==2 (
  echo b
)
if %errorlevel%==3 (
  echo c
)
pause