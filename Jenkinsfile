pipeline {
  agent any
  tools {
    jdk 'Java17'
    gradle 'Gradle8'
  }

  environment {
    APP_NAME = 'tierlist-api'
    RELEASE = '1.0.0'
    DOCKER_USER = ''
    DOCKER_PASS = 'dockerhub'
    IMAGE_NAME = "${DOCKER_USER}/${APP_NAME}"
    IMAGE_TAG = "${RELEASE}-${BUILD_NUMBER}"
  }

  stages {
    stage('Cleanup Workspace') {
      steps {
        cleanWs()
      }
    }

    stage('Checkout from SCM') {
      steps {
        git branch: 'develop', credentialsId: 'github', url: 'https://github.com/tierlist-projects/tierlist-api'
      }
    }

    stage('Build Application') {
      steps {
        sh 'gradle clean build'
      }
    }

    stage('Test Application') {
      steps {
        sh 'gradle test'
      }
    }

    stage('Build & Push Docker Image') {
      steps {
        script {
          docker.withRegistry('', DOCKER_PASS) {
            docker_image = docker.build "${IMAGE_NAME}"
          }

          docker.withRegistry('', DOCKER_PASS) {
            docker_image.push("${IMAGE_TAG}")
            docker_image.push('latest')
          }
        }
      }
    }
  }

}
