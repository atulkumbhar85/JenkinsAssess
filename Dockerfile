From tomcat:8-jre8 

# copy war file on to container 
COPY ./target/*.war /usr/local/tomcat/webapps/webapp.war
