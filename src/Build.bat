@echo off

setlocal enabledelayedexpansion

set destination=C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps
set /p projet="Nom du projet : "
set temp_destination="temp\WEB-INF\classes"
set webinf="temp\WEB-INF"
set class="temp\WEB-INF\classes"
set lib="temp\WEB-INF\lib"
set assets="temp\assets"
set src="target\*.java"
set archive=%projet%.war

if exist "%destination%%archive%" (
    del /S /Q "%destination%%archive%"
)
if exist "%destination%%projet%" (
    rmdir /S /Q "%destination%%projet%"
)

rmdir /S /Q temp
mkdir temp
mkdir %webinf%
mkdir %class%
mkdir %lib%
mkdir %assets%

xcopy conf\*.xml %webinf% /y
xcopy lib\*.jar %lib% /y
xcopy web\*.* temp /y
if exist assets xcopy assets %assets% /E /C /Y


set "dossier_principal=.\src"
set "target=target"

if not exist "%target%" mkdir "%target%"

for /r "%dossier_principal%" %%f in (*.java) do (
    set "chemin_complet=%%~f"
    set "nom_fichier=%%~nf.java"
    
    copy "!chemin_complet!" "%target%\!nom_fichier!" > nul
)

javac -g -cp lib\* -d "%class%" "%src%"

rmdir /S /Q target

jar -cvf %archive% -C temp\ .
move %archive% "%destination%"

endlocal