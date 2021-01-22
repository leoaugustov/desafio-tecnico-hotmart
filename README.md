# Desafio Técnico Hotmart

### Carga inicial do banco de dados
 - Os dados para as tabelas `customer` e `seller` foram gerados usando este [gerador de pessoas](https://www.4devs.com.br/gerador_de_pessoas);
 - Para as tabelas `product_category` e `product` usei o dataset [Cosmetic Products](https://www.kaggle.com/mfsoftworks/cosmetic-products). 
 Criei um [notebook](https://www.kaggle.com/leoaugustov/cosmetic-product-dataset-extraction) bem simples para realizar o download apenas 
 das colunas que me interessavam;
  - Para higienizar os dados, persistir os produtos e gerar as queries SQL das tabelas `product_category` e `sales` desenvolvi um 
  pequeno [script JS](https://gist.github.com/leoaugustov/7d36c59665557c51bff4aeef55381206);
  - As demais tabelas foram preenchidas usando as funções e rotinas do serviço.

### Pré-requisitos
 - Um servidor MySQL sendo executado localmente.
### Para rodar o projeto na sua máquina
#### Clone o repositório
    $ git clone https://github.com/leoaugustov/desafio-tecnico-hotmart.git
    $ cd desafio-tecnico-hotmart
#### Prepare o banco de dados e configure as credenciais de acesso
Crie um banco de dados com o nome `desafio_tecnico_hotmart`.
Altere as credenciais do banco de dados, caso necessário, e informe a chave de acesso da API de notícias na proriedade `newsapi.key` 
no arquivo [application.properties](https://github.com/leoaugustov/desafio-tecnico-hotmart/blob/main/src/main/resources/application.properties).
#### Execute o projeto
Na pasta do projeto execute:

    $ ./mvnw spring-boot:run

A URL base é http://localhost:8080/.
