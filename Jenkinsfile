pipeline {
    agent any

    parameters {
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'staging', 'prod'],
            description: 'Select the environment for deployment'
        )
        string(
            name: 'APP_VERSION',
            defaultValue: '1.0.0',
            description: 'Enter the application version to deploy'
        )
    }

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
                echo "Checking out source code from repository..."
                checkout scm
            }
        }

        stage('Prepare Version') {
            steps {
                echo "Preparing application version: ${params.APP_VERSION}"
                sh """
                    sed -i 's/^app.version=.*/app.version=${params.APP_VERSION}/' src/main/resources/application.properties
                """
                echo "Application version updated successfully in application.properties"
            }
        }

        stage('Validate') {
            steps {
                echo "Running Maven validate phase..."
                sh 'mvn clean validate'
            }
        }

        stage('Unit Test') {
            steps {
                echo "Running unit tests..."
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
                echo "Packaging the application into WAR file..."
                sh 'mvn package -DskipTests=false'
            }
            post {
                success {
                    archiveArtifacts artifacts: 'target/*.war', fingerprint: true
                }
            }
        }

        stage('Approval for Production') {
            when {
                expression { params.ENVIRONMENT == 'prod' }
            }
            steps {
                echo "Production deployment selected. Waiting for manual approval..."
                input message: "Approve deployment to PRODUCTION?", ok: "Approve and Deploy"
            }
        }

        stage('Deploy to App Server') {
            steps {
                echo "Deploying application version ${params.APP_VERSION} to ${params.ENVIRONMENT} environment..."

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
                echo "Waiting for application to come up..."
                sh '''
                    sleep 20
                    curl -f ${HEALTH_URL}
                '''
                echo "Smoke test completed successfully."
            }
        }
    }

    post {
        success {
            echo "Pipeline completed successfully. Application version ${params.APP_VERSION} deployed to ${params.ENVIRONMENT}."
        }
        failure {
            echo 'Pipeline failed. Check logs.'
        }
        always {
            cleanWs()
        }
    }
}
