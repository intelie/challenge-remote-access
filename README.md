# Accessing a remote service

In this challenge, you will propose a way to deploy and access a service running on a remotely blocked port.

Consider the architecture proposed on the representation below:
 
![alt text](https://github.com/intelie/challenge-remote-access/raw/master/example%20network%20infrastructure.png "Example network infrastructure")

The only allowed connection between Client A and Server A is via SSH.
The only allowed connection between Server A and Server B is via SSH.

We need to:

- Deploy a web service on Server B, running an Ansible script from Client A
- Then we will access this service from Client A using HTTP

Both client and servers run CentOS 7 without X.

We expect you to:
* Provide commands that create the test machines, they can be local VMs or instances in any cloud provider.
* Create an Ansible playbook that deploys the web service and its dependencies (described below). Make sure that your setup allows Ansible to run on Client A with Server B as the target host.
* Provide the set of commands that allow the service to be accessed from Client A, given the network restrictions in this environment.
* Provide a document, in english, explaining your solution: what each script/command does, their possible outputs, including failures, why you chose them, etc. Don't forget to describe how to make test calls from the client.

## The web service

We are going to deploy a Save Documents service. It is very simple:

- To save a document, the user sends a JSON as POST to [host]:8000/docs. A document ID is generated and returned in the response
- To get a document, the user calls [host]:8000/docs/[document id]

The code is already provided under the `src` directory.

To test it locally you must have NodeJS and MongoDB installed. Enter the `src` directory, run `npm install` to install the dependencies, and `node savedocs.js` to start the service. Then Use your preferred HTTP client to make test calls

What is expected in the server deployment:

Your Ansible script must install NodeJS and MongoDB, deploy the application files and configure the application to start as a Service.


## Solve this challenge

To solve this challenge, you may fork this repository, then 
send us a link with your implementation. Alternatively, if you do not want to have this repo on
your profile (we totally get it), send us a 
[git patch file](https://www.devroom.io/2009/10/26/how-to-create-and-apply-a-patch-with-git/) 
with your changes or a zip containing the updated project.

There is no unique solution to this challenge. The intent is to evaluate candidate's ability and familiarity with tools and best practices.

If you are already in the hiring process, you may send it to 
 whoever is your contact at Intelie. If you wish to apply for a job at 
 Intelie, please send your solution to [trabalhe@intelie.com.br](mailto:trabalhe@intelie.com.br).
