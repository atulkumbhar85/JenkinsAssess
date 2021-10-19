From tomcat:8-jre8 

# copy war file on to container 
COPY ./target/JenkinsAssess-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/webapp.war
