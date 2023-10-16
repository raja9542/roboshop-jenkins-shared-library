def call() {
    try {
        pipeline {

            agent {
                label 'workstation'
            }

            stages {

                stage('Compile/Build') {
                    steps {
                        script {
                            common.compile()
                        }
                    }
                }

                stage('Unit Tests') {
                    steps {
                        script {
                            common.unittests()
                        }
                    }
                }

                stage('Quality Control') {
                    environment {
                        SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                        //SONAR_PASS = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
                    }
                    steps {
                        script {
                            SONAR_PASS = sh ( script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
                            wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
                                sh "sonar-scanner -Dsonar.host.url=http://172.31.45.15:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey={component}"
                            }
                        }
                    }
                }

                stage('Upload Code to Centralized Place') {
                    steps {
                        echo 'Upload'
                    }
                }


            }

        }
    } catch(Exception e) {
        common.email("Failed")
    }
}


//stage('Quality Control') {
//    environment {
//        SONAR_USER = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.user  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
//        //SONAR_PASS = '$(aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\')'
//    }
//    steps {
//        script {
//            SONAR_PASS = sh(script: 'aws ssm get-parameters --region us-east-1 --names sonarqube.pass  --with-decryption --query Parameters[0].Value | sed \'s/"//g\'', returnStdout: true).trim()
//            wrap([$class: 'MaskPasswordsBuildWrapper', varPasswordPairs: [[password: "${SONAR_PASS}", var: 'SECRET']]]) {
//                sh "sonar-scanner -Dsonar.host.url=http://172.31.45.15:9000 -Dsonar.login=${SONAR_USER} -Dsonar.password=${SONAR_PASS} -Dsonar.projectKey=${component}"
//            }
//        }
//    }
//}
//








//def call() {
//
//    pipeline{
//
//        agent{
//            label 'workstation'
//        }
//
//        stages{
//
//            stage('Compile/Build') {
//                steps{
//                    echo 'compile'
//                }
//            }
//
//            stage('Unit Tests') {
//                steps{
//                    echo 'Unit Tests'
//                }
//            }
//
//            stage('Quality Control') {
//                steps{
//                    echo 'Quality Control'
//                }
//            }
//
//            stage('Upload code to a centralized place') {
//                steps{
//                    echo 'upload'
//                }
//            }
//
//        }
//    }
// }