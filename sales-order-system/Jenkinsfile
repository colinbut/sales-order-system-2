pipeline {
    agent any
    stages {
        stage('Install Dependencies') {
            steps {
                sh 'cd sales-order-system && npm install'
            }
        }
        stage('Run Production Build') {
            steps {
                sh 'cd sales-order-system && npm run build'
            }
        }
        stage('Build Docker Image') {
            environment {
                COMMIT_ID = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()
            }
            steps {
                sh "docker build -t sales-order-system:${COMMIT_ID} -f sales-order-system/Dockerfile sales-order-system/"
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