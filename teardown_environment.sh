#!/bin/bash

aws cloudformation delete-stack --stack-name container-lab

aws ecr delete-repository \
    --force \
    --repository-name public-service

aws ecr delete-repository \
     --force \
     --repository-name private-service 
