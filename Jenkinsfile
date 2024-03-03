pipeline {
    agent any
    environment {
        DATABASE_URL = credentials('DATABASE_URL')
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
            	sh 'echo $DATABASE_URL'
            	sh 'chmod +x ./jenkins/scripts/deliver.sh'
                
            }
        }
    }
}