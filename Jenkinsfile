pipeline {
    agent any
    stages {
        stage("Verify tooling") {
            steps {
            bat '''
            docker version
            docker compose version
            curl --version
            '''
            }
        }
        stage('Gradle clean build') {
            steps {
            bat './gradlew clean build'
            }
        }
        stage('Gradle docker push image') {
            steps {
                withDockerRegistry([credentialsId: "dockerhub", url: ""]){
                bat './gradlew dockerPushImage'
                }
            }
        }
        stage('Deploy application using HTTP API'){
            steps{
            bat 'curl -H "Content-Type: application/json" -X POST -d @marathon-deploy.json http://localhost:5000/v2/apps'
            }
        }
        stage('Update application using HTTP API'){
            steps{
            bat 'curl -H "Content-Type: application/json" -X PUT -d @marathon-deploy.json http://localhost:5000/v2/apps/resume-storage-project'
            }
        }
        stage('Restart application using HTTP API'){
            steps{
            bat 'curl -H "Content-Type: application/json" -X POST -d @marathon-deploy.json http://localhost:5000/v2/apps/resume-storage-project/restart'
            }
        }
    }
}
