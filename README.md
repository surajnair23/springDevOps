# springDevOps
adding the complete spring boot app with application properties for docker IP, k8s and localhost

Open project and make changes to application.properties with the configuration for the system you want to run it in.

To build image:
Make sure Maven install - skip tests is On
Run maven install
Head into target folder and run the following commands:
``docker build -f Dockerfile -t <dockerhub/repo> .

``docker push <dockerhub/repo>
