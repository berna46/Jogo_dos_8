public class Node implements Comparable<Node> {
	Node next;
	int table[][],depth,cost;
	String path;
	Node(){	
		table=new int[3][3];
		cost=depth=0;
		next=null;
		path=null;
	}
	Node(int t[][],Node n){
		table=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				table[i][j]=t[i][j];	
		cost=depth=0;
		next=n;
		path=new String();
	}
	Node(int t[][],int depth,String p,Node n){
		table=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				table[i][j]=t[i][j];	
		this.depth=depth;
		next=n;
		path=p;
		cost=0;
	}
	Node(Node n){
		table=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				table[i][j]=n.table[i][j];	
		depth=n.depth;
		next=n.next;
		path=n.path;
		cost=n.cost;
		}
	Node(int t[][],int depth,String p,int c,Node n){
		table=new int[3][3];
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				table[i][j]=t[i][j];	
		this.depth=depth;
		next=n;
		path=p;
		cost=c;
	}
	
	/**
	 * devolve a profundidade do elemento;
	 * @return depth	 profundidade 
	 */
		public int getDepth(){
			return depth;
		}

	/**
	 * devolve caminho
	 * @return path
	 */
		public String getPath(){
			return path;
		}
		
	/**
	 * devolve tabela
	 * @return table
	 */
		public int[][] getTable(){
			return table;
		}
		
	/**
	 * devolve o estado	
	 * @return cost
	 */
		public int getCost(){
			return cost;
		}
	/**
	 * comparador a ser implementado na fila de prioridade			
	 */
		public int compareTo(Node n){
			if(getCost()==n.getCost())
				return 0;
			else if(getCost()>n.getCost())
				return 1;
			return -1;
		}
	
	}