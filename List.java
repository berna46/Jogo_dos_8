
public class List extends Node{
	private Node first,last;
	private int size;
	
	List(){
		first=last=null;
		size=0;
	}
	
	List(List l){
		first=l.getFirst();
	}
/**
 * indica tamanho da lista
 * @return size	 tamanho da lista	
 */
	public int getSize(){
		return size;
	}
	
/**
 * verifica se a lista está vazia
 * @return boolean
 */ 	
	public boolean isEmpty(){
		if(size==0)
			return true;
		return false;
	}

/**
* devolve primeiro nó da lista	
*@return first	 primeiro nó da lista 
*/
	public Node getFirst() throws Error {	
		if(!isEmpty())
			return first;
		else
			throw new Error("Lista vazia!");
	}

/**
 * devolve último nó da lista	
 *@return last	 último nó da lista 
 */
	public Node getLast() throws Error{
		if(!isEmpty())
			return last;
		else
			throw new Error("Lista vazia!");
	}

/**
 * devolve a profundidade do elemento;
 * @return depth	 profundidade 
 */
	public int getDepth(){
		return first.depth;
	}

/**
 * devolve caminho
 * @return path
 */
	public String getPath(){
		return first.path;
	}
/**
 * devolve estado
 * @return state
 */
	public int getCost(){
		return cost;
	}
	
/**
 * adiciona um elemento no final da lista
 * @param t		tabela
 * @param d		profundidade
 * @param s		caminho
 */
	public void addLast(int t[][],int d,String s){
		Node aux=new Node(t,d,s,null);
		if(isEmpty())
			first=last=aux;
		else{
			if(getSize()==1){
				last=aux;
				first.next=aux;
			}
			else
				last.next=aux;
				last=aux;
		}
		size++;
	}
/**
 * adiciona um nó ao início lista
 * @param t		tabela
 * @param d		profundidade
 * @param s		caminho
 */
	public void addFirst(int t[][],int d, String s){
		Node aux=new Node(t,d,s,null);
		if(isEmpty())
			first=last=aux;
		else{
			if(getSize()==1){
				aux.next=last;
				first=aux;
			}
			else{
				aux.next=first;
				first=aux;
			}
				
		}
		size++;
	}
	
/**
 * remove o primeiro nó da lista
 *@return 	primeiro nó da lista	 
 */
	public int[][] remove() throws Error{
		if(!isEmpty()){
			Node aux=getFirst();
			first=first.next;
			size--;
			return aux.table;
		}
		else
			throw new Error("Lista vazia!");
	}

/**
 * verifica a exestência de um elemento(a[][])
 * @param t	 tabela
 * @return boolean
 */
	public boolean contains(int t[][]){
		Node pointer=this.first;
		while(pointer!=null){
			int dirty=0;
			for(int i=0;i<3;i++)
				for(int j=0;j<3;j++)
					if(t[i][j]!=pointer.table[i][j]){
						dirty=1;
						break;
					}
			if(dirty==0)
				return true;
			pointer=pointer.next;
		}
	return false;
	}
	
	/**
	 * imprime a lista
	 */
	public void printList(){
		Node pointer=first;
		while(pointer!=null){
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++)
					System.out.print(pointer.table[i][j]+" ");
				System.out.println();
			}
			System.out.println();
			pointer=pointer.next;
		}
	}
	
	/**
	 * adiciona um nó ao inicio da lista
	 * @param t 	tabela
	 * @param d		profundidade
	 * @param s		caminho
	 * @param c		custo
	 */
	public void addFirst(int t[][],int d, String s,int c){
		Node aux=new Node(t,d,s,c,null);
		if(isEmpty())
			first=last=aux;
		else{
			if(getSize()==1){
				aux.next=last;
				first=aux;
			}
			else{
				aux.next=first;
				first=aux;
			}
				
		}
		size++;
	}
	
/**
 * 	adiciona um nó ao final da lista
 * @param t		tabela
 * @param d		profundidade
 * @param s		caminho
 * @param c		custo
 */
	public void addLast(int t[][],int d,String s,int c){
		Node aux=new Node(t,d,s,c,null);
		if(isEmpty())
			first=last=aux;
		else{
			if(getSize()==1){
				last=aux;
				first.next=aux;
			}
			else
				last.next=aux;
				last=aux;
		}
		size++;
	}
}
