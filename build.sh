mvn clean && mvn package
mvn docker:build
#docker push ihelin/ihelin:2.0.0
docker tag ihelin/ihelin:2.0.0 registry.cn-qingdao.aliyuncs.com/ihelin/ihelin:2.0.0
docker push registry.cn-qingdao.aliyuncs.com/ihelin/ihelin:2.0.0
# 删除none镜像
docker rmi $(docker images | grep "none" | awk '{print $3}')
