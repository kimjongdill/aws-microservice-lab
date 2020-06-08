#!/bin/bash

aws cloudformation delete-stack --stack-name container-lab

aws ecr delete-repository \
    --force \
    --repository-name frontend-service

aws ecr delete-repository \
     --force \
     --repository-name backend-service 
