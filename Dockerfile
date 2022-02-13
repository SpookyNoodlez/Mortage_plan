#Use OpenJDK 17.0.2 as the base image
FROM openjdk:17.0.2

#Create a new app directoru
RUN mkdir "/app"

#Copy the jar file and prospects.txt from host machine to image filesystem
COPY out/artifacts/Mortage_plan_jar/Mortage_plan.jar /app
COPY out/artifacts/Mortage_plan_jar/prospects.txt /app

#Set the directory for executing future commands
WORKDIR /app

#Run jar file
CMD java -jar Mortage_plan.jar