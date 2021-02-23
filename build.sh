mvn clean && mvn package
mvn docker:build
docker push ihelin/ihelin:2.0.0
