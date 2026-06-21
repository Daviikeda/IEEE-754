
public static void main(String[] args) {

    ieee754 ieee = new ieee754();
    /* 5) Solicite dois números decimais como entrada */
    String resultado1 = ieee.transformarDecimalEmIEEE754(10.75);
    String resultado2 = ieee.transformarDecimalEmIEEE754(100);

    /* 6) Converta cada número para sua representação IEEE 754 (32 bits),
     mostrando os campos de sinal, expoente e mantissa. */
    System.out.println("10.75 em IEEE754 = " + resultado1);
    System.out.println("100 em IEEE754 = " + resultado2);

    /* 7) Realize uma operação de soma ou subtração e
     apresente o resultado em decimal e em formato IEEE 754. */
    double numA = 10.75;
    double numB = 100;

    ieee754 ieeeA = new ieee754(numA);
    ieee754 ieeeB = new ieee754(numB);

    ieee754 resultadoIEEE = ieeeA.somar(ieeeB);
    double resultadoDecimal = numA + numB;

    System.out.println("Soma em decimal:");
    System.out.println(numA + " + " + numB + " = " + resultadoDecimal);

    System.out.println("\nSoma em IEEE 754:");
    System.out.println(resultado1 + " + " + resultado2 + " = " + resultadoIEEE);
}