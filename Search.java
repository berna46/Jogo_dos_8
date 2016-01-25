import java.util.PriorityQueue;


public class Search extends List{
	
/**
 * verifica se foi encontrada a solução
 * @param current_table 	tabela em jogo
 * @param final_table 	tabela final/pretendida
 * @return boolean
 */
	public static boolean isSolution(int[][] current_table,int[][] final_table){
		for(int i=0;i<3;i++)
		    for(int j=0;j<3;j++)
		    	if(final_table[i][j]!=current_table[i][j])
		    		return false;
		return true;
	}
	
/**
 * pesquisa em profundidade	
 * @param table_list 	lista de tabelas
 * @param visited_tables	lista de tabelas visitadaso
 * @param final_table 	tabela final/pretendida
 * @return depth 	profundidade da solução
 */
	public static  Node depthFirstSearch(List table_list,List visited_tables,int [][] final_table){
		int[][] current_table=new int[3][3];
		int depth=0;
		String path=new String();
		List child_nodes=new List();
		while(!table_list.isEmpty()){
			path=table_list.getPath();
			depth=table_list.getDepth();
			current_table=table_list.remove();
			if(isSolution(current_table, final_table))
				return new Node(current_table,depth,path,null);
			visited_tables.addFirst(current_table,depth,path);
			child_nodes=playDFS(child_nodes,current_table,depth,path);
			//verifica se algum dos filhos já foi visitado
			while(!child_nodes.isEmpty()){
				int d=child_nodes.getDepth();
				String s=child_nodes.getPath();					
				int[][] child_table=child_nodes.remove();
				if(!visited_tables.contains(child_table))
					table_list.addFirst(child_table,d,s);
			}
		}
		throw new Error("Nao encontrou solucao");
	}
	
/**
 * pesquisa em largura	
 * @param table_list   	lista de tabelas
 * @param visited_tables	lista de tabelas visitadas
 * @param final_table   	tabela final/pretendida
 * @return depth   	profundidade da solução
 */
	public static  Node breadthFirstSearch(List table_list,List visited_tables,int [][] final_table) {
		int[][] current_table=new int[3][3];
		int depth=0;
		String path=new String();
		while(!table_list.isEmpty()){
			List child_nodes=new List();
			path=table_list.getPath();
			depth=table_list.getDepth();
			current_table=table_list.remove();
			if(isSolution(current_table, final_table))
				return new Node(current_table,depth,path,null);
			visited_tables.addFirst(current_table,depth,path);
			child_nodes=playBFS(child_nodes,current_table,depth,path);
			//verifica se algum dos filhos já foi visitado
			while(!child_nodes.isEmpty()){
				int d=child_nodes.getDepth();
				String s=child_nodes.getPath();					
				int[][] child_table=child_nodes.remove();
				if(!visited_tables.contains(child_table))
					table_list.addLast(child_table,d,s);
			}
		}
		throw new Error("Nao encontrou solucao");
	}
		
/**
 * 	
 * @param table_list	lista de tabelas
 * @param visited_tables	lista de tabelas visitadas
 * @param final_table	tabela final/pretendida
 */
	public static Node iterativeDepthFirstSearch(int[][] initial_table,int [][] final_table,int max_depth){
		List table_list=new List();
		table_list.addFirst(initial_table,0,"");
		List visited_tables=new List();
		int[][] current_table=new int[3][3];
		int depth=0;
		String path=new String();
		while(!table_list.isEmpty()){
			List child_nodes=new List();
			path=table_list.getPath();
			depth=table_list.getDepth();
			current_table=table_list.remove();
			if(isSolution(current_table, final_table))
				return new Node(current_table,depth,path,null);
			visited_tables.addFirst(current_table,depth,path);
			if(depth<max_depth){
				child_nodes=playDFS(child_nodes,current_table,depth,path);
				//verifica se algum dos filhos já foi visitado
				while(!child_nodes.isEmpty()){
					int d=child_nodes.getDepth();
					String s=child_nodes.getPath();					
					int[][] child_table=child_nodes.remove();
					if(!visited_tables.contains(child_table))
						table_list.addFirst(child_table,d,s);
				}
			}
		}
		return iterativeDepthFirstSearch(initial_table, final_table,max_depth+1);
	}
	
/**
 * pesquisa gulosa
 * @param table_list	lista de tabelas
 * @param visited_tables	lista de tabelas visitadas
 * @param final_table	tabela final/pretendida
 */
	public static Node greedySearch(PriorityQueue<Node> table_list,List visited_tables,int[][] final_table){
		int[][] current_table=new int[3][3];
		int depth=0;
		String path=new String();
		while(!table_list.isEmpty()){
			List child_nodes=new List();
			Node current_node=table_list.poll();
			path=current_node.getPath();
			depth=current_node.getDepth();
			current_table=current_node.getTable();
			if(isSolution(current_table, final_table))
				return new Node(current_table,depth,path,null);
			visited_tables.addFirst(current_table,depth,path);
			child_nodes=playBFS(child_nodes, current_table, depth, path);
			//verifica se algum dos filhos já foi visitado
			while(!child_nodes.isEmpty()){
				int d=child_nodes.getDepth();
				String s=child_nodes.getPath();					
				int[][] child_table=child_nodes.remove();
				if(!visited_tables.contains(child_table)){
					int c=getDistanceTo(child_table, final_table);
					Node child=new Node(child_table,d,s,c,null);
					table_list.add(child);
				}
			}
		}
		throw new Error("Nao encontrou solucao");
	}
	
/**
 * pesquisa A*
 * @param table_list	lista de tabelas
 * @param visited_tables	lista de tabelas visitadas
 * @param final_table	tabela final/pretendida
 * @return
*/
	public static Node aStarSearch(PriorityQueue<Node> table_list,int[][] final_table){
		int[][] current_table=new int[3][3];
		int depth=0;
		String path=new String();
		while(!table_list.isEmpty()){
			List child_nodes=new List();
			Node current_node=table_list.poll();
			path=current_node.getPath();
			depth=current_node.getDepth();
			current_table=current_node.getTable();
			/*
			System.out.println("custo: "+cost+"depth: "+depth);
			System.out.println("Tabela: ");
			for(int i=0;i<3;i++){
				for(int j=0;j<3;j++)
					System.out.print(current_table[i][j]);
				System.out.println();
			}
			*/
			if(isSolution(current_table, final_table))
				return new Node(current_table,depth,path,null);
			child_nodes=playBFS(child_nodes, current_table, depth, path);
			//verifica se algum dos filhos já foi visitado
			while(!child_nodes.isEmpty()){
				int d=child_nodes.getDepth();
				String s=child_nodes.getPath();					
				int[][] child_table=child_nodes.remove();
				int c=d+getDistanceTo(child_table, final_table);
				Node child=new Node(child_table,d,s,c,null);
				table_list.add(child);
			}
		}
		throw new Error("Nao encontrou solucao");
	}

	
/**
 * move célula vazia
 * @param child_nodes  	 lista de tabelas
 * @param curren_table  	 tabela em jogo
 */
 	public static List playDFS(List child_nodes,int[][] current_table,int depth,String path){
		int index_i=0,index_j=0;
		int[][] aux_table=new int[3][3];
		//obtem coordenadas da célula em branco
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(current_table[i][j]==0){
					index_i=i;
					index_j=j;
					break;
				}
		//move célula em branco para a esquerda
		if(index_j>0){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i][index_j-1];
			aux_table[index_i][index_j-1]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"E ");
		}	
		//move célula em branco para a direita
		if(index_j<2){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i][index_j+1];
			aux_table[index_i][index_j+1]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"D ");
		}
		//move célula em branco para baixo
		if(index_i<2){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i+1][index_j];
			aux_table[index_i+1][index_j]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"B ");
		}
		//move célula em branco para cima
		if(index_i>0){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i-1][index_j];
			aux_table[index_i-1][index_j]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"C ");
		}
		return child_nodes;
	}
	
/**
 * move célula vazia
 * @param child_nodes   	lista de tabelas
 * @param curren_table[][]	   tabela em jogo
 */
	public static List playBFS(List child_nodes,int[][] current_table,int depth,String path){
		int index_i=0,index_j=0;
		int[][] aux_table=new int[3][3];
		//obtem coordenadas da célula em branco
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				if(current_table[i][j]==0){
					index_i=i;
					index_j=j;
					break;
				} 
		//move célula em branco para cima
		if(index_i>0){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i-1][index_j];
			aux_table[index_i-1][index_j]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"C ");
		}
		//move célula em branco para baixo
		if(index_i<2){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i+1][index_j];
			aux_table[index_i+1][index_j]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"B ");
		}
		//move célula em branco para a direita
		if(index_j<2){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i][index_j+1];
			aux_table[index_i][index_j+1]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"D ");
		}
		//move célula em branco para a esquerda
		if(index_j>0){
			aux_table=setTable(aux_table,current_table);
			//move célula
			aux_table[index_i][index_j]=aux_table[index_i][index_j-1];
			aux_table[index_i][index_j-1]=0;
			//adiciona tabela à lista
			child_nodes.addLast(aux_table,depth+1,path+"E ");
		}
		return child_nodes;
	}
	
/**
 * define uma tabela igual a outra	
 * @param aux_table   tabela a definir
 * @param current_table   tabela base 
 * @return aux_table
 */
	public static int[][] setTable(int[][] aux_table,int[][] current_table){
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				aux_table[i][j]=current_table[i][j];
		return aux_table;
	}

/**
 * indica a distância entre duas tabelas(heurística)	 
 * @param current_table		tabela base
 * @param final_table		tabela final/pretendida
 * @return total_distance	distância total entre as tabelas
 */
	
	 public static int getDistanceTo(int[][] current_table,int[][] final_table){
		 int[]distances=new int[9];
		 for(int i=0;i<3;i++)
			 for(int j=0;j<3;j++){
				 distances[current_table[i][j]]=Math.abs(distances[current_table[i][j]]-((i+1)+(j+1)));
				 distances[final_table[i][j]]=Math.abs(distances[final_table[i][j]]-((i+1)+(j+1)));
			 }
		 int total_distance=0;
		 for(int i=1;i<9;i++)
			 total_distance+=distances[i];
		return total_distance;		 
	 }
	

}
	
