docker volume create gradle-repo-tomcat9

docker build . -t tomcat9

docker rm -f tomcat9

docker run -d --restart unless-stopped \
  --name tomcat9-p 8082:8080 \
  -v gradle-repo-tomcat9:/root/.m2 \
  -v /docker_data/tomcat9/logs:/springjpa/logs \
  tomcat9
