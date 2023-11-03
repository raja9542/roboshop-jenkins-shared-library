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
        sh 'npm test || true'
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
def artifactPush() {
    sh "echo ${TAG_NAME} >VERSION"
    if (app_lang == "nodejs") {
        sh "zip -r ${component}-${TAG_NAME}.zip node_modules server.js VERSION ${extraFiles}"
    }
    if (app_lang == "nginx" || app_lang == "python") {
        sh "zip -r ${component}-${TAG_NAME}.zip * -x Jenkinsfile"
    }

    NEXUS_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names nexus.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
    NEXUS_USER = sh(script: 'aws ssm get-parameters --region us-east-1 --names nexus.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
    wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${NEXUS_PASS}", var: 'SECRET']]]) {
        sh "curl -v -u ${NEXUS_USER}:${NEXUS_PASS} --upload-file ${component}-${TAG_NAME}.zip http://172.31.40.116:8081/repository/${component}/${component}-${TAG_NAME}.zip"
    }

}