import java.lang.reflect.*;
import java.util.concurrent.ExecutionException;

public class ListaSimplesDesordenada <X>
{
    private class No
    {
        private X  info; // informação a ser guardado
        private No prox; // nó para acessar o próximo nó

        public No (X i, No p)
        {
            this.info = i; // informação a ser guardada
            this.prox = p; // quem será o próximo nó do novo nó
        }

        public No (X i)
        {
            this.info = i;
            this.prox = null;
        }

        public X getInfo ()
        {
            return this.info;
        }

        public No getProx ()
        {
            return this.prox;
        }

        public void setInfo (X i) // Mudar a informação de um nó
        {
            this.info = i;
        }

        public void setProx (No p) // Mudar o próximo nó
        {
            this.prox = p;
        }

    } //fim da classe No

    private No primeiro, ultimo; //Atributos da classe lista, TODA LISTA DEVE POSSUIR ESTES ATRIBUTOS!

    /* OBS: para saltar de um nó para outro, é preciso sempre sair do primeiro para o segundo, segundo para
    o terceiro, e por assim vai, SEMPRE EM SEQUÊNCIA! */

    public ListaSimplesDesordenada ()
    {
        this.primeiro=this.ultimo=null;
    }

    private X meuCloneDeX (X x)
    {
        X ret=null;

        try
        {
            Class<?> classe         = x.getClass();
            Class<?>[] tipoDosParms = null;
            Method metodo           = classe.getMethod("clone",tipoDosParms);
            Object[] parms          = null;
            ret                     = (X)metodo.invoke(x,parms);
        }
        catch(NoSuchMethodException erro)
        {}
        catch(IllegalAccessException erro)
        {}
        catch(InvocationTargetException erro)
        {}

        return ret;
    }

    public void guardeUmItemNoInicio (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        X inserir = null;
        if (i instanceof Cloneable)
            inserir = (X)meuCloneDeX(i);
        else
            inserir = i;

        this.primeiro = new No (inserir,this.primeiro);

        if (this.ultimo==null)
            this.ultimo=this.primeiro;
    }

    public void guardeUmItemNoFinal (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        X inserir=null;
        if (i instanceof Cloneable)
            inserir = (X)meuCloneDeX(i);
        else
            inserir = i;

        if (this.ultimo==null) // && this.primeiro==null --> Lista não inicializada
        {
            this.ultimo   = new No (inserir);
            this.primeiro = this.ultimo;
        }
        else
        {
            this.ultimo.setProx (new No (inserir)); //Este endereço deve virar o próximo do último
            this.ultimo = this.ultimo.getProx();
        }
    }

    public X recupereItemDoInicio () throws Exception
    {
        if (this.primeiro==null/*&&this.fim==null)*/)
            throw new Exception ("Nada a obter");

        X ret = this.primeiro.getInfo();
        if (ret instanceof Cloneable)
            ret = meuCloneDeX (ret);

        return ret;
    }

    public X recupereItemDoFinal () throws Exception
    {
        if (this.primeiro==null/*&&this.ultimo==null)*/)
            throw new Exception ("Nada a obter");

        X ret = this.ultimo.getInfo();
        if (ret instanceof Cloneable)
            ret = meuCloneDeX (ret);

        return ret;
    }

    public void removaItemDoInicio () throws Exception
    {
        if (this.primeiro==null /*&& this.ultimo==null*/)
            throw new Exception ("Nada a remover");

        if (this.primeiro==this.ultimo) //so 1 elemento
        {
            this.primeiro=this.ultimo=null;
            return;
        }

//        No atual = this.primeiro.getProx();
//         this.primeiro.setProx(null);
            //this.primeiro = atual;
        this.primeiro = this.primeiro.getProx();
    }

    public void removaItemDoFinal () throws Exception
    {
        if (this.primeiro==null/*&&this.ultimo==null*/)
            throw new Exception ("Nada a remover");

        if (this.primeiro==this.ultimo) //so 1 elemento
        {
            this.primeiro=this.ultimo=null;
            return;
        }

        No atual;
        for (atual=this.primeiro;
             atual.getProx()!=this.ultimo;
             atual=atual.getProx())
            /*comando vazio*/;

        atual.setProx(null);
        this.ultimo=atual;
    }

    public int getQuantidade ()
    {
        No  atual=this.primeiro;
        int ret = 0;

        while (atual!=null)
        {
            ret++;
            atual = atual.getProx();
        }

        return ret;
    }

    public boolean tem (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        No atual=this.primeiro;

        while (atual!=null)
        {
            if (i.equals(atual.getInfo()))
                return true;

            atual = atual.getProx();
        }

        return false;
    }

    public void removaItemIndicado (X i) throws Exception
    {
        if (i==null)
            throw new Exception ("Informacao ausente");

        boolean removeu=false;

        for(;;) // FOR EVER (repete até break)
        {
            if (this.primeiro==null/*&&this.ultimo==null*/)
                break;

            if (!i.equals(this.primeiro.getInfo()))
                break;

            if (this.ultimo==this.primeiro) //Apenas 1 elemento
                this.ultimo=null;

            this.primeiro=this.primeiro.getProx();

            removeu=true;
        }

        if (this.primeiro!=null/*&&this.ultimo!=null*/)
        {
            No atual=this.primeiro;

            forever:for(;;) // repete ate break
            {
                if (atual.getProx()==null)
                    break;

                while (i.equals(atual.getProx().getInfo()))
                {
                    if (this.ultimo==atual.getProx())
                        this.ultimo=atual;

                    atual.setProx(atual.getProx().getProx());

                    removeu=true;

                    if (atual==this.ultimo)
                        break forever;
                }

                atual=atual.getProx();
            }
        }

        if (!removeu)
            throw new Exception ("Informacao inexistente");
    }

    public boolean isVazia ()
    {
        return this.primeiro==null/*&&this.ultimo==null*/;
    }

    public String toString ()
    {
        String ret="["; // F)

        No atual=this.primeiro; // G)

        while (atual!=null)
        {
            ret=ret+atual.getInfo(); // H)

            if (atual!=this.ultimo)
                ret=ret+",";

            atual=atual.getProx(); //pega o 'info' de 'atual' e armazena em 'atual'.
        }

        return ret+"]";
    }

    public X getEnesimaPosicao(int x) throws Exception // Deve recuperar a enésima posição
    {
        if(x < 0) throw new Exception("Posição inválida");
        No atual = this.primeiro;
        int posicao = 0;
        while(atual != null)
        {
            if(posicao == x)
            {
                if(atual.getInfo() instanceof Cloneable) return meuCloneDeX(atual.getInfo());
                else return atual.getInfo();

            }
            posicao++;
            atual = atual.getProx();


        }
        throw new Exception("Não há este índice na lista");
    }

    public boolean equals (Object obj)
    {
        if (this==obj)
            return true;

        if (obj==null)
            return false;

        if (this.getClass()!=obj.getClass())
            return false;

        ListaSimplesDesordenada<X> lista =
                (ListaSimplesDesordenada<X>)obj;

        No atualThis =this .primeiro;
        No atualLista=lista.primeiro;

        while (atualThis!=null && atualLista!=null)
        {
            if (!atualThis.getInfo().equals(atualLista.getInfo()))
                return false;

            atualThis  = atualThis .getProx();
            atualLista = atualLista.getProx();
        }

        if (atualThis!=null  /* && atualLista==null */)
            return false;

        if (atualLista!=null /* && atualThis ==null */)
            return false;

        // atualThis==null && atualLista==null
        return true;
    }

    public int hashCode ()
    {
        final int PRIMO = 13; // qualquer número primo serve

        int ret=666; // qualquer inteiro positivo serve

        for (No atual=this.primeiro; atual!=null; atual=atual.getProx())
            ret = PRIMO*ret + atual.getInfo().hashCode();

        if (ret<0) ret = -ret;

        return ret;
    }

    // construtor de copia
    public ListaSimplesDesordenada (ListaSimplesDesordenada<X> modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        if (modelo.primeiro==null)
            return; // sai do construtor, pq this.primeiro ja é null

        this.primeiro = new No (modelo.primeiro.getInfo());

        No atualDoThis   = this  .primeiro;
        No atualDoModelo = modelo.primeiro.getProx();

        while (atualDoModelo!=null)
        {
            atualDoThis.setProx (new No (atualDoModelo.getInfo()));
            atualDoThis   = atualDoThis  .getProx ();
            atualDoModelo = atualDoModelo.getProx ();
        }

        this.ultimo = atualDoThis;
    }

    public Object clone ()
    {
        ListaSimplesDesordenada<X> ret=null;

        try
        {
            ret = new ListaSimplesDesordenada (this);
        }
        catch (Exception erro)
        {} // sei que this NUNCA é null e o contrutor de copia da erro quando seu parametro é null

        return ret;
    }

    // 1) Contar quantas vezes elemento dado existe na lista.
    public int quantasVezesTemOElemento(X i) throws Exception
    {
        if(i == null) throw new Exception("Erro, informação inexistente");

        No atual = this.primeiro;
        int contador = 0;

        while(atual!= null)
        {
            if(i.equals(atual.getInfo()))
            {
                contador++;
                atual = atual.getProx();
            }
           else atual = atual.getProx();
        }
    return contador;
    }

    // 2)Obter o tamanho da Lista
    public int obterTamanhoDaLista()
    {
        No atual = this.primeiro;
        int tamanho = 0;

        if(atual == null)
        {
            return tamanho;
        }

        if(atual == this.ultimo)
        {
            tamanho++;
            return tamanho;  // Apenas 1 elemento
        }

        while(atual != null)
        {
            if(atual.getInfo() != null)
            {
                tamanho++;
                atual = atual.getProx();
            }

        }

        return tamanho;
    }

    // 4) Inverter a lista sem criar outra
    public ListaSimplesDesordenada<X> inverteListaSemCriarOutra()
    {
        if (this.primeiro == null || this.primeiro == this.ultimo)
        {
            return this;
        }

        No anterior = null;
        No atual = this.primeiro;
        No proximo;

        while (atual != null)
        {
            proximo = atual.getProx();
            atual.setProx(anterior);
            anterior = atual;
            atual = proximo;
        }

        this.ultimo = this.primeiro;
        this.primeiro = anterior;

        return this;
    }

    // 3) Inverter a lista criando outra e retornando
    public ListaSimplesDesordenada<X> inverteLista(ListaSimplesDesordenada<X> modelo) throws Exception
    {
        if (modelo==null)
            throw new Exception ("Modelo ausente");

        ListaSimplesDesordenada<X> m = new ListaSimplesDesordenada<>();

        m.primeiro = new No(modelo.ultimo.getInfo());

        No atual = m.primeiro;
        No aux = this.primeiro;
        No inverso = modelo.primeiro.getProx();
        No primeiroInverso = modelo.primeiro;

        while(inverso != null)
        {
            atual.setProx(new No(inverso.getInfo()));
            atual = atual.getProx();
            inverso = inverso.getProx();

            if(inverso == this.ultimo)
            {

                atual.setProx(new No(aux.getInfo()));
                atual = atual.getProx();
                m.ultimo = atual;
                atual.setProx(primeiroInverso.getProx());
                System.out.println(m);
                break;
            }

        }
        return m;
    }

    // 5) Concatenar à lista 'this' uma lista que vem como parâmetro, criando novos nós.

    public ListaSimplesDesordenada<X> ConcatenaNaListaTHIS(ListaSimplesDesordenada<X> lista)
    {
        No atual = this.ultimo;
        No primeiroDaListaParam = lista.primeiro;

        No novoNo = new No(primeiroDaListaParam.getInfo());
        atual.setProx(novoNo);
        atual = atual.getProx();
        No proxDoParam = primeiroDaListaParam.getProx();

        while (proxDoParam!= null)
        {
            novoNo = new No(proxDoParam.getInfo());
            atual.setProx(novoNo);
            atual = atual.getProx();
            proxDoParam = proxDoParam.getProx();

            if(proxDoParam == null)
            {
                atual = novoNo;
                this.ultimo = atual;
            }
        }
        return this;
    }

    // 6) Retornar nova lista com o conteúdo do this concatenado ao conteúdo da lista parâmetro.
    public ListaSimplesDesordenada ConcatenaNaLista2(ListaSimplesDesordenada<X> lista,
                                                       ListaSimplesDesordenada<X> lista2)
    {
        No ultimoDoThis = this.ultimo;
        No primeiroDoParam = lista2.primeiro;
        No ultimoDoParam = lista2.ultimo;
       ultimoDoThis.setProx(primeiroDoParam);
       ultimoDoThis = ultimoDoThis.getProx();

       No proxDoParametro = lista2.primeiro.getProx();

       while(proxDoParametro != null)
       {
           ultimoDoThis.setProx(proxDoParametro);
           ultimoDoThis = ultimoDoThis.getProx();
           proxDoParametro = proxDoParametro.getProx();

           if(proxDoParametro == null) this.ultimo = ultimoDoParam;
       }

        return lista;
    }

    // 7) Retornar lista com os elementos comuns ao this e ao parâmetro

    public ListaSimplesDesordenada<X> elementosComuns(X i) throws Exception
    {
        ListaSimplesDesordenada<X> novaLista = new ListaSimplesDesordenada<>();

        No atual = this.primeiro;
        int contador = 0;

        while (atual != null)
        {
            if (atual.getInfo().equals(i))
            {
                contador++;

                if (contador == 1)
                {
                    novaLista.primeiro = new No(atual.getInfo());
                    novaLista.ultimo = novaLista.primeiro;
                }
                else
                {
                    No novoNo = new No(atual.getInfo());
                    novaLista.ultimo.setProx(novoNo);
                    novaLista.ultimo = novoNo;
                }
            }

            atual = atual.getProx();
        }

        return novaLista;
    }

    //OBS: Uma alternativa para este método seria:
    public ListaSimplesDesordenada<X> elementosComuns2(X i) throws Exception
    {
        ListaSimplesDesordenada<X> novaLista = new ListaSimplesDesordenada<>();

        No atual = this.primeiro;

        while (atual != null)
        {
            if (atual.getInfo().equals(i))
            {
                novaLista.guardeUmItemNoFinal(atual.getInfo());
            }
            atual = atual.getProx();
        }

        return novaLista;
    }
}


/*
F) Será criado na memória um caractere '/' e que terá um endereço armazenado em 'ret'.

G) Guarda-se o endereço de 'this.primeiro' no objeto 'atual'.Ambos agora possuem o mesmo endereço.

H) Pega o 'info' que está no objeto do atual.Busca pelo endereço de memória que atual aponta, depois observa e ve
que info aponta para outro endereço, que por sinal é onde está uma string armazenada.

Após este comando, há uma concatenação com o caractere '/'. Nisso, aquele espaço de memória que antes continha este
caractere é descartada(em dado momento pelo S.O.) e é armazenado o valor concatenado anteriormente EM OUTRO ESPAÇO
DE MEMÓRIA, COM UM NOVO ENDEREÇO.'ret' agora possui este novo endereço.
 */
