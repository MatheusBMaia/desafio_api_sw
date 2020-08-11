## API_Desafio_SW
##### Set Up com o JDK 11 instalado.

Após realizar o "Git Pull" do projeto, será necessário realizar a instalação das dependências do projeto a partir do maven. Pode ser utilizado o wrapper que se encontra na pasta raiz do projeto utilizando o seguinte comando no prompt de comando( CMD ): [Terminal:] ./mvnw install

Obs.: Se preferir também é possivel usa-lo diretamente pelo eclipse.

##### Automatização dos Testes.

Os testes automizados podem ser encontrados no caminho src/test/java/com/b2w/apidesafiosw e serem executados pelo comando mvn(ou ./mvnw) test

##### Documentação do Projeto.

Realizada a documentação do projeto pelo Swagger. Ao realizar a execução do projeto a documentação pode ser acessada pela url:

```
 http://localhost:8080/swagger-ui.html
```
## License

The MIT License (MIT) Copyright (c) 2020 Matheus Maia