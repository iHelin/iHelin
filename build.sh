mvn clean && mvn package
mvn docker:build
docker push ihelin/ihelin:2.0.0
# 删除none镜像
docker rmi $(docker images | grep "none" | awk '{print $3}')
