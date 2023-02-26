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

      stage('Build') {
        steps {
          echo 'Perform maven build'
          sh '''
             mvn package -DskipTests
             '''
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