public class ieee754 {

    private int sinal;
    private String expoente;
    private String mantissa;

    public ieee754() {
    }

    public ieee754(double valor) {
        transformarDecimalEmIEEE754(valor);
    }

    public int getSinal() {
        return sinal;
    }

    public String getExpoente() {
        return expoente;
    }

    public String getMantissa() {
        return mantissa;
    }

    /*
     * Converte decimal para IEEE754 (32 bits)
     */
    public String transformarDecimalEmIEEE754(double valor) {

        if (valor == 0.0) {
            sinal = 0;
            expoente = "00000000";
            mantissa = "00000000000000000000000";
            return "00000000000000000000000000000000";
        }

        sinal = obterSinal(valor);
        valor = Math.abs(valor);

        int parteInteira = (int) valor;
        double parteFracionaria = valor - parteInteira;

        String binInteiro = inteiroParaBinario(parteInteira);
        String binFracao = fracaoParaBinario(parteFracionaria, 50);

        int expoenteReal;
        String normalizado;

        if (parteInteira != 0) {
            expoenteReal = binInteiro.length() - 1;
            normalizado = "1." + binInteiro.substring(1) + binFracao;
        } else {
            int primeiroUm = binFracao.indexOf('1');
            expoenteReal = -(primeiroUm + 1);
            normalizado = "1." + binFracao.substring(primeiroUm + 1);
        }

        expoente = gerarExpoente(expoenteReal);
        mantissa = gerarMantissa(normalizado);

        return sinal + expoente + mantissa;
    }

    /*
     * Converte IEEE754 para decimal
     */
    public double paraDecimal() {

        if (expoente.equals("00000000")
                && mantissa.equals("00000000000000000000000")) {
            return 0.0;
        }

        int expoenteArmazenado = Integer.parseInt(expoente, 2);
        int expoenteReal = expoenteArmazenado - 127;

        double significando = 1.0;

        for (int i = 0; i < mantissa.length(); i++) {
            if (mantissa.charAt(i) == '1') {
                significando += Math.pow(2, -(i + 1));
            }
        }

        double valor = significando * Math.pow(2, expoenteReal);

        if (sinal == 1) {
            valor *= -1;
        }

        return valor;
    }

    /*
     * Soma
     */
    public ieee754 somar(ieee754 outro) {
        double resultado = this.paraDecimal() + outro.paraDecimal();
        return new ieee754(resultado);
    }

    /*
     * Subtração
     */
    public ieee754 subtrair(ieee754 outro) {
        double resultado = this.paraDecimal() - outro.paraDecimal();
        return new ieee754(resultado);
    }

    /*
     * Multiplicação
     */
    public ieee754 multiplicar(ieee754 outro) {
        double resultado = this.paraDecimal() * outro.paraDecimal();
        return new ieee754(resultado);
    }

    /*
     * Divisão
     */
    public ieee754 dividir(ieee754 outro) {

        double divisor = outro.paraDecimal();

        if (divisor == 0.0) {
            throw new ArithmeticException("Divisão por zero.");
        }

        double resultado = this.paraDecimal() / divisor;
        return new ieee754(resultado);
    }

    /*
     * Retorna 1 para negativo e 0 para positivo
     */
    public int obterSinal(double valor) {
        return valor < 0 ? 1 : 0;
    }

    /*
     * Converte parte inteira para binário
     */
    public String inteiroParaBinario(int numero) {

        if (numero == 0) {
            return "0";
        }

        String binario = "";

        while (numero > 0) {
            binario = (numero % 2) + binario;
            numero /= 2;
        }

        return binario;
    }

    /*
     * Converte parte fracionária para binário
     */
    public String fracaoParaBinario(double fracao, int limiteBits) {

        String binario = "";

        while (fracao > 0 && binario.length() < limiteBits) {

            fracao *= 2;

            if (fracao >= 1) {
                binario += "1";
                fracao -= 1;
            } else {
                binario += "0";
            }
        }

        return binario;
    }

    /*
     * Gera expoente com bias 127
     */
    public String gerarExpoente(int expoenteReal) {

        int expoenteComBias = expoenteReal + 127;
        String binario = Integer.toBinaryString(expoenteComBias);

        return String.format("%8s", binario).replace(' ', '0');
    }

    /*
     * Extrai e completa a mantissa
     */
    public String gerarMantissa(String normalizado) {

        String mantissa = normalizado.replace(".", "").substring(1);

        while (mantissa.length() < 23) {
            mantissa += "0";
        }

        return mantissa.substring(0, 23);
    }

    /*
     * Retorna os 32 bits completos
     */
    public String getBits() {
        return sinal + expoente + mantissa;
    }

    @Override
    public String toString() {

        return "Bits: " + getBits()
                + "\nSinal: " + sinal
                + "\nExpoente: " + expoente
                + "\nMantissa: " + mantissa
                + "\nDecimal: " + paraDecimal();
    }
}