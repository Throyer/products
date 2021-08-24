# Requisitos

- MariaDB: `^10.6.1`
- Java: `^16`
- node: `^12`
- yarn: `^1.22.5`

> recomendo a instalação do maven localmente, mas o projeto tem uma versão portátil nos arquivos [`mvnw`](./server/mvnw) e [`mvnw.cmd`](./server/mvnw.cmd)

Esse projeto foi configurado com [Spring Initializr](https://start.spring.io/).

# Instalação Back-end

> Caso tiver o maven instalado localmente substitua `mvnw` por `mvn` (_para usuários do zsh adicione o comando `bash` antes de mvnw_)


```shell
# Clone o repositório e acesse o diretório.
$ git clone git@github.com:Throyer/products.git && cd products/server

# Baixe as dependências (o parâmetro -DskipTests pula os testes)
$ mvnw install -DskipTests

# Rode a aplicação
$ mvnw spring-boot:run

# Para rodar os testes
$ mvnw test

# Para buildar para produção
$ mvnw clean package
```

## Rodando um teste especifico
use o parâmetro `-Dtest=<Classe>#<metodo>`


por exemplo o teste de integração de criação usuário:
```
$ mvnw test -Dtest=ProductsControllerTests#should_list_products
```

# Instalação Front-end


```shell
# Clone o repositório e acesse o diretório.
$ git clone git@github.com:Throyer/products.git && cd products/web

# Baixe as dependências
$ yarn

# Rode a aplicação
$ yarn start

# Para buildar para produção
$ yarn build
```
