pipeline {
    agent any
    stages {
        stage('Compile Code') {
            steps {
                sh 'cd user-service && ./mvnw clean compile'
            }
        }
        stage('Unit Test') {
            steps {
                sh 'cd user-service && ./mvnw test'
            }
        }
        stage('Integration Test') {
            steps {
                sh 'cd user-service && ./mvnw verify'
            }
        }
        stage('Package Artifact Jar') {
            steps {
                sh 'cd user-service && ./mvnw package'
            }
        }
        stage('Build Docker Image') {
            environment {
                COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
            }
            steps {
                sh "docker build -t user-service:${COMMIT_ID} -f user-service/Dockerfile user-service/"
            }
        }
    }
    post {
        success {
            echo "Build Success"
            echo "Successfully built ${env.JOB_BASE_NAME} - ${env.BUILD_ID} on ${env.BUILD_URL}"
        }
        failure {
            echo "Build Failed - ${env.JOB_BASE_NAME} - ${env.BUILD_ID} on ${env.BUILD_URL}"
        }
        aborted {
            echo " ${env.JOB_BASE_NAME} Build - ${env.BUILD_ID} Aborted!"
        }
    }
}