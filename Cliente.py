#Sorocaba, 03 de novembro de 2024
#Matteo Cileneo Savan
#UFSCar Sorocaba

import socket

opcao = int(input("Escolha uma opção:\n1. Enviar arquivo ao servidor\n2. Receber arquivo do servidor"
                     "\nOu digite qualquer outro número para cancelar: "))

cliente_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
cliente_socket.connect(("localhost", 12000)) # abre a conexão

with cliente_socket:
    cliente_socket.sendall(bytes([opcao]))  # envie a opção primeiro
    if opcao == 1:
        # pegando o nome do arquivo:
        print("Digite o caminho do arquivo que deseja enviar ao servidor (ex: C:\Users\PastaDestino.txt): ")
        caminho_arquivo = input()

        # abaixo é para pegar o formato do arquivo e envia-lo ao servidor:
        print("Agora digite o formato (ex: .txt): ")
        mensagem = input()
        mensagem = mensagem +'\n'
        cliente_socket.sendall(mensagem.encode()) # envia texto com o formato para o servidor
        print("a mensagem é " + mensagem) # print para conferir se o formato enviado é correto

        # enviando o arquivo em bytes:
        f = open(caminho_arquivo, "rb") # lê o arquivo em forma binária
        cliente_socket.sendfile(f)  # envia o arquivo no formato de bytes para o servidor

    elif opcao == 2:
        formato = (cliente_socket.recv(1024)).decode('utf-8') #recebe o formato do arquivo do servidor. Se nao tem arquivo, recebe "n"
        print("O formato do arquivo recebido é: " + formato)

        # caso nao haja arquivo no servidor
        if (formato=="n"):
            print("Nao existe arquivo no servidor")
            exit()
        
        # caso haja arquivo no servidor:
        print("Digite o caminho onde o arquivo ""Resposta do Servidor"" deve ficar (ele conterá a resposta do servidor.Ex: C:\Users\PastaDestino): ")
        caminho_arquivo = input()
        caminho_arquivo += "\Resposta do Servidor" + formato
        f = open(caminho_arquivo, "wb")
        while True:
            # recebendo dados do servidor:
            dados = cliente_socket.recv(4096)  # Tamanho do buffer de 4 KB
            if not dados:  # se não tiverem mais dados, sai do loop
                break
            f.write(dados)  # escreve os dados recebidos no arquivo
            
    else:
        print("Operação cancelada.")
