# Accessing a remote service

In this challenge, you will propose a way to access a service running on a remotely blocked port.

Consider the architecture proposed on the figure below:
 
![alt text](https://github.com/intelie/challenge-remote-access/raw/master/example%20network%20infrastructure.png "Example network infrastructure")

The only allowed connection between Client A and Server A is via SSH.
The only allowed connection between Server A and Server B is via SSH.

We need to access, from Client A and using HTTP, a service running on port 8000 of Server B.

Note:
> There is a service running on port 8000 of Server A.

Both client and servers run CentOS 7 without X.

We expect you to:
* Provide the set of commands that allow the service to be accessed
* Provide a document, in english, explaining each of the commands, their possible outputs, including failures, and why you chose them

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
