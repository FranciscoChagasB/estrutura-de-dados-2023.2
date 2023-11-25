package estrutura;

// Nó da árvore AVL
class NoAVL {
    protected int altura;       // Altura do nó
    protected int chave;        // Valor do nó
    protected NoAVL esquerda, direita;  // Filhos à esquerda e à direita

    // Construtor para criar um nó com um valor e, opcionalmente, filhos
    public NoAVL(int oElemento) {
        this(oElemento, null, null);
    }

    public NoAVL(int oElemento, NoAVL lt, NoAVL rt) {
        chave = oElemento;
        esquerda = lt;
        direita = rt;
        altura = 0;
    }
}

// Classe principal da Árvore AVL
public class ArvoreAVL {
    private NoAVL raiz = null;  // Raiz da árvore AVL

    // Construtor para criar uma árvore AVL vazia
    public ArvoreAVL() {
        raiz = null;
    }

    // Limpa a árvore, tornando-a vazia
    public void limpar() {
        raiz = null;
    }

    // Verifica se a árvore está vazia
    public boolean estaVazia() {
        return raiz == null;
    }

    // Obtém a raiz da árvore
    public NoAVL obterNoRaiz() {
        return raiz;
    }

    // Método privado para calcular a altura de um nó
    private static int altura(NoAVL t) {
        return t == null ? -1 : t.altura;
    }

    // Método privado para calcular o máximo entre dois valores
    private static int max(int esquerda, int direita) {
        return esquerda > direita ? esquerda : direita;
    }

    // Método privado para obter o fator de balanceamento de um nó
    private int obterFator(NoAVL t) {
        return altura(t.esquerda) - altura(t.direita);
    }

    // Método público para inserir um elemento na árvore AVL
    public boolean inserir(int x) {
        raiz = inserir(x, raiz);
        return true;
    }

    // Método privado para inserir um elemento recursivamente
    private NoAVL inserir(int x, NoAVL t) {
        if (t == null)
            t = new NoAVL(x, null, null);
        else if (x < t.chave)
            t.esquerda = inserir(x, t.esquerda);
        else if (x > t.chave)
            t.direita = inserir(x, t.direita);
        t = balancear(t);
        return t;
    }

    // Método para balancear a árvore após a inserção
    public NoAVL balancear(NoAVL t) {
        if (obterFator(t) == 2) {
            if (obterFator(t.esquerda) > 0)
                t = fazerRotacaoDireita(t);
            else
                t = fazerRotacaoDuplaDireita(t);
        } else if (obterFator(t) == -2) {
            if (obterFator(t.direita) < 0)
                t = fazerRotacaoEsquerda(t);
            else
                t = fazerRotacaoDuplaEsquerda(t);
        }
        t.altura = max(altura(t.esquerda), altura(t.direita)) + 1;
        return t;
    }

    // Métodos privados para realizar rotações simples e duplas
    private static NoAVL fazerRotacaoDireita(NoAVL k2) {
        NoAVL k1 = k2.esquerda;
        k2.esquerda = k1.direita;
        k1.direita = k2;
        k2.altura = max(altura(k2.esquerda), altura(k2.direita)) + 1;
        k1.altura = max(altura(k1.esquerda), k2.altura) + 1;
        return k1;
    }

    private static NoAVL fazerRotacaoEsquerda(NoAVL k1) {
        NoAVL k2 = k1.direita;
        k1.direita = k2.esquerda;
        k2.esquerda = k1;
        k1.altura = max(altura(k1.esquerda), altura(k1.direita)) + 1;
        k2.altura = max(altura(k2.direita), k1.altura) + 1;
        return k2;
    }

    private static NoAVL fazerRotacaoDuplaDireita(NoAVL k3) {
        k3.esquerda = fazerRotacaoEsquerda(k3.esquerda);
        return fazerRotacaoDireita(k3);
    }

    private static NoAVL fazerRotacaoDuplaEsquerda(NoAVL k1) {
        k1.direita = fazerRotacaoDireita(k1.direita);
        return fazerRotacaoEsquerda(k1);
    }

    // Método público para procurar um elemento na árvore
    public NoAVL procurar(int el) {
        return procurar(raiz, el);
    }

    // Método privado para procurar um elemento recursivamente
    protected NoAVL procurar(NoAVL p, int el) {
        while (p != null) {
            if (el == p.chave)
                return p;
            else if (el < p.chave)
                p = p.esquerda;
            else
                p = p.direita;
        }
        return null;
    }

    // Método para realizar o percurso em ordem
    public void emOrdem() {
        emOrdem(raiz);
    }

    // Método privado para realizar o percurso em ordem recursivamente
    protected void emOrdem(NoAVL p) {
        if (p != null) {
            emOrdem(p.esquerda);
            System.out.print(p.chave + " - ");
            emOrdem(p.direita);
        }
    }

    // Métodos para realizar os percursos pré-ordem e pós-ordem
    public void preOrdem() {
        preOrdem(raiz);
    }

    protected void preOrdem(NoAVL p) {
        if (p != null) {
            System.out.print(p.chave + " ");
            preOrdem(p.esquerda);
            preOrdem(p.direita);
        }
    }

    public void posOrdem() {
        posOrdem(raiz);
    }

    protected void posOrdem(NoAVL p) {
        if (p != null) {
            posOrdem(p.esquerda);
            posOrdem(p.direita);
            System.out.print(p.chave + " ");
        }
    }

    // Método para procurar o pai de um elemento na árvore
    protected NoAVL procurarPai(int el) {
        NoAVL p = raiz;
        NoAVL anterior = null;
        while (p != null && !(p.chave == el)) {
            anterior = p;
            if (p.chave < el)
                p = p.direita;
            else
                p = p.esquerda;
        }
        if (p != null && p.chave == el)
            return anterior;
        return null;
    }

    // Método para exibir a árvore de forma hierárquica
    public void exibirArvore() {
        if (estaVazia()) {
            System.out.println("Árvore vazia!");
            return;
        }
        String separador = String.valueOf("  |__");
        System.out.println(this.raiz.chave + "(" + raiz.altura + ")");
        exibirSubArvore(raiz.esquerda, separador);
        exibirSubArvore(raiz.direita, separador);
    }

    private void exibirSubArvore(NoAVL no, String separador) {
        if (no != null) {
            NoAVL pai = this.procurarPai(no.chave);
            if (no.equals(pai.esquerda) == true) {
                System.out.println(separador + no.chave + "(" + no.altura + ")" + " (ESQ)");
            } else {
                System.out.println(separador + no.chave + "(" + no.altura + ")" + " (DIR)");
            }
            exibirSubArvore(no.esquerda, "     " + separador);
            exibirSubArvore(no.direita, "     " + separador);
        }
    }

    // Método principal para testar a árvore AVL
    public static void main(String args[]) {
        ArvoreAVL arvore = new ArvoreAVL();
        arvore.inserir(1);
        arvore.inserir(2);
        arvore.exibirArvore();
        arvore.inserir(3);
        arvore.inserir(4);
        arvore.exibirArvore();
        arvore.inserir(5);
        arvore.inserir(6);
        arvore.inserir(7);
        arvore.inserir(8);
        arvore.inserir(9);
        arvore.exibirArvore();
    }
}
