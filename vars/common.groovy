def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }

    if(app_lang == "maven") {
        sh 'mvn clean package'
    }
    if(app_lang == "golang"){
        sh 'rm go.mod'
        sh 'go mod init ${component}'
        sh 'go get'
        sh 'go build'
    }
}

def unittests() {
    if (app_lang == "nodejs") {
        sh 'npm test'
    }

    if(app_lang == "maven") {
        sh 'mvn clean package'
    }
    if(app_lang == "golang"){
        sh 'rm go.mod'
        sh 'go mod init ${component}'
        sh 'go get'
        sh 'go build'
    }


}