#!/usr/bin/env groovy
def call(String... args) {

  pipeline {

    tools {
      maven 'Maven-3.8.5'
      jdk 'OPENJDK17'
    }

    options {
      buildDiscarder(logRotator(numToKeepStr: '3'))
    }

    agent any

    stages {

      stage('Stage 1') {
        steps {
          echo 'Hello world!'
          sayHello HOMELAB_DOCKER_REGISTRY
          sh 'ls -all'
          sh 'java -version'
          sh '''
             docker --version
             '''
        }
      }

    }
  }
}