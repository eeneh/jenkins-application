pipeline {
    agent any

    environment {
        APP_NAME = 'my-first-application'
        APP_SERVER = '3.110.51.39'
        APP_SERVER_USER = 'deployer'
        REMOTE_DEPLOY_PATH = '/opt/deployments/my-first-application.war'
        REMOTE_SCRIPT_PATH = '/opt/deployments/deploy.sh'
        HEALTH_URL = 'http://3.110.51.39:8080/my-first-application/health'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Validate') {
            steps {
                sh 'mvn clean validate'
            }
        }

        stage('Unit Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests=false'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                }
            }
        }

        stage('Deploy to App Server') {
            steps {
                sshagent(credentials: ['my-application-server-access']) {
                    sh '''
                        scp -o StrictHostKeyChecking=no target/my-first-application.war ${APP_SERVER_USER}@${APP_SERVER}:${REMOTE_DEPLOY_PATH}
                        scp -o StrictHostKeyChecking=no scripts/deploy.sh ${APP_SERVER_USER}@${APP_SERVER}:${REMOTE_SCRIPT_PATH}
                        ssh -o StrictHostKeyChecking=no ${APP_SERVER_USER}@${APP_SERVER} "chmod +x ${REMOTE_SCRIPT_PATH} && bash ${REMOTE_SCRIPT_PATH}"
                    '''
                }
            }
        }

        stage('Smoke Test') {
            steps {
                sh '''
                    echo "Waiting for application to come up..."
                    sleep 20
                    curl -f ${HEALTH_URL}
                '''
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully. Application deployed.'
        }
        failure {
            echo 'Pipeline failed. Check logs.'
        }
        always {
            cleanWs()
        }
    }
}

