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
          sh '''
             mvn package -DskipTests
             '''
        }
      }

      stage('Docker Image') {
        steps {
          sh '''
             cd docker/image
             ls
             echo "remove any previous jar files"
             rm ./*.jar || true
             '''
        }
      }

    }
  }
}