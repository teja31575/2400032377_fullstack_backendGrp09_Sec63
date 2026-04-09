#!/usr/bin/env sh
set -eu

SCRIPT_DIR="$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)"
WRAPPER_PROPS="$SCRIPT_DIR/.mvn/wrapper/maven-wrapper.properties"

if [ ! -f "$WRAPPER_PROPS" ]; then
  echo "[ERROR] Missing $WRAPPER_PROPS"
  exit 1
fi

DIST_URL="$(awk -F= '/^distributionUrl=/{print $2}' "$WRAPPER_PROPS")"
if [ -z "$DIST_URL" ]; then
  echo "[ERROR] distributionUrl not found in maven-wrapper.properties"
  exit 1
fi

MAVEN_USER_HOME="${MAVEN_USER_HOME:-$HOME/.m2}"
WRAPPER_HOME="$MAVEN_USER_HOME/wrapper/dists"
DIST_FILE="$WRAPPER_HOME/apache-maven-bin.zip"
MAVEN_HOME="$WRAPPER_HOME/apache-maven"

mkdir -p "$WRAPPER_HOME"

if [ ! -x "$MAVEN_HOME/bin/mvn" ]; then
  echo "Downloading Maven distribution..."
  if command -v curl >/dev/null 2>&1; then
    curl -fsSL "$DIST_URL" -o "$DIST_FILE"
  elif command -v wget >/dev/null 2>&1; then
    wget -q "$DIST_URL" -O "$DIST_FILE"
  else
    echo "[ERROR] Need curl or wget to download Maven"
    exit 1
  fi

  echo "Extracting Maven..."
  rm -rf "$MAVEN_HOME"
  mkdir -p "$MAVEN_HOME"
  unzip -q -o "$DIST_FILE" -d "$WRAPPER_HOME"
  for d in "$WRAPPER_HOME"/apache-maven-*; do
    if [ -x "$d/bin/mvn" ]; then
      rm -rf "$MAVEN_HOME"
      mv "$d" "$MAVEN_HOME"
      break
    fi
  done
fi

exec "$MAVEN_HOME/bin/mvn" "$@"

