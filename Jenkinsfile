pipeline {
  agent any
  tools {
    jdk 'Java17'
    gradle 'Gradle8'
  }

  environment {
    APP_NAME = 'dev-tierlist-api'
    RELEASE = '1.0.0'
    DOCKER_USER = 'duk9741'
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
          docker.withRegistry('https://registry.hub.docker.com', DOCKER_PASS) {
            docker_image = docker.build "${IMAGE_NAME}"
            docker_image.push("${IMAGE_TAG}")
            docker_image.push('latest')
          }
        }
      }
    }

    stage('Update the Deployment Tags at Git Ops Repo') {
      steps {
        cleanWs()

        git branch: 'develop', credentialsId: 'github', url: 'https://github.com/tierlist-projects/tierlist-git-ops'

        sh """
          cat app/Deployment.yaml
          set -i 's/${APP_NAME}.*/${APP_NAME}:${IMAGE_TAG}/g' app/Deployment.yaml
          cat app/Deployment.yaml

          git config --global user.name "dukcode"
          git config --global user.email "duk9741@gmail.com"
          git add app/Deployment.yaml
          git commit -m "Update Deployment Manifest"
        """

        withCredentials([gitUsernamePassword(credentialsId: 'github', gitToolName: 'Default')]) {
          sh "git push 'https://github.com/tierlist-projects/tierlist-git-ops' main"
        }

      }
    }

  }

}
