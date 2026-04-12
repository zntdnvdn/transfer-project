pipeline {
    agent any

    tools {
        jdk 'JDK17'
        maven 'Maven-3.9.9'
    }

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        REQUEST_IMAGE = 'transfer-request'
        PROCESSING_IMAGE = 'transfer-processing'
        IMAGE_TAG = "${BUILD_NUMBER}"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Preflight') {
            steps {
                bat 'echo ===== JAVA ====='
                bat 'where java'
                bat 'java -version'

                bat 'echo ===== MAVEN ====='
                bat 'where mvn'
                bat 'mvn -v'

                bat 'echo ===== GIT ====='
                bat 'where git'
                bat 'git --version'

                bat 'echo ===== DOCKER ====='
                bat 'where docker'
                bat 'docker version'
                bat 'docker info'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean verify'
            }
        }

        stage('Build transfer-request Image') {
            steps {
                bat 'docker build -t %REQUEST_IMAGE%:%IMAGE_TAG% -t %REQUEST_IMAGE%:latest .\\transfer-request'
            }
        }

        stage('Build transfer-processing Image') {
            steps {
                bat 'docker build -t %PROCESSING_IMAGE%:%IMAGE_TAG% -t %PROCESSING_IMAGE%:latest .\\transfer-processing'
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml, **/target/failsafe-reports/*.xml'
            archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
        }
    }
}