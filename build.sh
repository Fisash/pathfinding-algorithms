#!/bin/bash

DEST="app"
mkdir -p "$DEST"

if [ "$1" == "all" ]; then
    MODES=("linux" "win" "mac" "all-platforms")
    echo "Starting FULL holy shity release build (4 iterations)..."
else
    MODES=("all-platforms")
    echo "Starting build with only one graphic module for all natives"
fi

rm -r "$DEST"/*.jar

for MODE in "${MODES[@]}"; do
    echo ">>> Building profile: $MODE..."
    mvn package -P"$MODE" -DskipTests -q

    SEARCH_MODE=$MODE
    if [ "$MODE" == "win" ]; then SEARCH_MODE="windows"; fi
    if [ "$MODE" == "mac" ]; then SEARCH_MODE="macos"; fi
    if [ "$MODE" == "all-platforms" ]; then SEARCH_MODE="all"; fi

    cp graphic/target/path-finding-graphic-*-${SEARCH_MODE}.jar "$DEST/"
done

echo ">>> Copying console and core modules..."
cp console/target/path-finding-console-*.jar "$DEST/"
cp core/target/path-finding-core-*.jar "$DEST/"

echo "Release ready in /$DEST/ ---"
ls -lh "$DEST"
