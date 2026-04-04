#!/bin/bash
set -e

APP_NAME="my-first-application"
TOMCAT_WEBAPPS="/opt/tomcat/webapps"
BACKUP_DIR="/opt/deployments/backup"
DEPLOY_FILE="/opt/deployments/my-first-application.war"

echo "Creating backup directory if not exists..."
mkdir -p "${BACKUP_DIR}"

echo "Stopping old exploded application if it exists..."
rm -rf "${TOMCAT_WEBAPPS}/${APP_NAME}"

if [ -f "${TOMCAT_WEBAPPS}/${APP_NAME}.war" ]; then
  echo "Backing up old WAR..."
  cp "${TOMCAT_WEBAPPS}/${APP_NAME}.war" "${BACKUP_DIR}/${APP_NAME}_$(date +%F_%H-%M-%S).war"
fi

echo "Deploying new WAR..."
cp "${DEPLOY_FILE}" "${TOMCAT_WEBAPPS}/${APP_NAME}.war"

echo "Restarting Tomcat..."
sudo /usr/bin/systemctl restart tomcat

echo "Checking Tomcat status..."
sudo /usr/bin/systemctl status tomcat
