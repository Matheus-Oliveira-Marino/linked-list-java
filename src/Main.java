public class Main
{
    public static void main(String[] args)
    {
        try
        {
            ListaSimplesDesordenada<String> lis; // A)
            lis = new ListaSimplesDesordenada<>(); // B)

            ListaSimplesDesordenada<String> lis2;
            lis2 = new ListaSimplesDesordenada<>();

            lis2.guardeUmItemNoInicio("MySQL");
            lis2.guardeUmItemNoInicio("MongoDB");
            lis2.guardeUmItemNoFinal("Firebase");

            //Apenas estes dois trechos de códigos criam uma lista vazia sem nenhuma info e sem nós.

            // Apenas com esse número, haverá apenas um nó: inicio e fim
            //lis.guardeUmItemNoInicio("C++"); // C)
            lis.guardeUmItemNoInicio("C++");
            lis.guardeUmItemNoInicio("C"); // D)
            lis.guardeUmItemNoFinal("Java"); // E)
            //lis.guardeUmItemNoInicio("C");
            //lis.guardeUmItemNoInicio("C");
             System.out.println("[" + lis.recupereItemDoInicio() + "]");
//            System.out.println("[" + lis.recupereItemDoFinal() + "]");


            //lis.guardeUmItemNoInicio("Teste");
           // lis.removaItemDoInicio();
           // lis.removaItemDoFinal();
           // lis.removaItemDoFinal();
            System.out.println(lis.getQuantidade());
            //System.out.println(lis.tem("JAVA"));
            //lis.removaItemIndicado("C++");

//            System.out.println(lis.getEnesimaPosicao(1));
            System.out.println(lis);
//            lis.removaItemDoFinal();
//            lis.removaItemDoFinal();
            System.out.println("Exercicios: ------------------------------------------");
            System.out.println("------------------------");

//            System.out.println("O elemento indicado aparece "+ lis.quantasVezesTemOElemento("C") + " vez(es)");
//            System.out.println(lis.obterTamanhoDaLista() + " unidades");
            //System.out.println(lis.inverteLista(String.valueOf(lis)));

           // System.out.println(lis.inverteLista(lis));
            System.out.println(lis.inverteListaSemCriarOutra());

           // System.out.println(lis.ConcatenaNaListaTHIS(lis2));
            //System.out.println(lis.ConcatenaNaLista2(lis, lis2));

           // System.out.println(lis.elementosComuns("Java"));

            // o Resultado final ficaria: [C, C++, java]
           // System.out.println(lis); //toString será chamado.Irá printar os caracteerees que estiver em ret.
        }
        catch (Exception e)
        {
            //Sei que não vou passar null para os métodos de inserir
        }
    }
}