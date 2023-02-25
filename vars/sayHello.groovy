#!/usr/bin/env groovy
def call(String name = 'human') {
  echo "Hello, ${name}."
  docker --version
}
