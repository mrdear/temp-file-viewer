#!/usr/bin/env bash

# 构建前端
(cd temp-file-viewer-frontend && yarn build)

# 构建后端
(cd temp-file-viewer-backend && mvn clean package)

# 构建docker
(docker build -t ifreehub/temp-file-viewer:1.0 .)
