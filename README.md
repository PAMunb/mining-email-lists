# Web Crawler

Este é um web crawler desenvolvido em Java com Spring Boot, utilizando Maven como gerenciador de dependências. O objetivo do projeto é coletar informações de websites de forma automatizada. 
Para persistência de dados foram utilizados o JPA e Hibernate. Enquanto, o banco de dados mySql pode ser inicializado via Docker. Além disso, foi o utilizado o lombok para diminuir a verbosidade do código e para documentação o Javadoc.

#### Pré-requisitos

Certifique-se de ter as seguintes dependências instaladas em seu ambiente de desenvolvimento:

- Java Development Kit (JDK) 17; 
- Docker e Docker Compose;
- Lombok 

#### Configuração

1. Clone este repositório para o seu ambiente de desenvolvimento:
[Git Hub] <https://github.com/PAMunb/mining-email-lists.git>
2. Importe o projeto no seu IDE de preferência (por exemplo, IntelliJ, Eclipse).
3. Configurações do **lombok**: 
fazer a instalação no site e setar a IDE utilizada ou baixar a dependência (vscode, por exemplo).
[Lombok] <https://projectlombok.org>
4. Configurações do banco de dados:
   As configurações de conexão com o banco de dados MySQL estão localizadas no arquivo `src/main/resources/application.properties`.
   
   Verifique as configurações do banco de dados, como URL, nome de usuário e senha, e ajuste conforme necessário. Nesse caso: 
   
   **user: spring_user;**
   
   **senha: root;**
   
   **database: scrap;**
____

#### Docker Compose

1. Navegue até o diretório do projeto:
Ex: `cd/…/mining-email-list `
2. Inicie o MySQL usando o **Docker Compose**:

> - docker compose up

    
#### Executando o Web Crawler

1. Inicie o aplicativo Spring Boot executando o seguinte comando na raiz do projeto:

> - mvn spring-boot:run ou mvn

#### Documentação JavaDoc

1. A documentação javaDoc pode ser criada executando o comando **abaixo** na raiz do projeto e pode ser acessada em:
`target/apidocs/br/unb/scrap/`

> - mvn javadoc:javadoc

____

##### Obs:no repositório do projeto já um script chamado "run_bd.sh" que auxilia na criação do banco. 