docker volume create maven-repo-springjpa

docker build . -t springjpa

docker rm -f springjpa

docker run -d --restart unless-stopped \
  --name springjpa -p 8082:8082 \
  -v maven-repo-springjpa:/root/.m2 \
  -v /docker_data/springjpa/logs:/springjpa/logs \
  springjpa
