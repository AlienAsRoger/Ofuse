#!/usr/bin/env bash
# Reproduces a JitPack-style clean checkout build locally, before pushing a release tag.
#
# JitPack always builds cold (no cached .gradle/build dirs), and the 0.1.1 incident
# (see CLAUDE.md) showed a build that succeeds locally with warm caches can still fail
# on a genuinely clean one -- a config-cache serialization error only surfaced once
# .gradle/build were removed. Run this before tagging a release.
#
# Usage: ./scripts/verify-clean-build.sh

set -euo pipefail
cd "$(dirname "${BASH_SOURCE[0]}")/.."

echo "==> Removing .gradle and module build/ directories for a cold build..."
rm -rf .gradle
find . -maxdepth 2 -type d -name build -exec rm -rf {} +

echo "==> Running a clean build (matching JitPack's default gradle.properties settings)..."
./gradlew clean build

echo "==> Clean build succeeded."
