pipeline {
    agent any

    environment {
        APP_NAME = "dropbox-backend"
        DOCKER_IMAGE = "dropbox-backend:latest"
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saisankar333/online_file_sharing_springboot.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Docker Build & Run') {
            steps {
                script {
                    // Build Docker image
                    sh "docker build -t ${DOCKER_IMAGE} ."

                    // Stop and remove old container if exists
                    sh "docker stop ${APP_NAME} || true"
                    sh "docker rm ${APP_NAME} || true"

                    // Run new container
                    sh "docker run -d -p 8080:8080 --name ${APP_NAME} ${DOCKER_IMAGE}"
                }
            }
        }

        stage('Deploy') {
            steps {
                echo "Application deployed and running at http://<JENKINS_HOST>:8080"
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo 'Pipeline failed. Check the logs.'
        }
    }
}
