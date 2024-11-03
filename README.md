# Programa Cliente-Servidor com servidor em Java e cliente em Python

## O projeto
Este projeto contém um programa cliente-servidor que utiliza Sockets e é um trabalho na disciplina de Redes, no curso de Ciências da Computação na UFSCar Sorocaba. A proposta do projeto era de criar um programa cliente-servidor em que:

- o servidor e o cliente devessem estar em linguagens de programação diferentes;
- houvesse suporte a múltiplas conexões;
- o servidor deveria receber um arquivo, armazená-lo e enviar este arquivo caso fosse solicitado;
- deveria haver, no cliente, um menu simples que desse as opções de enviar ou receber o arquivo do servidor

Agora, sobre os programas:

- o programa servidor é escrito em Java e é composto por duas classes: a Main.java e a ClientHandler.java. As duas classes estavam dentro do mesmo pacote nomeado como "servidor".
- o programa cliente é escrito em Python e é composto apenas pelo arquivo Cliente.py

## Como posso usar este projeto?

Para conseguir que haja um funcionamento correto é necessário algumas adaptações:

- no arquivo *ClientHandler.java*, deve-se alterar: 
    ```sh
    "C:\\Users\\matte\\Desktop\\auxiliar_servidor"
    ```
    para:
    ```sh
    "<endereço da pasta que você preferir>"
    ```
