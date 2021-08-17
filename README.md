<!-- Visualizador online: https://stackedit.io/ -->
 ![Logo da UDESC Alto Vale](http://www1.udesc.br/imagens/id_submenu/2019/marca_alto_vale_horizontal_assinatura_rgb_01.jpg)

---

# Simulador de Malha viária

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

*Especificação dos Veículos*

a) Cada veículo deve ser um thread.<br>
b) O veículo se movimenta pela malha, uma posição por vez, respeitando o sentido de fluxo dapista. O veículo só pode se mover caso a posição à frente esteja livre.<br>
c) Ao se deparar com um cruzamento:<br>
 - Deve escolher, aleatoriamente, uma das vias de saída do cruzamento para seguir. Aescolha deve ser feita antes do veículo ingressar no cruzamento.<br>

d) Só deve se mover pelo cruzamento se todas as posições por onde vai passar estiverem totalmente livres (exclusão mútua).<br>
e) Não deve bloquear o cruzamento de outros veículos (ficar parado no cruzamento).<br>
f) Novos veículos são inseridos nos pontos de entrada da malha (ver especificação da malha).<br>
g) Ao atingir um ponto de saída (ver especificação da malha), o veículo deve ser encerrado.<br>
h) Veículos possuem velocidades de movimentação diferente (tempo de sleep a cada passo).<br>

*Especificação da Malha Viária*

a) Deve ser carregada de um arquivo texto.<br>
b) Nas duas primeiras linhas estão a quantidade de linhas e colunas da malha, respectivamente.<br>
c) As próximas linhas especificam o tipo de cada segmento (célula) da malha:<br>

![image](https://user-images.githubusercontent.com/45270751/125324650-7bc18d80-e316-11eb-95a2-db304a22ea5c.png)

d) Características gerais das vias:<br>
 - São sempre horizontais ou verticais (não haverá vias em diagonal).<br>
 - São de mão dupla (ou seja, possuem duas pistas).<br>
 - Nas bordas, só haverá vias perpendiculares.<br>
 - Entre vias paralelas, haverá sempre ao menos uma linha ou coluna em branco (ou seja,não haverá vias “grudadas” umas nas outras).<br>
e) Identificação de pontos de entrada e de saída de veículos:<br>
 - Entrada: posição inicial da pista que está em uma das bordas da malha.<br>
 - Saída: posição final da pista que está em uma das bordas da malha.<br>
f) Exemplo de malha e arquivo:<br>

![image](https://user-images.githubusercontent.com/45270751/125324886-b88d8480-e316-11eb-8fb1-f9e3cbfaa812.png)

*Especificação Geral do Sistema*

a) A malha e os veículos devem ser visualizados em uma interface gráfica.<br>
b) Deve possuir opções para:<br>
1. limitar quantidade de veículos: usuário informar a quantidade máxima de veículos que poderão estar circulando simultaneamente na malha. O sistema fica inserindo veículos até atingir esta quantidade. Assim que veículos sairem da malha, novos veículos devem ser inseridos para manter a quantidade máxima de circulando simultaneamente.<br>
[opcional] usuário informar um intervalo de inserção de veículos (de quanto em quanto tempo um veículo é inserido).<br>
2. iniciar simulação: fica inserindo veículos, que se movimentam na malha, respeitando a quantidade descrita anteriormente no item (1).<br>
3. encerrar simulação: para de inserir e encerra imediatamente todos os veículos.<br>
[opcional] encerrar e aguardar veículos saírem da malha.<br>

c) Deve suportar tanto semáforos quanto monitores. Deve haver uma opção para o usuário escolher qual destes mecanismos será utilizado.<br>

