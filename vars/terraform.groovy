def call() {
    pipeline {

        agent {
            node {
                label 'workstation'
            }
        }
        parameters {
            string(name: 'INFRA_ENV', defaultValue: '', description: 'Enter env like dev or prod')
        }

        satges{
            stage('Terraform Init') {
                sh "terraform init -backend-config=env-${INFRA_ENV}/state.tfvars"
            }
        }
    }
}