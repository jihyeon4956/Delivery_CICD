#!/usr/bin/env bash

PROJECT_ROOT="/home/ubuntu/app"
JAR_FILE="$PROJECT_ROOT/spring-webapp.jar"

# S3에 저장된 파일 읽어오기
S3_BUCKET_NAME="mini-delivery"
S3_FILE_PATH="secu/application.properties"  # S3 내부의 파일 경로
PROPERTIES_PATH="$PROJECT_ROOT/application.properties"  # EC2 인스턴스 내의 경로

# S3에서 application.properties 파일 복사
aws s3 cp s3://$S3_BUCKET_NAME/$S3_FILE_PATH $PROPERTIES_PATH


APP_LOG="$PROJECT_ROOT/application.log"
ERROR_LOG="$PROJECT_ROOT/error.log"
DEPLOY_LOG="$PROJECT_ROOT/deploy.log"

TIME_NOW=$(date +%c)

# build 파일 복사
echo "$TIME_NOW > $JAR_FILE 파일 복사" >> $DEPLOY_LOG
cp $PROJECT_ROOT/build/libs/*.jar $JAR_FILE

# jar 파일 실행
echo "$TIME_NOW > $JAR_FILE 파일 실행" >> $DEPLOY_LOG
nohup java -jar $JAR_FILE > $APP_LOG 2> $ERROR_LOG &

CURRENT_PID=$(pgrep -f $JAR_FILE)
echo "$TIME_NOW > 실행된 프로세스 아이디 $CURRENT_PID 입니다." >> $DEPLOY_LOG
