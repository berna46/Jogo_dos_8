import java.util.*;

public class Assignment1 extends Search{

	public static void main(String[] args){
		Scanner in=new Scanner(System.in);
		int final_table[][]=new int[3][3];
		System.out.println("JOGO DOS 8");
		System.out.println();
		System.out.println("Escreva a configuracao das tabelas");
		System.out.println("-----------------------------------");
		System.out.println("Tabela inicial (3x3) :");
		int initial_table[][]=new int[3][3];
		//lê tabela inicial
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				initial_table[i][j]=in.nextInt();
		System.out.println("-----------------------------------");
		//lê tabela final
		System.out.println("Tabela final (3x3) :");
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				final_table[i][j]=in.nextInt();
		System.out.println("-----------------------------------");
		if(hasSolution(initial_table, final_table)){
			//lista de tabelas visitadas
			List visited_tables=new List();
			System.out.println("Selecione o tipo de pesquisa");
			System.out.println();
			System.out.println("-Profundidade: 1\n-Largura: 2\n-Profundidade Iterativa: 3\n-Gulosa: 4\n-A*: 5");
			System.out.println("-----------------------------------");
			System.out.print("Pesquisa: ");
			int search=in.nextInt();
			Node n=new Node();
			double start=new Date().getTime();
			if(search==1){
				List table_list=new List();
				//adiciona tabela à lista
				table_list.addFirst(initial_table,0,"");
				System.out.println("Selecionou: Pesquisa em Profundida");
				n=depthFirstSearch(table_list, visited_tables, final_table);
			}
			else if(search==2){
				List table_list=new List();
				//adiciona tabela à lista
				table_list.addFirst(initial_table,0,"");
				System.out.println("Selecionou: Pesquisa em Largura");
				n=breadthFirstSearch(table_list, visited_tables, final_table);
		}
			else if(search==3){
				List table_list=new List();
				//adiciona tabela à lista
				table_list.addFirst(initial_table,0,"");
				System.out.println("Selecionou: Pesquisa em Profundidade Iterativa");
				n=iterativeDepthFirstSearch(initial_table, final_table, 0);
			}
			else if(search==4){
				PriorityQueue<Node> table_list=new PriorityQueue<Node>();
				//adiciona tabela à lista
				Node initial_node=new Node(initial_table,0,"",getDistanceTo(initial_table, final_table),null);	
				table_list.add(initial_node);
				System.out.println("Selecionou: Pesquisa Gulosa");
				n=greedySearch(table_list, visited_tables, final_table);
			}
			else if(search==5){
				PriorityQueue<Node> table_list=new PriorityQueue<Node>();
				//adiciona tabela à lista
				Node initial_node=new Node(initial_table,0,"",getDistanceTo(initial_table, final_table),null);	
				table_list.add(initial_node);
				System.out.println("Selecionou: Pesquisa A*");
				n=aStarSearch(table_list, final_table);
			}
			else
				System.out.println("Operação invalida!\nO número que selecionou não está associado a nenhuma pesquisa");
			if(search==1 || search==2 || search==3 || search==4 || search==5){
				System.out.println("-----------------------------------");
				double end=new Date().getTime();
				System.out.println("Profundidade da solução: "+n.getDepth());
				System.out.println("Jogadas: "+n.getPath());
				System.out.println("Tempo de execução: "+((end-start)/1000)+"s ("+(end-start)+"ms)");
				double used_memory=Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
				System.out.printf("Memória utilizada: %dkb (%.3fMB)\n",((int)used_memory/(1024)),(used_memory/(1024*1024)));
			}
		}
		else
			System.out.println("Nao tem solucao!");
	}
			
/**
 * verifica se o problema tem solução
 * @param initial_table	 tabela inicial
 * @param final_table	 tabela final
 * @return boolean	
 */
	static public boolean hasSolution(int[][] initial_table,int[][] final_table){
		int init_count=0,final_count=0;
		for(int i=0;i<3;i++)
			for(int j=0;j<3;j++)
				for(int k=i;k<3;k++)
					for(int t=0;t<3;t++){
						if(initial_table[i][j]>initial_table[k][t] && (i<k || j<t) && initial_table[k][t]!=0)
							init_count++;
						if(final_table[i][j]>final_table[k][t] && (i<k || j<t) && final_table[k][t]!=0)
							final_count++;
					}
		//verifica se as duas tabelas têm a mesma paridade
		if((final_count%2==0 && init_count%2!=0) || (init_count%2==0 && final_count%2!=0))
			return false;
		return true;
	}
}