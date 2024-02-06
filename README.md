# Calculadora Android com Jetpack Compose

## Descrição do Projeto
Este projeto tem como objetivo proporcionar uma experiência prática na utilização do Jetpack Compose para desenvolvimento de interfaces de usuário no Android. A proposta é construir uma calculadora simples, mas funcional, utilizando Kotlin e Jetpack Compose.

## Pré-requisitos
- [Android Studio](https://developer.android.com/studio?gclid=Cj0KCQiAn-2tBhDVARIsAGmStVn0ZRoVaj-yzX9n2mn51-iL_fKmhvQv8iyFdjaUMuV_gyMgxlYToZoaAmQSEALw_wcB&gclsrc=aw.ds&hl=pt-br)
- Conhecimento básico em [Kotlin](https://kotlinlang.org/) e Android Studio

## Implementação da Calculadora

### 1. Layout da Calculadora
Crie um layout simples para a calculadora utilizando Compose. Utilize `Column`, `Row`, e outros componentes fornecidos pelo Jetpack Compose para criar a disposição dos botões.

### 2. Lógica da Calculadora
Implemente a lógica da calculadora em um [Serviço do Android](https://developer.android.com/guide/components/services?hl=pt-br). Crie funções para manipular as operações básicas (adição, subtração, multiplicação, divisão) e para processar os números digitados.

### 3. Conectando Interface com Lógica
Conecte os botões da interface com as funções da lógica da calculadora. Para isso, realize o o vinculo do serviço com a [Activity](https://developer.android.com/reference/android/app/Activity) responsável por montar a tela e utilize `onClick` ou outros eventos fornecidos pelo Jetpack Compose para realizar as operações correspondentes quando um botão é pressionado.

## Testando a Calculadora
Execute o aplicativo no emulador ou dispositivo Android e teste a calculadora para garantir que todas as operações estejam funcionando conforme esperado.

## Considerações Finais
Este exercício é uma introdução ao Jetpack Compose, proporcionando a oportunidade de aplicar os fundamentos aprendidos. Sinta-se à vontade para adicionar recursos adicionais ou melhorias à calculadora conforme desejar.

## Recursos Adicionais
- [Documentação oficial do Jetpack Compose](https://developer.android.com/jetpack/compose)
- [Codelab - Jetpack Compose](https://developer.android.com/codelabs/jetpack-compose-basics)
