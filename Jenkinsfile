pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saisankar333/online_file_sharing_springboot.git'
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package' // If using Maven wrapper
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying application...'
            }
        }
    }
}
