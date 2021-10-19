def CONTAINER_NAME="jenkins-pipeline"
def CONTAINER_TAG="latest"
def DOCKER_HUB_USER="atulkumbhar85"
def HTTP_PORT="8080"

node('agent01'){
    try{        
        stage('compile'){
            
            sh 'mvn compile'

        }
        stage('Install'){

            sh 'mvn clean install package'

        }
        stage('test'){

            sh 'mvn test'
            junit allowEmptyResults: true, testResults: '/home/labsuser/Downloads/JenkinsAssess/reports.xml'

        }
        stage("Image Prune"){
            imagePrune(CONTAINER_NAME)
        }
        stage('Image Build'){
            imageBuild(CONTAINER_NAME, CONTAINER_TAG)
        }
        stage('Push to Docker Registry'){
            withCredentials([usernamePassword(credentialsId: 'dockerHubAccount', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                pushToImage(CONTAINER_NAME, CONTAINER_TAG, USERNAME, PASSWORD)
            }
        }
        stage('Run App'){
            runApp(CONTAINER_NAME, CONTAINER_TAG, DOCKER_HUB_USER, HTTP_PORT)
        }
    }
    catch(Exception e){
        currentBuild.result = "FAILURE"
        emailext body: '${BUILD_NUMBER} of ${BUILD_URL} failed due to ', subject: '${BUILD_NUMBER} of ${BUILD_URL} failed ', to: 'manager@example.com'
    }
    finally{
        echo 'Pipeline job completed'
    }
}


def imagePrune(containerName){
    try {
        sh "docker image prune -f"
        sh "docker stop $containerName"
    } catch(error){}
}

def imageBuild(containerName, tag){
    sh "docker build -t $containerName:$tag  -t $containerName --pull --no-cache ."
    echo "Image build complete"
}

def pushToImage(containerName, tag, dockerUser, dockerPassword){
    sh "docker login -u $dockerUser -p $dockerPassword"
    sh "docker tag $containerName:$tag $dockerUser/$containerName:$tag"
    sh "docker push $dockerUser/$containerName:$tag"
    echo "Image push complete"
}

def runApp(containerName, tag, dockerHubUser, httpPort){
    sh "docker pull $dockerHubUser/$containerName"
    sh "docker run -d --rm -p 8090:$httpPort --name $containerName $dockerHubUser/$containerName:$tag"
    echo "Application started on port: ${httpPort} (http)"
}
