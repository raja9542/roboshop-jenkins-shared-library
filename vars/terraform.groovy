def call() {
    pipeline {

        options {
            ansicolor('xterm')
        }

        agent {
            node {
                label 'workstation'
            }
        }
        parameters {
            string(name: 'INFRA_ENV', defaultValue: '', description: 'Enter env like dev or prod')
        }

        stages{
            stage('Terraform Init') {
                steps{
                    sh "terraform init -backend-config=env-${INFRA_ENV}/state.tfvars"
                }
            }
        }
    }
}