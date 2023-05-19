package one;
import java.util.ArrayList;
import java.util.Collections;


class Edge implements Comparable<Edge>
{
	int vertex1;
	int vertex2;
	int cost;
	public Edge(int v1 , int v2 , int cost)
	{
		vertex1 = v1;
		vertex2 = v2;
		this.cost = cost;
	}
	@Override
	public int compareTo(Edge compared) {	
		return this.cost - compared.cost;
	}

}
class subset
{
	int parent, rank;
	public subset(int parent, int rank)
	{
		this.parent = parent;
		this.rank = rank;
	}
};
public class ElectricNetwork {
	static int find(subset subsets[], int i)
	{
		if (subsets[i].parent != i)
			subsets[i].parent= find(subsets, subsets[i].parent);
		return subsets[i].parent;
	}
	static void Union(subset subsets[], int xroot, int yroot)
	{
//			if (subsets[xroot].rank < subsets[yroot].rank)
//				subsets[xroot].parent = yroot;
//			else if (subsets[xroot].rank > subsets[yroot].rank)
//				subsets[yroot].parent = xroot;
//			else {
//				subsets[yroot].parent = xroot;
//				subsets[xroot].rank++;
//			}
	    if (subsets[xroot].rank < subsets[yroot].rank)
	    {
			subsets[xroot].parent = yroot;
			subsets[yroot].rank++;
	    }
		else {
			subsets[yroot].parent = xroot;
			subsets[xroot].rank++;
		}
		
	}
	static String mst(ArrayList<Edge> edges , int V)
	{
		ArrayList<Edge> result = new ArrayList<Edge>();
		Collections.sort(edges);
		subset subsets[] = new subset[V+1];
		for (int v = 1; v < V+1; ++v)
			subsets[v] = new subset(v,0);
		int i = 0; 
		int e = 0;
		while (e < V - 1)
		{
			Edge next_edge = edges.get(i++);
			int xroot = find(subsets, next_edge.vertex1);
			int yroot = find(subsets, next_edge.vertex2);
			if (xroot != yroot) {
				result.add(next_edge);
				e++;
				Union(subsets, xroot, yroot);
			}
			// Else discard the next_edge
		}

		int minimumCost = 0;
		String returned = "";
		for (i = 0; i < e; ++i)
		{
			Edge E = result.get(i);
			returned += "T" + E.vertex1 + "," + "T" + E.vertex2 + "," + E.cost + ";";
			minimumCost += result.get(i).cost;
		}
		returned += minimumCost;
		System.out.println(returned);
		return returned;
	}
	public static String minimumConnections (String network)
	{
		String [] split = network.split("[,;]+");
		ArrayList<Edge> edges = new ArrayList<Edge>();
		ArrayList<Integer> vertices = new ArrayList<Integer>();
		for (int i = 0 ; i < split.length-3 ; i+=3)
		{
			int v1 = Integer.parseInt(split[i].substring(1)); 
			vertices.add(v1);
			int v2 = Integer.parseInt(split[i+1].substring(1)); 
			vertices.add(v2);
			int cost = Integer.parseInt(split[i+2]); 
			Edge newE = new Edge(v1,v2,cost);
			edges.add(newE);
		}
		Collections.sort(vertices);
		int V = vertices.get(vertices.size()-1);
		return mst(edges,V);
	}
//		public static void main(String [] args)
//		{
//			String network = "T1,T2,4;T1,T9,8;T2,T3,8;T2,T9,11;T3,T4,7;T3,T6,4;T3,T8,2;T4,T5,9;T4,T6,14;T5,T6,10;T6,T7,2;T7,T8,6;T7,T9,1;T8,T9,7;";
//			minimumConnections(network);
//		}
}
