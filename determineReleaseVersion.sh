#!/usr/bin/env bash

usingNonMainBranch=false

commitHash=$(/usr/bin/git rev-parse --short HEAD)
branchName=$(/usr/bin/git rev-parse --abbrev-ref HEAD)

echo "Calculated Version (Commit Hash): '$($commitHash)'."
echo "Calculated Version (Branch Name): '$($branchName)'."

version=""

if [[ $branchName == "main" ]]; then
  echo "Not on Main branch."
  usingNonMainBranch=true
fi

version="$($commitHash)"

echo "Calculated Version (Stage 1): '$($commitHash)'."

if [[ $usingNonMainBranch = true ]]; then
  version="$($version)-$($branchName)"
fi

echo "Calculated Version (Final): '$($version)'."

echo "VersionID=$($version)" >> "$GITHUB_OUTPUT"