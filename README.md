SISTEMA DE GESTÃO DE PROJETOS

Descrição
Este projeto foi desenvolvido em Java com o objetivo de gerenciar projetos, tarefas e equipes, permitindo o cadastro, consulta, atualização e exclusão de informações através de interface gráfica.

Tecnologias Utilizadas
- Java
- Java Swing
- JDBC
- MySQL
- NetBeans IDE

Como Executar o Projeto

1. Importar o banco de dados
- Abrir o MySQL Workbench
- Antes de executar o sistema, configure o usuário e senha do MySQL na classe ConnectionFactory.java
- Executar o arquivo banco.sql para criar o banco e as tabelas

2. Configurar conexão com banco
Verificar a classe:

src/connection/ConnectionFactory.java

Configuração padrão:

private static final String URL = "jdbc:mysql://localhost:3306/sistema_gestao_projetos";
private static final String USER = "root";
private static final String PASSWORD = "sua_senha";

3. Executar o projeto

Opção 1 (Recomendado)
- Abrir o projeto no NetBeans
- Clicar em Run

Opção 2 (Executar .jar)
Executar no terminal:

java -jar sistema_gestao_projetos.jar

Estrutura do Projeto

- model → classes de entidades
- dao → acesso ao banco de dados
- controller → regras de negócio
- view → telas do sistema
- connection → conexão com banco

Funcionalidades

- Cadastro de projetos
- Cadastro de tarefas
- Cadastro de usuários
- Edição de registros
- Exclusão de registros
- Atribuição de responsáveis
- Controle de status das tarefas
- Upload de arquivos
- Sistema de login
- Interface gráfica Java Swing

Configuração do Banco

- Host: localhost
- Porta: 3306
- Banco: sistema_gestao_projetos
- Usuário: root

Observações

- É necessário possuir Java e MySQL instalados.
- O banco de dados deve estar ativo durante a execução.
- Pode ser necessário configurar UTF-8 no ambiente.
