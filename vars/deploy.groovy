def call() {

    pipeline{

        agent any

        parameters {
            string(name: 'APP_ENV', defaultValue: '', description: 'Enter env like dev or prod')
            string(name: 'COMPONENT', defaultValue: '', description: 'Enter component name')
            string(name: 'APP_VERSION', defaultValue: '', description: 'Enter application version to deploy')
        }

        stages{

            stage('Run Deployment'){

                steps{
// to update/overwrite app version we use this
                    sh '''aws ssm put-parameter --name "${APP_ENV}.${COMPONENT}.APP_VERSION" --type "String" --value "${APP_VERSION}" --overwrite '''
                }

            }
        }

    }
}