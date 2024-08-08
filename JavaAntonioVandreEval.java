// Projeto Mathematical Ramblings (mathematicalramblings.blogspot.com).

// Software JavaAntonioVandreEval, calculadora de expressões matemáticas.

// Dependências: JDK19, AntonioVandre >= 20231101, mXparser 5.2.1 Orion.

// Licença de uso: Atribuição-NãoComercial-CompartilhaIgual (CC BY-NC-SA).

// Última atualização: 08-08-2024.

import AntonioVandre.*;

import org.mariuszgromada.math.mxparser.*;

import java.net.*;
import java.io.*;
import java.util.*;

public class JavaAntonioVandreEval
    {
    // Início definições de Antonio Vandré Pedrosa Furtunato Gomes (bit.ly/antoniovandrelegado_ontologico).

    static String Version = "08-08-2024";

    static long VersionN = Integer.valueOf(JavaAntonioVandreEval.Version.replace("-", ""));

    // Ocorre uma simples substituição dos primeiros elementos pelos valores dos segundos.

    static String[][] Argumentos = {

            {".T.", "(6.2831853071795864769252867665590057683943387987502116419498891846156328125724179972560696506842)", "Constante tau, o dobro de pi."},

            {".P.", "(1.61803398874989484820458683436563811772030917980576286213544862270526046281890244970720720418939)", "Razão áurea."},

            {".CC.", "(0.91596559417721901505460351493238411077414937428167213426649811962176301977625476947935651292611)", "Constante de Catalan."},

            {".EM.", "(0.57721566490153286060651209008240243104215933593992359880576723488486772677766467093694706329174)", "Constante de Euler-Mascheroni."},

            {".FA.", "(2.50290787509589282228390287321821578638127137672714997733619205677923546317959020670329964974643)", "Constante alfa de Feigenbaum."},

            {".FS.", "(4.66920160910299067185320382046620161725818557747576863274565134300413433021131473713868974402394)", "Constante sigma de Feigenbaum."},

            {".AP.", "(1.20205690315959428539973816151144999076498629234049888179227155534183820578631309018645587360933)", "Constante de Apéry."},

            {".KH.", "(2.68545200106530644530971483548179569382038229399446295305115234555721885953715200280114117493184)", "Constante de Khinchin."},

            {".GK.", "(1.28242712910062263687534256886979172776768892732500119206374002174040630885882646112973649195820)", "Constante de Glaisher-Kinkelin."},

            {".C.", "(299792458)", "Constante c, velocidade da luz no vácuo, no SI."},

            {".G.", "(6.67430E-11)", "Constante G, da Gravitação Universal, no SI."},

            {".H.", "(6.62606891E-34)", "Constante h, de Planck, no SI."},

            {".HH.", "(1.054571621E-34)", "Constante reduzida de Planck, no SI."},

            {".K.", "(1.38065156E-23)", "Constante de Boltzmann, no SI."},

            {".ME.", "(9.1093837015E-31)", "Massa do elétron, no SI."},

            {".MP.", "(1.67262192369E-27)", "Massa do próton, no SI."},

            {".A.", "(6.02214076E23)", "Número de Avogadro, em mol^(-1)."},

            {".AM.", "(6.02214076)", "Número de Avogadro, vezes 10^(-23), em mol^(-1)."},

            {".KE.", "(8.9875517923E9)", "Constante de Coulomb, constante eletrostática, no SI."},

            {".R.", "(8.31446261815324)", "Constante universal dos gases perfeitos, no SI."},

            {".SB.", "(5.670374419E-8)", "Constante de Stefan-Boltzmann, no SI."},

            {".PM.", "(0.00000125663706143591729538505735331180115367886775975004232838997783692312656251448359945121393013684)", "Constante de permeabilidade magnética no vácuo, no SI."},

            {".PE.", "(8.8541878176E-12)", "Constante de permeabilidade elétrica do vácuo, no SI."},

            {".B.", "(0.002897771955185172)", "Constante de deslocamento de Wien, no SI."},

            {".MN.", "(1.67492749804E-27)", "Massa do neutron, no SI."}
        };

    // O elemento da array "Funcoes" é constituído de: primeiro: o identificador; segundo: os argumentos (1, 2, 3...), separados por pontos ".", a não processar (útil para funções que envolvam literais); terceiro: entradas proibidas separadas por ponto "."; quarto: a expressão built-in do mXparser; e quinto: a descrição do que a função faz.

    static String[][] Funcoes = {
            {".HIPOTENUSA.", "", "", "sqrt(.1. * .1. + .2. * .2.)", "Retorna a medida da hipotenusa dadas as medidas dos catetos."},

            {".CORDA.", "", "", "sqrt(2(1 - cos(.1.)))", "Função trigonométrica corda."},

            {".CELSIUSFAHRENHEIT.", "", "", ".1. * 9/5 + 32", "Converte graus Celsius para Fahrenheit."},

            {".FAHRENHEITCELSIUS.", "", "", "(.1. - 32) * 5/9", "Converte graus Fahrenheit para Celsius."},

            {".FAHRENHEITCELSIUS.", "", "", "(.1. - 32) * 5/9", "Converte graus Fahrenheit para Celsius."},

            {".VALORFUNCAO.", "1.2", "", "sum(.2., .3., .3., .1.)", "Retorna o valor de uma função em um ponto do domínio."},

            {".DERIVADA.", "1.2", "", "der(.1., .2., .3.)", "Retorna a derivada de uma função em um ponto do domínio."},

            {".DERIVADASEGUNDA.", "1.2", "", "sum(i, (der(.1., .2., .3. + 1 / .4.) - der(.1., .2., .3. - 1 / .4.)) / (2 / .4.), (der(.1., .2., .3. + 1 / .4.) - der(.1., .2., .3. - 1 / .4.)) / (2 / .4.), i)", "Retorna a segunda derivada de uma função em um ponto do domínio."},

            {".INTEGRAL.", "1.2", "", "int(.1., .2., .3., .4.)", "Retorna a integral de uma função em um intervalo do domínio."},

            {".COMPRIMENTOGRAFICOFUNCAO.", "1.2", "", "sum(i, 1, .5., sqrt((sum(.2., (.4. - .3.) * (i + 1) / (.5. + 1), (.4. - .3.) * (i + 1) / (.5. + 1), .1.) - sum(.2., (.4. - .3.) * i / (.5. + 1), (.4. - .3.) * i / (.5. + 1), .1.))^2 + (sum(.2., (.4. - .3.) * (i + 1) / (.5. + 1), (.4. - .3.) * (i + 1) / (.5. + 1), .1.) - sum(.2., (.4. - .3.) * i / (.5. + 1), (.4. - .3.) * i / (.5. + 1), .1.))^2))", "Retorna o comprimento do gráfico de uma função entre os pontos do domínio dados."},

            {".VELOCIDADEANTONIOVANDRE.", "4.5", "", ".1.((sum(.5., .6., .6., .5.) - .2.) + (sum(.5., .6., .6., .4.) - .3.)(der(.4., .5., .6.))) / sqrt(((sum(.5., .6., .6., .5.) - .2.)^2 + (sum(.5., .6., .6., .4.) - .3.)^2)(1 + (der(.4., .5., .6.))^2))", "Retorna a Velocidade de Antonio Vandré dados os argumentos velocidade, abscissa do ponto de referência, ordenada do ponto de referência, função, variável da função, e ponto do domínio da função."},

            {".VELOCIDADEANGULARANTONIOVANDRE.", "5.6", "", "(((.3. - .1.) + (.4. - .2.)(der(.5., .6., .8.)))(sqrt(((.3. - .1.)^2 + (.4. - .2.)^2)((sum(.6., .8., .8., .6.) - .1.)^2 + (sum(.6., .8., .8., .5.) - .2.)^2))) - (((.3. - .1.)(sum(.6., .8., .8., .6.) - .1.) + (.4. - .2.)(sum(.6., .8., .8., .5.) - .2.))((.3. - .1.)^2 + (.4. - .2.)^2)(2(sum(.6., .8., .8., .6.) - .1.) + 2(sum(.6., .8., .8., .5.) - .2.)(der(.5., .6., .8.))) / (2(sqrt(((.3. - .1.)^2 + (.4. - .2.)^2)((sum(.6., .8., .8., .6.) - .1.)^2 + (sum(.6., .8., .8., .5.) - .2.)^2)))))) / (((.3. - .1.)^2 + (.4. - .2.)^2)((sum(.6., .8., .8., .6.) - .1.)^2 + (sum(.6., .8., .8., .5.) - .2.)^2))(.7. / sqrt(1 + (der(.5., .6., .8.))^2))(-1 / sqrt(1 - ((.3. - .1.)(sum(.6., .8., .8., .6.) - .1.) + (.4. - .2.)(sum(.6., .8., .8., .5.) - .2.))^2 / (((.3. - .1.)^2 + (.4. - .2.)^2)((sum(.6., .8., .8., .6.) - .1.)^2 + (sum(.6., .8., .8., .5.) - .2.)^2))))", "Retorna a Velocidade Angular de Antonio Vandré dados os argumentos abscissa da origem do eixo de referência, ordenada da origem do eixo de referência, abscissa da extremidade do eixo de referência, ordenada da extremidade do eixo de referência, função, variável, velocidade, e ponto do domínio."}
        };

    static String HelpText = "HELP                                        \n      .\':clllc;\'.   ..;clll:,.          \n      .col;\'..,cxxl,,ldo:\'.,lxxl.         \n    .:dc.      .lkkxxl:.  .cdxkx:         \n   .cd:.       .lkkko.    ;xkkkd\'         \n   ,o:.        ,dkkx:     .;cc;.   .......\n    .         .ckkko.         ....\'\'\'\'\'\'\'\'\n              ,dkkx;       ...\'\'\'\'\'\'\'\'\'\'\'\'\n             .ckkko.    ..\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n             \'dkkx:. ..\'\'\'\'\'\',;,\'\'\'\'\'\'\'\'\'\'\n   .:cc;.   .ckkkd,.\'\'\'\'\'\'\'\';odc\'\'\'\'\'\'\'\'\'\'\n  ;dkkkx,   ;xkkkl,\'\'\'\'\'\'\'\';oxl,\'\'\'\'\'\'\'\'\'\'\n  ckkxd:.  ,dxxkxo,\'\'\'\'\'\',cddc,\'\'\'\'\'\'\'\'\'\'\'\n  \'okdc\'.,coc\':dxxo::::clddl;\'\'\'\'\'\'\'\'\'\'\'\'\'\n   .,:clcc;.  .;clooooool:;\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n             .\'\'\'\'\',,,\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n            .\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n            .\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n           .\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n           .\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\'\n\nProjeto Mathematical Ramblings (mathematicalramblings.blogspot.com).\n\nJavaAntonioVandreEval versão " + JavaAntonioVandreEval.Version + ".\n____________________\n\nConstantes matemáticas:\n\n\"pi\": razão entre o comprimento e o diâmetro de uma circunferência.\n\n\".T.\": tau, o dobro de pi.\n\n\"e\": base dos logaritmos naturais.\n\n\".P.\": razão áurea.\n\n\".CC.\": constante de Catalan.\n\n\".EM.\": constante de Euler-Mascheroni.\n\n\".FA.\": constante alfa de Feigenbaum.\n\n\".FS.\": constante sigma de Feigenbaum.\n\n\".AP.\": constante de Apéry.\n\n\".KH.\": constante de Khinchin.\n\n\".GK.\": constante de Glaisher-Kinkelin.\n_____\n\nConstantes físico-químicas:\n\n\".C.\": constante c, velocidade da luz no vácuo, no SI.\n\n\".G.\": constante G, da Gravitação Universal, no SI.\n\n\".H.\": constante h, de Planck, no SI.\n\n\".HH.\": constante reduzida de Planck, no SI.\n\n\".K.\": constante de Boltzmann, no SI.\n\n\".ME.\": massa do elétron, no SI.\n\n\".MP.\": massa do próton, no SI.\n\n\".A.\": número de Avogadro, em mol^(-1).\n\n\".AM.\": número de Avogadro, vezes 10^(-23), em mol^(-1).\n\n\".KE.\": constante de Coulomb, constante eletrostática, no SI.\n\n\".R.\": constante universal dos gases perfeitos, no SI.\n\n\".SB.\": constante de Stefan-Boltzmann, no SI.\n\n\".PM.\": constante de permeabilidade magnética no vácuo, no SI.\n\n\".PE.\": constante de permeabilidade elétrica do vácuo, no SI.\n\n\".B.\": constante de deslocamento de Wien, no SI.\n\n\".MN.\": massa do neutron, no SI.\n\n_____\n\nFunções:\n\n\".HIPOTENUSA.(b, c)\": retorna a medida da hipotenusa dadas as medidas dos catetos.\n\n\".CORDA.(a)\": função trigonométrica corda (https://mathematicalramblings.blogspot.com/2012/06/funcao-trigonometrica-corda_5177.html).\n\n\".CELSIUSFAHRENHEIT.(c)\": converte graus Celsius para Fahrenheit.\n\n\".FAHRENHEITCELCIUS.(f)\": converte graus Fahrenheit para Celsius.\n\n\".VALORFUNCAO.(função, variável, ponto do domínio).\": retorna o valor de uma função em um ponto do domínio.\n\n\".DERIVADA.(função, variável, ponto do domínio).\": retorna a derivada de uma função em um ponto do domínio.\n\n\".DERIVADASEGUNDA.(função, variável, ponto do domínio, precisão).\": retorna a segunda derivada de uma função em um ponto do domínio.\n\n\".INTEGRAL.(função, variável, limite inferior, limite superior).\": Retorna a integral de uma função em um intervalo do domínio.\n\n\".COMPRIMENTOGRAFICOFUNCAO.(função, variável, inf, sup, resolução)\": retorna o comprimento do gráfico de uma função entre os pontos do domínio dados.\n\n\".VELOCIDADEANTONIOVANDRE.(velocidade, abscissa do ponto de referência, ordenada do ponto de referência, função, variável, ponto do domínio)\": retorna a Velocidade de Antonio Vandré dados os argumentos velocidade, abscissa do ponto de referência, ordenada do ponto de referência, função, variável da função, e ponto do domínio da função (https://mathematicalramblings.blogspot.com/2021/06/velocidade-de-antonio-vandre.html).\n\n\".VELOCIDADEANGULARANTONIOVANDRE.(abscissa da origem do eixo de referência, ordenada da origem do eixo de referência, abscissa da extremidade do eixo de referência, ordenada da extremidade do eixo de referência, função, variável, velocidade, ponto do domínio)\": retorna a Velocidade Angular de Antonio Vandré dados os argumentos abscissa da origem do eixo de referência, ordenada da origem do eixo de referência, abscissa da extremidade do eixo de referência, ordenada da extremidade do eixo de referência, função, variável, velocidade, e ponto do domínio (https://mathematicalramblings.blogspot.com/search?q=velocidade+angular+de+antonio+vandr%C3%A9).\n____________________\n\nArgumentos de linha de comando:\n\n\"h\" ou \"help\": mostrar esta mensagem.\n\n\"v\" ou \"version\": mostrar a versão.\n\n\"vn\" ou \"versionnumber\": mostrar a versão em formato numérico (útil para scripts).\n\n____________________\n\nPowered by mXparser 5.2.1 Orion (https://mathparser.org/).\n____________________\n\nPara mais informações, visite \"https://mathparser.org/\"\n\nSugestões ou comunicar erros: \"a.vandre.g@gmail.com\".";

    // Fim definições de Antonio Vandré Pedrosa Furtunato Gomes (bit.ly/antoniovandrelegado_ontologico).

    static String MensagemErroEntradaProibida = "Entrada proibida para uma função.";

    static String MensagemErro = "Erro do Java (não meu) ou entrada inválida.";

    static String VersionUrlLink = "https://github.com/antoniovandre2/JavaAntonioVandreEvalGUI/blob/main/JavaAntonioVandreEvalUpdateHash.txt?raw=true";

    static String CustomConstFilePath="CustomConst.txt";

    public static String MensagemErroAntonioVandreLib = "Requer AntonioVandre >= 20231101.";

    public static void main(String[] args)
        {
        try
            {
            long ValorInteiroLong = Long.parseLong(String.valueOf(AntonioVandre.Versao));
            }
        catch (NumberFormatException e)
            {
            System.out.println(MensagemErroAntonioVandreLib);
            return;
            }

        if (AntonioVandre.Versao < 20231101)
            {
            System.out.println(MensagemErroAntonioVandreLib);
            return;
            }

        boolean isCallSuccessful = License.iConfirmNonCommercialUse("Antonio Vandré Pedrosa Furtunato Gomes (bit.ly/antoniovandre_legadoontologico)");

        String VersionUrlS = "";

        int flagUpdate = 0;
        int flagFetch = 1;
        int flagCustomConst = 0;
        int flagCustomConstRead = 1;

        File CustomConstFile = new File(CustomConstFilePath);

        String[][] CustomConstA = {{}};
        String CustomConstStr = "";

        if (flagUpdate == 1)
            System.out.print("Há uma nova versão disponível para download. ");

        if (args.length == 0)
            {
            System.out.println(JavaAntonioVandreEval.HelpText);
            return;
            }

        if ((args[0].equals("h")) || (args[0].equals("help")))
            {
            System.out.println(JavaAntonioVandreEval.HelpText);
            return;
            }

        if ((args[0].equals("v")) || (args[0].equals("version")))
            {
            System.out.println(JavaAntonioVandreEval.Version);
            return;
            }

        if ((args[0].equals("vn")) || (args[0].equals("versionnumber")))
            {
            System.out.println(JavaAntonioVandreEval.VersionN);
            return;
            }

        if (args.length > 1)
            {
            System.out.println(AntonioVandre.MensagemErroContagemParametros);
            return;
            }

        try
            {
            URL VersionUrl = new URL(VersionUrlLink);

            Scanner VersionUrlSc = new Scanner(VersionUrl.openStream());

            while(VersionUrlSc.hasNextLine())
                VersionUrlS = VersionUrlS + "\n" + VersionUrlSc.nextLine();

            VersionUrlSc.close();
            }
        catch(IOException ex) {flagFetch = 0;}

        if (flagFetch == 1)
            {
            String[] VersionUrlSA = VersionUrlS.split("\\n");

            for (int i = 0; i < VersionUrlSA.length; i++)
                {
                VersionUrlSA[i] = VersionUrlSA[i].replace(" ", "");

                if (! VersionUrlSA[i].equals(""))
                    if (VersionUrlSA[i].charAt(0) != '#')
                        {
                        String s = AntonioVandre.SoNumeros(VersionUrlSA[i]);

                        if (AntonioVandre.NumeroNaturalPositivo(s))
                            if (Integer.parseInt(s) > JavaAntonioVandreEval.VersionN)
                                {
                                flagUpdate = 1;
                                break;
                                }
                        }
                }
            }

        try
            {
            Scanner CustomConstScanner = new Scanner(CustomConstFile);

            while (CustomConstScanner.hasNextLine())
                CustomConstStr = CustomConstStr + "\n" + CustomConstScanner.nextLine();

            CustomConstScanner.close();
            }
        catch(IOException ex) {flagCustomConstRead = 0;}

        if (flagCustomConstRead == 1)
            {
            String[] CustomConstAL = CustomConstStr.split("\\n");
            int flagCustomConstPrim = 0;

            for(int i = 0; i < CustomConstAL.length; i++)
                {
                CustomConstAL[i] = CustomConstAL[i].replace(" ", "");

                if (! AntonioVandre.SoLetras(CustomConstAL[i]).equals(""))
                    if (CustomConstAL[i].charAt(0) != '#')
                        {
                        String[] LinhaA = CustomConstAL[i].split("[,]");

                        if (LinhaA.length != 2)
                            {
                            flagCustomConst = 0;
                            break;
                            }

                        if ((AntonioVandre.SoNumeros(LinhaA[0]).equals("")) && (AntonioVandre.NumeroReal(LinhaA[1])))
                            {
                            String [] LinhaAU = {"." + LinhaA[0] + ".", LinhaA[1]};
                            
                            int tam = CustomConstA.length;

                            if (flagCustomConstPrim == 1)
                                {
                                String[][] buf = new String[tam + 1][];

                                for(int j = 0; j < tam; j++)
                                    buf[j] = CustomConstA[j];

                                buf[tam] = LinhaAU;

                                CustomConstA = buf;
                                }
                            else
                                {
                                CustomConstA[0] = LinhaAU;
                                flagCustomConstPrim = 1;
                                }

                            flagCustomConst = 1;
                            }
                        }
                }
            }

        String expressao = args[0].replace(" ", "");

        if (flagCustomConst == 1)
            for (int i = 0; i < CustomConstA.length; i++)
                expressao = expressao.replace(CustomConstA[i][0], "(" + CustomConstA[i][1] + ")");

        for (int i = 0; i < JavaAntonioVandreEval.Argumentos.length; i++)
            expressao = expressao.replace(JavaAntonioVandreEval.Argumentos[i][0], "(" + JavaAntonioVandreEval.Argumentos[i][1] + ")");

        String fer = FuncaoEval(expressao);

        if (fer.equals(JavaAntonioVandreEval.MensagemErro))
            {
            System.out.println(JavaAntonioVandreEval.MensagemErro);
            return;
            }

        if (fer.equals(JavaAntonioVandreEval.MensagemErroEntradaProibida))
            {
            System.out.println(JavaAntonioVandreEval.MensagemErroEntradaProibida);
            return;
            }

        Expression expr = new Expression(fer);

        double resultado;

        try
            {
            resultado = expr.calculate();
            }
        catch (Exception e)
            {
            System.out.println(AntonioVandre.MensagemErro);
            return;
            }

        if (Double.isNaN(resultado))
            {
            System.out.println(AntonioVandre.MensagemErro);
            return;
            }

        if (Math.abs(resultado) > AntonioVandre.MaximoValorReal)
            {
            System.out.println(AntonioVandre.MensagemErroNumeroGrandeDemais);
            return;
            }
        else
            System.out.println(resultado);

        return;
        }

        public static String FuncaoEval(String expressao)
            {
            String exprt = expressao;
            String exprt2 = "";

            int flaga = 0;

            do
                {
                for (int i = 0; i < expressao.length(); i++)
                    for (int j = 0; j < JavaAntonioVandreEval.Funcoes.length; j++)
                        {
                        String exprarg = JavaAntonioVandreEval.Funcoes[j][3];

                        if (i + JavaAntonioVandreEval.Funcoes[j][0].length() < expressao.length())
                            if (expressao.substring(i, i + JavaAntonioVandreEval.Funcoes[j][0].length()).equals(JavaAntonioVandreEval.Funcoes[j][0]))
                                {
                                flaga = 0;
                                int flagg = 0;
                                int ct = 0;
                                int ag = 1;
                                int inicio = i;
                                int fim = -1;
                                int inicioarg = -1;
                                int fimarg = -1;

                                if (expressao.charAt(i + JavaAntonioVandreEval.Funcoes[j][0].length()) != '(')
                                    return(JavaAntonioVandreEval.MensagemErro);

                                for (int k = i + JavaAntonioVandreEval.Funcoes[j][0].length(); k < expressao.length(); k++)
                                    {
                                    if (expressao.charAt(k) == '(')
                                        {
                                        flaga = 1;
                                        if (ct == 0) inicioarg = k + 1;
                                        ct++;
                                        }

                                    if ((ct == 1) && (expressao.charAt(k) == ','))
                                        {
                                        flagg = 1;
                                        ag++;
                                        fimarg = k - 1;
                                        }

                                    if (expressao.charAt(k) == ')')
                                        {
                                        if (ct == 1)
                                            {
                                            ag++;
                                            fim = k;
                                            fimarg = k - 1;
                                            flagg = 1;
                                            }

                                        ct--;
                                        }

                                    if (flagg == 1)
                                        {
                                        String[] prargs = JavaAntonioVandreEval.Funcoes[j][2].split("[.]");

                                        int flagpr = 0;

                                        for (var l = 0; l < prargs.length; l++)
                                            if (! prargs[l].equals(""))
                                                if (prargs[l].equals(expressao.substring(inicioarg, fimarg + 1)))
                                                    flagpr = 1;

                                        if (flagpr == 1)
                                            return(JavaAntonioVandreEval.MensagemErroEntradaProibida);

                                        String[] igargs = JavaAntonioVandreEval.Funcoes[j][1].split("[.]");

                                        int flagig = 0;

                                        for (var l = 0; l < igargs.length; l++)
                                            if (! igargs[l].equals(""))
                                                if (Integer.parseInt(igargs[l]) == ag - 1)
                                                    flagig = 1;

                                        if (flagig == 0)
                                            exprarg = exprarg.replace("." + Integer.toString(ag - 1) + ".", "(" + expressao.substring(inicioarg, fimarg + 1) + ")");
                                        else
                                            exprarg = exprarg.replace("." + Integer.toString(ag - 1) + ".", expressao.substring(inicioarg, fimarg + 1));

                                        inicioarg = k + 1;
                                        flagg = 0;
                                        }

                                    if ((ct == 0) && (flaga == 1))
                                        {
                                        String[] prargs = JavaAntonioVandreEval.Funcoes[j][2].split("[.]");

                                        int flagpr = 0;

                                        for (var l = 0; l < prargs.length; l++)
                                            if (! prargs[l].equals(""))
                                                if (prargs[l].equals(expressao.substring(inicio, fim + 1)))
                                                    flagpr = 1;

                                        if (flagpr == 1)
                                            return(JavaAntonioVandreEval.MensagemErroEntradaProibida);

                                        String[] igargs = JavaAntonioVandreEval.Funcoes[j][1].split("[.]");

                                        int flagig = 0;

                                        for (var l = 0; l < igargs.length; l++)
                                            if (! igargs[l].equals(""))
                                                if (Integer.parseInt(igargs[l]) == ag - 1)
                                                    flagig = 1;

                                        if (flagig == 0)
                                            {
                                            String fer = FuncaoEval(exprarg);

                                            if (fer.equals(JavaAntonioVandreEval.MensagemErroEntradaProibida))
                                                {
                                                flaga = 0;
                                                return(JavaAntonioVandreEval.MensagemErroEntradaProibida);
                                                }

                                            if (fer.equals(JavaAntonioVandreEval.MensagemErro))
                                                {
                                                flaga = 0;
                                                return(JavaAntonioVandreEval.MensagemErro);
                                                }

                                            Expression exprev = new Expression(fer);

                                            double exprevr;

                                            try
                                                {
                                                exprevr = exprev.calculate();
                                                }
                                            catch (Exception e)
                                                {
                                                flaga = 0;
                                                return(JavaAntonioVandreEval.MensagemErro);
                                                }

                                            if (Double.isNaN(exprevr))
                                                {
                                                flaga = 0;
                                                return(JavaAntonioVandreEval.MensagemErro);
                                                }
                                    
                                            exprt = exprt.replace(expressao.substring(inicio, fim + 1), Double.toString(exprevr));
                                            }
                                        else
                                            exprt = exprt.replace(expressao.substring(inicio, fim + 1), exprarg);

                                        flaga = 0;
                                        }
                                    }
                                }
                        }

                int tam = exprt.length();

                if (tam > 1)
                    for (int i = 0; i < tam - 1; i++)
                        {
                        int flagt = 0;

                        if ((exprt.charAt(i) == '+') || (exprt.charAt(i) == '-') || (exprt.charAt(i) == '*') || (exprt.charAt(i) == '/') || (exprt.charAt(i) == '^'))
                            flagt++;

                        if ((exprt.charAt(i + 1) == '+') || (exprt.charAt(i + 1) == '-') || (exprt.charAt(i + 1) == '*') || (exprt.charAt(i + 1) == '/') || (exprt.charAt(i + 1) == '^'))
                            flagt++;

                        if (flagt == 2)
                            return(JavaAntonioVandreEval.MensagemErro);
                        }

                label: for (int i = 0; i < tam; i++)
                    {
                    int flagt = 0;

                    if (i > 0)
                        {
                        if ((exprt.charAt(i - 1) != 'E') && (exprt.charAt(i) == '-'))
                            flagt = 1;
                        }
                    else
                        if (exprt.charAt(i) == '-')
                            flagt = 1;

                    if (flagt == 1)
                        {
                        for (int j = i + 1; j < tam; j++)
                            {
                            if ((j < tam - 1) && (AntonioVandre.NumeroNatural("" + exprt.charAt(j)) || (exprt.charAt(j) == '.') || (exprt.charAt(j) == 'E') || ((exprt.charAt(j - 1) == 'E') && ((exprt.charAt(j) == '+') || (exprt.charAt(j) == '-')))))
                                continue;

                            exprt2 = exprt2 + "+(-1)";
                            continue label;
                            }
                        }
                    else
                        exprt2 = exprt2 + exprt.charAt(i);
                    }

                expressao = exprt2;
                } while (flaga == 1);

            return(exprt2);
            }
}
