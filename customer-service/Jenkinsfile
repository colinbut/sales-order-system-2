pipeline {
    agent any
    stages {
        stage('Compile Code') {
            steps {
                sh 'cd customer-service && ./mvnw clean compile'
            }
        }
        stage('Unit Test') {
            steps {
                sh 'cd customer-service && ./mvnw test'
            }
        }
        stage('Integration Test') {
            steps {
                sh 'cd customer-service && ./mvnw verify'
            }
        }
        stage('Package Artifact Jar') {
            steps {
                sh 'cd customer-service && ./mvnw package'
            }
        }
        stage('Build Docker Image') {
            environment {
                COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
            }
            steps {
                sh "docker build -t customer-service:${COMMIT_ID} -f customer-service/Dockerfile customer-service/"
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