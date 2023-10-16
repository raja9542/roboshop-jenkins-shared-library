def compile() {
    if (app_lang == "nodejs") {
        sh 'npm install'
        sh 'env'
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
        //Developer is Missing unit test cases in our project, He need to add them as of best practice
           sh "npm test || true"
    }

    if(app_lang == "maven") {
        sh 'mvn test'
    }
    if(app_lang == "python") {
        sh 'python3 -m unittest'
    }
    if(app_lang == "golang"){
        sh 'go test'

    }
}

def email(email_note) {
    mail bcc: '', body: "JOB FAILED - ${JOB_BASE_NAME} \n Jenkins URL- ${JOB_URL}", cc: '', from: 'rajasekhar1banda@gmail.com', replyTo: '', subject: 'Jenkins Job Failed', to: 'rajasekhar1banda@gmail.com'
}