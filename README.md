# P2 - Sistema de Login com CRUD (Java + MySQL)

Projeto individual para a avaliação P2 de Desenvolvimento para Servidores I. Implementa cadastro, autenticação, consulta, alteração e exclusão de usuários usando Java Swing, JDBC e MySQL.

## Requisitos
- JDK 17 ou superior
- Apache Maven
- MySQL Server (8.x recomendado)
- NetBeans, IntelliJ IDEA ou VS Code com suporte a Java

## 1. Criar o banco de dados
Abra o MySQL Workbench ou outro cliente SQL e execute o arquivo `src/main/resources/bd_login.sql`.

O script cria o banco `bd_login` e a tabela `usuario`, com os campos:
- `usuario`: identificador (chave primária, até 10 caracteres)
- `nome`: nome do usuário (até 30 caracteres)
- `senha`: hash MD5 hexadecimal (32 caracteres), seguindo o exemplo didático das notas de aula.

## 2. Configurar a conexão
Por padrão, a aplicação usa:
- URL: `jdbc:mysql://localhost:3306/bd_login`
- Usuário: `root`
- Senha: vazia

Para configurar outro ambiente, defina estas variáveis de ambiente antes de executar:
- `DB_URL`
- `DB_USER`
- `DB_PASSWORD`

Exemplo no PowerShell:
```powershell
$env:DB_URL="jdbc:mysql://localhost:3306/bd_login"
$env:DB_USER="root"
$env:DB_PASSWORD="sua_senha"
```

## 3. Executar
Na pasta do projeto:
```bash
mvn clean compile
mvn exec:java
```

No NetBeans: abra a pasta como projeto Maven e execute a classe `login.Main`.

## 4. Roteiro de demonstração da P2
1. Abra o sistema e mostre a tela de login.
2. Tente entrar com os campos vazios (deve apresentar validação).
3. Abra o cadastro e tente enviar campos vazios.
4. Cadastre um usuário e confirme que as senhas iguais são exigidas.
5. Tente cadastrar novamente o mesmo usuário (deve impedir duplicidade).
6. Faça login com os dados cadastrados.
7. Mostre a tela inicial com o nome do usuário.
8. Altere o nome, usuário e/ou senha; confirme a alteração.
9. Entre novamente com os dados atualizados.
10. Exclua o usuário e confirme que ele não consegue mais entrar.

## Observação de segurança
O MD5 foi mantido por corresponder ao conteúdo didático apresentado nas notas da disciplina. MD5 não é adequado para proteger senhas em sistemas reais. Em aplicações de produção, use algoritmos próprios para senhas, como Argon2id ou bcrypt.

## Estrutura
```text
src/main/java/login/
  Main.java
  Conexao.java
  Usuario.java
  TelaLogin.java
  TelaCadastro.java
  TelaInicio.java
  TelaAlteracao.java
src/main/resources/
  bd_login.sql
```
