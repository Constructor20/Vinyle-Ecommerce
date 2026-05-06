@echo off
setlocal EnableDelayedExpansion

set WRAPPER_JAR="%CD%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_PROPERTIES="%CD%\.mvn\wrapper\maven-wrapper.properties"

if not exist %WRAPPER_JAR% (
    echo Downloading maven-wrapper.jar...
    powershell -Command "(New-Object Net.WebClient).DownloadFile('https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.2/maven-wrapper-3.3.2.jar', '%CD%\.mvn\wrapper\maven-wrapper.jar')"
)

if not exist %WRAPPER_PROPERTIES% (
    echo maven-wrapper.properties not found.
    exit /b 1
)

for /f "usebackq tokens=1,2 delims==" %%i in (`type %WRAPPER_PROPERTIES%`) do (
    if "%%i"=="distributionUrl" set DISTRIBUTION_URL=%%j
    if "%%i"=="wrapperUrl" set WRAPPER_URL=%%j
)

if not defined DISTRIBUTION_URL (
    echo distributionUrl not found in %WRAPPER_PROPERTIES%
    exit /b 1
)

set MAVEN_HOME=%USERPROFILE%\.m2\wrapper\dists\apache-maven\

if not exist "%MAVEN_HOME%" (
    echo Downloading Maven...
    powershell -Command "Invoke-WebRequest -Uri '%DISTRIBUTION_URL%' -OutFile '%TEMP%\apache-maven.zip'; Expand-Archive -Path '%TEMP%\apache-maven.zip' -DestinationPath '%MAVEN_HOME%' -Force"
)

for /d %%i in ("%MAVEN_HOME%\apache-maven-*") do set MAVEN_DIR=%%i

"%MAVEN_DIR%\bin\mvn.cmd" %*
