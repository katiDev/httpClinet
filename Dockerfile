FROM tomcat:latest
ADD target/HttpClientApplication.war /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]