#!/bin/bash

(cd privateService && docker build -t private-service .)
(cd publicService && docker build -t public-service .)

aws ecr create-repository \
    --repository-name private-service \
    --image-scanning-configuration scanOnPush=true \
    --region us-east-2 \
    > private-url.txt

PRIVATEURI=`cat private-url.txt | grep 'repositoryUri' | awk '{gsub(/"/, ""); gsub(/,/, ""); print $2}'`

aws ecr create-repository \
    --repository-name public-service \
    --image-scanning-configuration scanOnPush=true \
    --region us-east-2 \
    > public-url.txt

PUBLICURI=`cat public-url.txt | grep 'repositoryUri' | awk '{gsub(/"/, ""); gsub(/,/, ""); print $2}'`

echo $PUBLICURI
echo $PRIVATEURI

rm public-url.txt
rm private-url.txt

aws ecr get-login-password --region us-east-2 \
    | docker login --username AWS --password-stdin $PUBLICURI

docker tag private-service:latest $PRIVATEURI
docker push $PRIVATEURI

docker tag public-service:latest $PUBLICURI
docker push $PUBLICURI

aws cloudformation create-stack --stack-name "container-lab" \
    --template-body file://cloudformation/vpc_stack.json \
    --parameters ParameterKey=AvailabilityZone1,ParameterValue=us-east-2a \
    ParameterKey=AvailabilityZone2,ParameterValue=us-east-2b
