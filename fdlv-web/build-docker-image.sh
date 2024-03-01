#!/bin/bash

# Read the  parameters
GITLAB_URL=$1
CI_PROJECT_PATH=$2
CI_SERVER_HOST=$3
GITLAB_USER_EMAIL=$4
CI_COMMIT_REF_NAME=$5
CI_JOB_TOKEN=$6
CI_REGISTRY_IMAGE=$7
POSTFIX=$8
CI_COMMIT_SHORT_SHA=$9

# Fail if parameters are not the same
if [ "$#" -ne 9 ]; then
  echo "Illegal number of parameters: $#"
  echo "Usage: $0 <GITLAB_URL> <CI_PROJECT_PATH> <CI_SERVER_HOST> <GITLAB_USER_EMAIL> <CI_COMMIT_REF_NAME> <CI_JOB_TOKEN> <CI_REGISTRY_IMAGE> <POSTFIX> <CI_COMMIT_SHORT_SHA>"
  exit 1
fi

# Set necessary environment variables
export DOCKER_DRIVER=overlay

# Login to docker
docker login -u gitlab-ci-token -p "$CI_JOB_TOKEN" registry.gitlab.com


# Configure Gitlab
git config --global user.name "Gitlab-pipeline"
git config --global user.email "${GITLAB_USER_EMAIL}"
git remote rm origin
git remote add origin git@"$CI_SERVER_HOST":"$CI_PROJECT_PATH".git

# Checkout the specified branch
git checkout -B "$CI_COMMIT_REF_NAME"

# Get the version of the project from the package.json
tag=$(cat package.json | grep version | grep -Eo "[[:digit:]]+\.[[:digit:]]+\.[[:digit:]]+").$CI_COMMIT_SHORT_SHA.$POSTFIX

# Create a tag in Gitlab
echo "Creating tag $tag in Gitlab from the version found in package.json..."
git tag "$tag"
git push origin "$tag" --no-verify

# The new docker names
image_full_name=$CI_REGISTRY_IMAGE:$tag
image_latest_name=$CI_REGISTRY_IMAGE:latest.$POSTFIX

echo "Image name $image_full_name"
echo "Image latest name $image_latest_name"

# Build the docker image
docker build --build-arg POSTFIX="$POSTFIX" -t "$image_full_name" -t "$image_latest_name" .

# Push the docker image
docker push "$image_full_name"
docker push "$image_latest_name"
