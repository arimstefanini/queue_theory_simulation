# queue_theory_simulation
Queue Theory Simulation

Trabalho 2 – Análise de Desempenho 
Data de entrega: 16/10/2020 até as 23:55 no Moodle. Valor: 4,0.
Pode ser feito em dupla ou individualmente.
Prof. Flavio Barbieri Gonzaga – Universidade Federal de Alfenas – UNIFAL-MG
No trabalho 2 pensaremos no nosso simulador como sendo uma loja, e cujo negócio deu muito
certo ($$$)! O proprietário da loja já possui um módulo de treinamento e sistema otimizados,
de modo que cada caixa da loja gasta  em média 1 minuto  (seguindo v.a. Exponencial) para
atender cada cliente.
Como   o   movimento   da   loja   está   em   franca   expansão,   o   seu   simulador   deve   ser   capaz   de
suportar quantos caixas forem necessários (o limite é a memória do computador). Assim, no
início da simulação deve se informar quantos caixas estarão disponíveis ao longo da mesma.
Além da quantidade de caixas, outro parâmetro  a ser informado no início da simulação é a
relação entre a chegada e a capacidade total  de atendimento . Por exemplo, suponha que a
simulação   seja   executada   com   10   caixas   (como   cada   caixa   leva   em   média   1   minuto   para
atender   cada   cliente),   constata-se   que   a   loja   possui   a   capacidade   agregada   de   atender   em
média 10 clientes por minuto, ou seja, um em cada caixa. Caso o parâmetro de   relação entre a
chegada e a capacidade total de atendimento  seja informado como sendo 0,8, isso significa
que a taxa de média de chegada deverá ser de 80% da capacidade total de atendimento da
loja, ou nesse caso, de 8 clientes/minuto (também de acordo com v.a. Exponencial).
Tomando como base o código de simulação desenvolvido até aqui, vocês devem executar 8
cenários na simulação, com as respectivas características:
 - 5 caixas
    Relação entre a chegada e capacidade total de atendimento == 0,4
    Relação entre a chegada e capacidade total de atendimento == 0,8
    Relação entre a chegada e capacidade total de atendimento == 0,9
    Relação entre a chegada e capacidade total de atendimento == 0,99
 - 10 caixas
    Relação entre a chegada e capacidade total de atendimento == 0,4
    Relação entre a chegada e capacidade total de atendimento == 0,8
    Relação entre a chegada e capacidade total de atendimento == 0,9
    Relação entre a chegada e capacidade total de atendimento == 0,99
