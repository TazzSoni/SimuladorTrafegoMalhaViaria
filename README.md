<!-- Visualizador online: https://stackedit.io/ -->
 ![Logo da UDESC Alto Vale](http://www1.udesc.br/imagens/id_submenu/2019/marca_alto_vale_horizontal_assinatura_rgb_01.jpg)

---

# Procurar Palavras em Arquivos com Threads

Atividade desenvolvida para a disciplina de Desenvolvimento de Sistemas Paralelos e Distribuídos do [Centro de Educação Superior do Alto Vale do Itajaí (CEAVI/UDESC)](https://www.udesc.br/ceavi)<br>

# Sumário
* [Equipe](#equipe)
* [Resultados](#resultados)
* [Especificações](#especificações)

## [Equipe](#equipe)
 - [**Rodrigo Souza Tassoni**](mailto:tazzsoni@gmail.com) - [GitHub](https://github.com/tazzsoni)
 - [**Samuel Koepsel**](mailto:sjoepsel@hotmail.com.br) - [GitHub](https://github.com/samuelkoepsel1)
 
## [Atividade](#atividade)

Desenvolver um simulador de tráfego. Neste simulador há veículos que se movem pelas vias de uma malha viária. A malha é informada através de arquivo.

## [Especificações](#especificações)

Especificação dos Veículos
a) Cada veículo deve ser um thread.
b) O veículo se movimenta pela malha, uma posição por vez, respeitando o sentido de fluxo da
pista. O veículo só pode se mover caso a posição à frente esteja livre.
c) Ao se deparar com um cruzamento:
• Deve escolher, aleatoriamente, uma das vias de saída do cruzamento para seguir. A
escolha deve ser feita antes do veículo ingressar no cruzamento.
d) Só deve se mover pelo cruzamento se todas as posições por onde vai passar estiverem
totalmente livres (exclusão mútua).
e) Não deve bloquear o cruzamento de outros veículos (ficar parado no cruzamento).
f) Novos veículos são inseridos nos pontos de entrada da malha (ver especificação da malha)
g) Ao atingir um ponto de saída (ver especificação da malha), o veículo deve ser encerrado.
h) Veículos possuem velocidades de movimentação diferente (tempo de sleep a cada passo).
