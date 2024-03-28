pipeline {
  agent any
  tools {
    jdk 'Java17'
    gradle 'Gradle8'
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
  }

}
