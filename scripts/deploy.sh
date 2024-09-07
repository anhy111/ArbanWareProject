
REPOSITORY=/home/ec2-user/arbanWare
cd $REPOSITORY

APP_NAME=arbanWare
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)
CURRENT_PID_H2=$(pgrep -f h2)

if [ -z $CURRENT_PID_H2 ]
then
  echo "> 시작된 h2서버 없음."
  nohup java -cp /home/ec2-user/h2/bin/h2*.jar org.h2.tools.Server -tcp -tcpAllowOthers -webAllowOthers &
  echo "> h2서버 시작"
  sleep 10
else
  echo "> h2서버 실행중"
fi


if [ -z $CURRENT_PID ]
then
  echo "> 종료할 프로그램 없음."
else
  echo "> kill -9 $CURRENT_PID"
      kill -9 $CURRENT_PID
      sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH 1> REPOSITORY/arbanware.log 2>arbanWare_error.log &