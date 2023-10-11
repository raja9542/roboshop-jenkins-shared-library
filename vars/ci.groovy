def call() {

    pipeline{

        agent{
            label 'workstation'
        }

        stages{

            stage('Compile/Build') {
                steps{
                    script{
                        common.compile()
                    }
                }
            }

            stage('Unit Tests') {
                steps{
                    script{
                        common.unittests()
                    }
                }
            }

            stage('Quality Control') {
                steps{
                    echo 'Quality Control'
                }
            }

            stage('Upload code to a centralized place') {
                steps{
                    echo 'upload'
                }
            }

        }
    }
}








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
//}