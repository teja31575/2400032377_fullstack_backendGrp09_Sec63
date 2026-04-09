@ECHO OFF
SETLOCAL

SET "SCRIPT_DIR=%~dp0"
SET "WRAPPER_PROPS=%SCRIPT_DIR%.mvn\wrapper\maven-wrapper.properties"

IF NOT EXIST "%WRAPPER_PROPS%" (
  ECHO [ERROR] Missing %WRAPPER_PROPS%
  EXIT /B 1
)

FOR /F "tokens=1,* delims==" %%A IN (%WRAPPER_PROPS%) DO (
  IF "%%A"=="distributionUrl" SET "DIST_URL=%%B"
)

IF "%DIST_URL%"=="" (
  ECHO [ERROR] distributionUrl not found in maven-wrapper.properties
  EXIT /B 1
)

SET "MAVEN_USER_HOME=%USERPROFILE%\.m2"
SET "WRAPPER_HOME=%MAVEN_USER_HOME%\wrapper\dists"
SET "DIST_FILE=%WRAPPER_HOME%\apache-maven-bin.zip"
SET "MAVEN_HOME=%WRAPPER_HOME%\apache-maven"

IF NOT EXIST "%WRAPPER_HOME%" MKDIR "%WRAPPER_HOME%"

IF NOT EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
  ECHO Downloading Maven distribution...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "[Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12; Invoke-WebRequest -UseBasicParsing -Uri '%DIST_URL%' -OutFile '%DIST_FILE%'" || EXIT /B 1

  ECHO Extracting Maven...
  powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "Expand-Archive -Path '%DIST_FILE%' -DestinationPath '%WRAPPER_HOME%' -Force" || EXIT /B 1

  FOR /D %%D IN ("%WRAPPER_HOME%\apache-maven-*") DO (
    IF EXIST "%%D\bin\mvn.cmd" (
      IF EXIST "%MAVEN_HOME%" RMDIR /S /Q "%MAVEN_HOME%"
      REN "%%D" "apache-maven"
      GOTO :runMaven
    )
  )
)

:runMaven
IF NOT EXIST "%MAVEN_HOME%\bin\mvn.cmd" (
  ECHO [ERROR] Maven extraction failed.
  EXIT /B 1
)

CALL "%MAVEN_HOME%\bin\mvn.cmd" %*
EXIT /B %ERRORLEVEL%

