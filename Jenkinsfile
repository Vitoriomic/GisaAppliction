pipeline {
    agent any

    stages {
        stage('Clone') {
            steps {
                // Clona o repositório
                git branch: 'main', url: 'https://seu-repositorio.git'
            }
        }

        stage('Build') {
            steps {
                // Compila o projeto com Maven
                bat 'mvn clean package'
            }
        }

        stage('Deploy') {
            steps {
                // Para a aplicação existente
                bat 'taskkill /F /IM java.exe || echo "Nenhum processo encontrado"'

                // Inicia o novo build
                bat 'java -jar target/meu-projeto.jar'
            }
        }
    }
}
