#!/usr/bin/env bash

usingNonMainBranch=false

ls /usr/bin/

commitHash=$("/usr/bin/git rev-parse --short HEAD")
branchName=$("/usr/bin/git rev-parse --abbrev-ref HEAD")

version=""

if [[ $branchName == "main" ]]; then
    usingNonMainBranch=false
fi

version="$($commitHash)"

if [[ $usingNonMainBranch = true ]]; then
    version="$($version)-$($branchName)"
fi

echo "VersionID=$($version)" >> "$GITHUB_OUTPUT"