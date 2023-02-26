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
             echo "remove windows-style line-endings"
             sed -i 's/\r$//' ./*.*
             echo "make build scripts executable"
             chmod u+x ./build.sh
             echo "copy jar files"
             cp ../../target/*.jar .
             ls
             echo "substitute docker registry"
             sed -i "s/_DOCKER_REGISTRY_/$HOMELAB_DOCKER_REGISTRY/g" ./build.sh
             echo "start docker build"
             ./build.sh
             '''
        }
      }

    }
  }
}