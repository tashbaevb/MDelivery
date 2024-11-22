#!/usr/bin/env bash


 mvn clean package -DskipTests

 echo "Copy files ..."

 scp -i ~/.ssh/mdelivery-ssh \
     ./target/*.jar \
     root@157.230.30.221:~/mdeliveryServer/hosting.jar

 echo "Restart server ..."

 ssh -i ~/.ssh/mdelivery-ssh root@157.230.30.221 << EOF

 pgrep java | xargs kill -9
 nohup java -jar mdeliveryServer/hosting.jar &

 EOF

 echo "Bye !"