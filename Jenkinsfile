pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/saisankar333/online_file_sharing_springboot.git'
            }
        }
        stage('Build Backend') {
            steps {
                sh './mvnw clean package'
            }
        }
        stage('Build Frontend') {
            steps {
                dir('dropbox-react') {
                    sh 'npm install'
                    sh 'npm run build'
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying application...'
                // Add Docker or deployment commands here
            }
        }
    }
}
