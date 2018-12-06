package apps;

import structures.*;
import java.util.ArrayList;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		/* COMPLETE THIS METHOD */
		
		PartialTreeList L = new PartialTreeList();
		Vertex [] v = graph.vertices;
		PartialTree T;
		boolean [] visited = new boolean[v.length];
		for(int i =0; i<v.length;i++){
			T = new PartialTree(v[i]);
			
			v[i].parent = T.getRoot();
			MinHeap<PartialTree.Arc> P = T.getArcs();
			for(Vertex.Neighbor n = v[i].neighbors; n!=null; n = n.next){
				PartialTree.Arc arc = new PartialTree.Arc(v[i], n.vertex, n.weight);
				P.insert(arc);
			}
			if(visited[i]==false){
				L.append(T);
				visited[i]=true;
			}
		
		}
		return L;
	}

	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		
		/* COMPLETE THIS METHOD */
		ArrayList<PartialTree.Arc> list1 = new ArrayList<PartialTree.Arc>();
		PartialTreeList temp = ptlist;
		PartialTree.Arc arc;
		
		while(temp.size()>1){
			PartialTree PQX = temp.remove();
			
			while(PQX.getArcs().size()>0){
				arc = PQX.getArcs().deleteMin();
				if(arc.v2.getRoot()==PQX.getRoot()){
					continue;
				}else{
					PartialTree PQY = temp.removeTreeContaining(arc.v2.getRoot());
					PQX.merge(PQY);
					temp.append(PQX);
					list1.add(arc);
					break;
				}
			}
		}
		return list1;
	}
}
