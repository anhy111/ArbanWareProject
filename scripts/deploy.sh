
REPOSITORY=/home/ec2-user/arbanWare
cd $REPOSITORY

APP_NAME=arbanWare
JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep 'SNAPSHOT.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/$JAR_NAME

CURRENT_PID=$(pgrep -f $APP_NAME)
CURRENT_PID_H2=$(pgrep -f h2)

if [ -z $CURRENT_PID_H2 ]
then
  echo "> 종료할 h2서버 없음."
else
  echo "> kill -9 $CURRENT_PID_H2"
  kill -15 $CURRENT_PID_H2
  sleep 5
fi
nohup java -cp /home/ec2-user/h2/bin/h2*.jar org.h2.tools.Server -tcp &

if [ -z $CURRENT_PID ]
then
  echo "> 종료할 프로그램 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH > /dev/null 2> /dev/null < /dev/null &