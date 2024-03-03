pipeline {
    agent any
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
            	scripts {
            	def databaseUrl = env.DATABASE_URL
                   echo "DATABASE_URL: ${databaseUrl}"
            	}
            	sh 'chmod +x ./jenkins/scripts/deliver.sh'
                sh './jenkins/scripts/deliver.sh' 
            }
        }
    }
}