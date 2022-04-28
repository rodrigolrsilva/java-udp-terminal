# java-udp-terminal
Comunicação cliente/servidor baseada em UDP via terminal com Docker

Programa em Java para a comunicação entre cliente e servidor via UDP para a disciplina de "Redes de Computadores: Aplicação e Transporte" do curso de graduação de Ciência da Computação da Unisinos. O mesmo código pode ser executado por diferentes computadores, com o número de argumentos definindo se o usuário em questão é servidor ou cliente (o servidor apenas fornece uma porta para seu socket, enquanto o cliente recebe dois IPs e porta de destino). Testado com containers Ubuntu via Docker.

Planos futuros incluem principalmente a conversão em um sistema Peer-to-peer(P2P), que era o objetivo inicial mas que por enquanto não foi possível, o que possibilitaria a implementação de, entre outras coisas, um módulo funcional para a transmissão e visualização de comandos bash.
