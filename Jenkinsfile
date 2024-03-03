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
        stage("Staging") {
        	steps {
                sh "pid=\$(lsof -i:9090 -t); kill -TERM \$pid " 
                  + "|| kill -KILL \$pid"
                sh 'nohup ./mvnw spring-boot:run -Dserver.port=8989 &'
        	}
        }
    }
}