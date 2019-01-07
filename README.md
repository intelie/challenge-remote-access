# Accessing a remote service

In this challenge, you will propose a way to deploy and access a service running on a remotely blocked port.

Consider the architecture proposed in the representation below:
 
![alt text](https://github.com/intelie/challenge-remote-access/raw/master/example%20network%20infrastructure.png "Example network infrastructure")

The only allowed connection between Client A and Server A is via SSH.
The only allowed connection between Server A and Server B is via SSH.

We need to:

- Deploy a web service on Server B, running an Ansible script from Client A
- Then we will access this service from Client A using HTTP

Both client and servers run CentOS 7 without X.

We expect you to:
* Provide commands that create the test machines, they can be local VMs or instances in any cloud provider.
* Create an Ansible playbook that deploys the web service and its dependencies (described below). Make sure to create a setup that allows Ansible to run on Client A with Server B as the target host.
* Provide the set of commands that allow the service to be accessed from Client A, given the network restrictions in this environment.
* Provide a document, in english, explaining your solution: what each script/command does, their possible outputs, including failures, why you chose them, etc. Don't forget to describe how to make test calls from the client.

## The web service

We are going to deploy a Sensor Data service. It is very simple:

- To save a measurement, the user sends a JSON as POST to [host]:8000/measures. The JSON contains the name of the sensor that did the measurement, the timestamp and the measured value. This application does not consider the unit for each measurement, they are just "values"

Examples
```
{"sensor": "Motor 1 Temperature", "timestamp":"2019-01-06_09:35:40", "value": 90}
{"sensor": "Motor 1 Temperature", "timestamp":"2019-01-06_09:35:47", "value": 94}
{"sensor": "Motor 1 RPM",         "timestamp":"2019-01-07_09:30:28", "value": 5000}
{"sensor": "Valve XYZ Pressure",  "timestamp":"2019-01-07_10:12:56", "value": 52}
etc
```

- After saving some measurements the user can generate a report displaying the average value per minute. To generate the report call [host]:8000/minute-report

Example output:
```
--------------------------------------------------------------------------------------------
|Sensor                             |Minute           |Num of measures|           Avg value|
--------------------------------------------------------------------------------------------
|Motor 1 RPM                        |2019-01-07 09:30 |              1|               5,000|
|Motor 1 Temperature                |2019-01-06 09:35 |              2|                  92|
|Valve XYZ Pressure                 |2019-01-07 10:12 |              1|                  52|
--------------------------------------------------------------------------------------------
```

The Java package that implements this service is already provided under the `app/release/` directory.

To test it locally you must have Java (recommended version 8) and MongoDB installed. Enter the `app/release/` directory and run `java -jar [name of the jar file]` to start the service. Then Use your preferred HTTP client to make test calls

What is expected in the server deployment:

Your Ansible script must install Java and MongoDB, deploy the application package and configure the application to start as a Service.


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
