# Environment and Sample Application

This post describes a sample microservice based web application and a cloud stack meant for use with our tutorials on launching web applications using [AWS Elastic Container Service](../ecs) and [Elastic Kubernetes Service.](../eks) 

Follow the instructions in this post before starting either of those tutorials. 

## The Application
We've put together a simple microservice based application for demonstrating the features of each container orchestration system. The app consists of two microservices. The first service is exposed to the internet where it accepts requests and passed them on to the backend service. Therefore, both services have the same API but perform different tasks. We have exposed four command in our API, health, greeting, crash, and infinite. 

|Endpoint  |Service 1 Action  |Service 2 Action  |
|:---------|:-----------------|:-----------------|
|/health| Returns true when running| Returns true when running|
|/greeting| Forwards greeting request to backend. Repeats the greeting provided by backend when successful or displays an error message. | Returns its UUID and a greeting|
|/crash| Forwards crash request to backend. Repeats confirmation or returns an error message| Returns its UUID and a confirmation of receipt. Simulates a container crash by exiting the program|
|/infinite| Forwards infinite request to the backend. Repeats confirmation or returns an error message| Returns its UUID and a confirmation of receipt. Simulates a heavy workload by starting a thread with an infinite loop

A sequence diagram showing the service interaction is shown below. 

![Sequence diagram showing the relationship between services][Sequence]

[Sequence]: img/sequence.png "A sequence diagram showing the relationship between services"

## The Environment
We will deploy our application into a virtual private cloud. We've provided an AWS CloudFormation script to build this infrastructure for you. At the top level we have a private IP address space with four subnetworks across two availability zones. The network access control lists (NACL) block access to the internet for the 2 private subnetworks and the route tables allow outgoing traffic to the NAT Gateway in the public subnet. Access to our public facing service will be through an application load balancer with targets in the public subnet in each availability zone. 

Note that this environment is highly available. We will have redundant instances of our service in different availability zones. In the case that there is an outage in one of AWS's availability zones our service will continue to serve traffic. 

![A high availability VPC][VPC]

[VPC]: img/cloud-setup.png "A high availability VPC"

### Environment Setup
Things you will need:
* An AWS Account with permissions to create the resources described in the lab. 
* [AWS Command Line Interface Tool](https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-install.html)
* [Docker Desktop](https://docs.docker.com/get-docker/)

We've provided a shell script to setup and teardown the services required for the lab. Windows users can perform equivalents in the order provided in the script. 

```setup-environment.sh```

When the setup is complete, log into the AWS console and confirm you have a new VPC and repositories in the Elastic Container Registry for frontend-service and backend-service.  Take note of the URLs for these repositories. 

After you've completed the tutorials, tear down and delete the repositories at the end with ```teardown-environment.sh```. 