pipeline {
    agent any
    stages {
        stage('Compile Code') {
            steps {
                sh 'cd order-service && ./gradlew clean compileJava'
            }
        }
        stage('Unit Test') {
            steps {
                sh 'cd order-service && ./gradlew test'
            }
        }
        stage('Integration Test') {
            steps {
                echo 'Integration Test'
            }
        }
        stage('Package Artifact Jar') {
            steps {
                sh 'cd order-service && ./gradlew bootJar'
            }
        }
        stage('Build Docker Image') {
            environment {
                COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
            }
            steps {
                sh "docker build -t order-service:${COMMIT_ID} -f order-service/Dockerfile order-service/"
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