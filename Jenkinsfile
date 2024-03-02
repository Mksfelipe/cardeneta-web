pipeline {
    agent any
     environment {
        DB_URL = credentials('DATABASE_URL')
    }
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
               sh 'mvn -B -DskipTests clean package'
               sh 'echo ${DATABASE_URL}'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }
        stage('Deliver') { 
            steps {
            	sh 'docker build --build-arg DB_URL=${DB_URL} -t cardeneta-api .'
            	sh 'chmod +x ./jenkins/scripts/deliver.sh'
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}