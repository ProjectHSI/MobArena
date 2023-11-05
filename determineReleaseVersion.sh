#!/usr/bin/env bash

usingNonMainBranch=false

commitHash=$("git rev-parse --short HEAD")
branchName=$("git rev-parse --abbrev-ref HEAD")

version=""

if [ $branchName == "main" ]; then
    usingNonMainBranch=false
fi

version="$(commitHash)"

if [ $usingNonMainBranch == true ]; then
    version="$(version)-$(branchName)"
fi

echo "VersionID=$(version)" >> "$GITHUB_OUTPUT"