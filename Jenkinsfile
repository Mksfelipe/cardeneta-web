pipeline {
    agent any
    options {
        skipStagesAfterUnstable()
    }
    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests verify'
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
            	sh 'docker rmi -f $(docker images -q)'
            	sh 'docker build -t cardeneta-api .'
            	sh 'chmod +x ./jenkins/scripts/deliver.sh'
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}