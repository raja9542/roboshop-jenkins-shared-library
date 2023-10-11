def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
    }

    if(app_lang == "maven") {
        sh 'mvn clean package'
    }
    if(app_lang == "golang"){
        sh 'go mod init ${component}'
        sh 'go get'
        sh 'go build'
    }
}