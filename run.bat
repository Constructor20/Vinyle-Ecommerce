@echo off
title Vinyl Store
chcp 65001 >nul

echo ============================================
echo   Vinyl Store - Lancement
echo ============================================
echo.

REM Vérifier qu'on est bien dans le dossier du projet
if not exist "pom.xml" (
    echo ERREUR : Placez ce fichier a la racine du projet Vinyl Store
    pause
    exit /b 1
)

REM Vérifier que Java 21+ est installé
java -version >nul 2>&1
if %ERRORLEVEL% NEQ 0 (
    echo ERREUR : Java n'est pas installe. Installez Java 21 depuis https://adoptium.net/
    pause
    exit /b 1
)

REM Build la fat JAR si elle n'existe pas
if not exist "target\vinyl-store.jar" (
    echo [1/2] Compilation du projet...
    call .\mvnw.cmd package -q
    if %ERRORLEVEL% NEQ 0 (
        echo ERREUR lors de la compilation
        pause
        exit /b 1
    )
    echo OK
)

REM Vérifier JavaFX SDK
set JAVAFX_DIR=javafx-sdk-21.0.9
if not exist "%JAVAFX_DIR%" (
    echo [2/2] Telechargement de JavaFX SDK (21.0.9 Windows)...
    powershell -Command "& {Invoke-WebRequest -Uri 'https://download2.gluonhq.com/openjfx/21.0.9/openjfx-21.0.9_windows-x64_bin-sdk.zip' -OutFile 'javafx-sdk.zip'}"
    if exist "javafx-sdk.zip" (
        powershell -Command "& {Expand-Archive -Path 'javafx-sdk.zip' -DestinationPath '.' -Force}"
        del javafx-sdk.zip
        REM Renommer le dossier
        if exist "javafx-sdk-21.0.9" (echo OK) else (
            for /d %%i in (javafx-sdk-*) do ren "%%i" "javafx-sdk-21.0.9"
        )
    ) else (
        echo ERREUR : Impossible de telecharger JavaFX SDK
        pause
        exit /b 1
    )
)

echo.
echo Demarrage...
echo.

java --module-path "%JAVAFX_DIR%\lib" --add-modules javafx.controls,javafx.fxml -jar "target\vinyl-store.jar"

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo L'application s'est arretee. Verifiez que MySQL est lance dans XAMPP.
    pause
)
