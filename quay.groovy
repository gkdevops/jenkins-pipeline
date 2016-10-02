// Perform docker login to quay.io
def login(quay_creds_id) {
/*
    args Quay Credetianls ID name as defined in Jenkins credentials
    Utilizes the Credentials Binding Plugin to read Jenkins credentials
    dockerEmail var (docker login -e) is deprecetiated. Supporting earlier versions
*/

    println "attempt docker build/publish: ${args.host}/${args.acct}/${args.repo}:${args.tags}"

    def dockerEmail = "."

    withCredentials([[$class          : 'UsernamePasswordMultiBinding', credentialsId: quay_creds_id,
                    usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {

    sh "echo ${env.PASSWORD} | base64 --decode > ${pwd}/docker_pass"
    sh "docker login -e ${dockerEmail} -u ${env.USERNAME} -p `cat ${pwd}/docker_pass` quay.io"
                    }
}