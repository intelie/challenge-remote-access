# Accessing a remote service

In this challenge, you will propose a way to access a service running on a remotely unreachable port.

Consider the architecture proposed on the figure below:
 
![alt text](https://github.com/intelie/challenge-remote-access/raw/master/example%20network%20infrastructure.png "Example network infrastructure")

The only allowed connection between Client A and Server A is via SSH.
The only allowed connection between Server A and Server B is via SSH.

We need to access, from Client A and using HTTP, a service running on port 8000 of Server B.

Notes and constraints:
- There is no direct route from Client A to Server B, and no practical way to build one. Imagine the following scenario as example: Server B belongs to a customer internal datacenter, and we were provided with a VPN that allows access to Server A SSH port only.
- There is another service running on port 8000 of Server A, we must not cause impacts on this one

Both client and servers run CentOS 7 without X.

We expect you to:
* Provide the set of commands that allow the service to be accessed
* Provide a document, in english, explaining each of the commands, their possible outputs, including failures, and why you chose them
* You don't need to be concerned about a production-level solution, users will not be accessing the service in a regular basis. You just need to provide an ad-hoc access (as in the typical scenario: support analyst needs to access web interface for some configuration task)
* However it will be welcome to have a short comment on how you would change the architecture/solution to provide permanent access, in the case users in Local Network start to perform heavy usage of application in Server B.

## Solve this challenge

To solve this challenge, you may fork this repository, then 
send us a link with your implementation. Alternatively, if you do not want to have this repo on
your profile (we totally get it), send us a 
[git patch file](https://www.devroom.io/2009/10/26/how-to-create-and-apply-a-patch-with-git/) 
with your changes.

There is no unique solution to this challenge. The intent is to evaluate candidate's ability and familiarity with tools and best practices.

If you are already in the hiring process, you may send it to 
 whoever is your contact at Intelie. If you wish to apply for a job at 
 Intelie, please send your solution to [trabalhe@intelie.com.br](mailto:trabalhe@intelie.com.br).
