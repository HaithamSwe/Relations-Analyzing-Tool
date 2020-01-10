package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class Functionalities {
    protected static String setMembers[];
    protected static int[][] matrix;
    protected static String hasseDiagram;
    protected static ArrayList<String> maximals, minimals;
    protected static String greatest, least;

    // A relation R is reflexive if the matrix diagonal elements are 1
    public static boolean isReflexive(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i][i] != 1)
                return false;
        return true;
    }

    // A relation R is irreflexive if the matrix diagonal elements are 0
    public static boolean isIrreflexive(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            if (matrix[i][i] != 0)
                return false;
        return true;
    }

    public boolean isNotReflexiveNotIrreflexive(int[][] matrix) {
        // return !isReflexive(matrix)&&!isIrreflexive(matrix); by DeMorgan’s Theory :
        return !(isReflexive(matrix) || isIrreflexive(matrix));
    }

    // A relation R on a set A is called symmetric if (b, a) ∈ R whenever (a, b)
    // ∈ R, for all a, b ∈ A.
    public static boolean isSymmetric(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[0].length; j++)
                if (matrix[i][j] != matrix[j][i])
                    return false;
        return true;
    }

    // A relation R on a set A such that for all a, b ∈ A, if (a, b) ∈ R and (b,
    // a) ∈ R, then a = b is called antisymmetric.
    public static boolean isAntisymmetric(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (i != j && matrix[i][j] != 0 && 0 != matrix[j][i])
                    return false;
            }
        }
        return true;
    }

    // A relation R on a set A is called symmetric if (b, a) ∈/ R whenever (a, b)
    // ∈ R, for all a, b ∈ A is Asymmetric
    public static boolean isAsymmetric(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] == matrix[j][i])
                    return false;
        return true;
    }

//	// A relation R on a set A is called transitive if (a, b) ∈ R and (b, c) ∈ R
//	// then (a, c) ∈ R
//	// in slide #12 : (In other words, compute the relation composition with itself.
//	// If the resulting relation is a subset of the original relation, then the
//	// relation is transitive).

    public static boolean isTransitive(int[][] matrix) {
//		return isSubset(matrix, selfComposition(matrix));
        return checkTransitivity();
    }

    private static int[][] selfComposition(int[][] matrix) {
        return composition(matrix);
    }

    private static int[][] composition(int[][] matrix) {
        int[][] result = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++) {
                int sum = 0;
                for (int k = 0; k < matrix.length; k++)
                    sum += matrix[i][k] * matrix[k][j];
                result[i][j] = sum;
            }

        return result;
    }

    // is b subset of a ?
    private static boolean isSubset(int[][] a, int[][] b) {

        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length; j++)
                if (a[i][j] == 0 && b[i][j] == 1)
                    return false;
        return true;
    }

    private static boolean checkTransitivity() {
        boolean isTransitive = true;

        int[][] squared = squaredMatrix();

        // if m[i][j] == 0 go check s[i][j]
        // if s[i][j] == 1 -> not trans.
        for (int i = 0; i < squared.length; i++)
            for (int j = 0; j < squared.length; j++)
                if (matrix[i][j] == 0 && squared[i][j] == 1)
                    isTransitive = false;
        return isTransitive;
    }

    // Helper for checkTransitivity();
    private static int[][] squaredMatrix() {
        int[][] result = new int[matrix.length][matrix.length];

        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix.length; j++) {
                int sum = 0;
                for (int k = 0; k < matrix.length; k++)
                    sum += matrix[i][k] * matrix[k][j];
                result[i][j] = sum;
            }

        return result;
    }


    public static boolean isEquivalence(int[][] matrix) {
        return isReflexive(matrix) && isSymmetric(matrix) && isTransitive(matrix);
    }

    public static boolean isPartialOrdering(int[][] matrix) {
        return isReflexive(matrix) && isAntisymmetric(matrix) && isTransitive(matrix);
    }


    public static String getEquivalence(int[][] matrix, String setMembers[]) {
        String equivalenceClasses;
        int[][] copy = new int[matrix.length][matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                copy[i][j] = matrix[i][j];
            }
        }

        equivalenceClasses = "";
        int count = 1;
        for (int i = 0; i < matrix.length; i++) {
            if (copy[i][0] == -1)
                continue; // Skip already compared row.
            if (i == matrix.length - 1) {
                equivalenceClasses += "EQ" + count + ": " + setMembers[i] + ", ";
                break; // Handles last row/class.
            }
            equivalenceClasses += "EQ" + count + ": " + setMembers[i] + ", ";

            for (int j = i + 1; j < setMembers.length; j++) {
                boolean equal = true; // checks row similarity.
                for (int k = 0; k < setMembers.length; k++) {
                    if (copy[i][k] != copy[j][k]) {
                        equal = false;
                        break;
                    }
                }
                if (equal) {
                    equivalenceClasses += setMembers[j] + ", ";
                    copy[j][0] = -1; // marks the row as compared.
                }
            }
            count++;
            equivalenceClasses += "\n";
        }
        return equivalenceClasses;

    }

    // All logic explained in HasseDiagram methods.
    protected static void generateHasseDiagram() {
        HasseDiagram hasseDiagram = new HasseDiagram();
        Functionalities.hasseDiagram = hasseDiagram.representableToString();

        maximals = hasseDiagram.getMaximals();
        minimals = hasseDiagram.getMinimals();
        greatest = hasseDiagram.getGreatest();
        least = hasseDiagram.getLest();

    }

    private static class HasseDiagram {

        HasseNode[] diagram;

        public HasseDiagram() {
            generate();
        }

        /*
         * Step 1: generates basic Hasse diagram without loops, but with transitive
         * edges. achieved by storing to what does the element point to moon --> sun
         * (sun stored as next) and what points to the element sun <-- moon (moon stored
         * as prev) diagram represented in a 1D array* containing all elements as
         * HasseNode/s. *: underlying data structure is an array. ` Step 2: removes
         * transitive edges using <removeTransitiveEdges()>.
         */
        private void generate() {
            this.diagram = new HasseNode[setMembers.length];
            for (int i = 0; i < setMembers.length; i++) { // initial step, stores plain nodes.
                this.diagram[i] = new HasseNode(setMembers[i]);
            }

            for (int i = 0; i < setMembers.length; i++) { // creates the references without loops.
                for (int j = 0; j < setMembers.length; j++) {
                    if ((i != j) && (matrix[i][j] == 1)) {
                        diagram[i].nexts.add(diagram[j]);
                        diagram[j].prevs.add(diagram[i]);
                    }
                }
            }
            removeTransitiveEdges();

        }

        /*
         * To remove the transitive edges, this does the following: It computes the map
         * with costs and paths using <calcPathsCosts()> for the current node. It stores
         * the costs in an array, finds the minimum cost (hence, the desired edge), gets
         * the node related to this cost and removes the reference from the current node
         * to it, from both the map and diagram (specifically from <nexts>). And by
         * that, the transitive edge is removed, it repeats the process for all nodes,
         * and until there is a single path in the map for every node.
         */
        private void removeTransitiveEdges() {
            for (int i = 0; i < setMembers.length; i++) {
                if (!diagram[i].nexts.isEmpty()) {
                    HashMap<Integer, HasseNode> map = calcPathsCosts(i);

                    while (map.size() > 1) {
                        int min = Integer.MAX_VALUE;

                        Object[] keys = map.keySet().toArray();
                        for (int k = 0; k < keys.length; k++) {
                            if ((Integer) keys[k] < min)
                                min = (Integer) keys[k];
                        }

                        diagram[i].nexts.remove(map.get(min));
                        map.get(min).prevs.remove(diagram[i]);
                        map.remove(min);
                    }

                }
            }
        }

        /*
         * Inorder to help at finding transitive edges this does the following: Take the
         * current node, traverse from it to the last possible node using the <nexts>
         * list count how many steps it took to reach <cost>. store the node that began
         * the path and the cost in a map. do the same for other nodes that the current
         * node can go to. the map would look something like this: map: {2=earth, 1=sun}
         * <cost/int, path/HasseNode>
         */
        private HashMap<Integer, HasseNode> calcPathsCosts(int current) {
            HashMap<Integer, HasseNode> map = new HashMap<>();
            for (int j = 0; j < diagram[current].nexts.size(); j++) {
                int cost = 1;

                for (HasseNode tmp = diagram[current].nexts.get(j); !tmp.nexts.isEmpty(); tmp = tmp.nexts
                        .get(0), cost++)
                    ;

                map.put(cost, diagram[current].nexts.get(j));
            }
            return map;
        }

        /*
         * First gets the representable map using <getRepresentable()>, then: Stores
         * keys <HasseNode/s> and values <ArrayList<Integer `label`> in separate arrays.
         * Since the insertion order is maintained, the two arrays would still be
         * consistent. builds up the string in this style: P#: (- or P#..., {element}) P
         * -> label.
         */
        public String representableToString() {
            LinkedHashMap<HasseNode, ArrayList<Integer>> map = getRepresentable();
            Object[] keys = map.keySet().toArray();
            Object[] vals = map.values().toArray();

            String str = "";
            for (int i = 0; i < setMembers.length; i++) {
                str += "P" + i + ": ";
                ArrayList<String> list = (ArrayList<String>) vals[i];
                str += "(";
                if (list.size() == 1) {
                    str += "-, {";
                } else {
                    for (int j = 1; j < list.size(); j++) {
                        str += "P" + String.valueOf(list.get(j)) + " ";
                    }
                    str += ", {";
                }
                str += ((HasseNode) keys[i]).data + "})\n";
            }
            return str;
        }

        /*
         * To be able to represent the diagram in the desired style, this does the
         * following: The map stores a HasseNode and an ArrayList of preceding elements,
         * the first item in the ArrayList is the label of the element itself, followed
         * by preceding labels. This is achieved by first getting the nodes with no
         * preceding elements (empty prev) then adding the rest from lower size of prev
         * to higher. in case of nodes having same <prev> size and one coming before the
         * other, it is added to <skipped> for later processing if its prev is not in
         * the map. produces a map that looks like: ________________ | HasseNode | [its
         * label, preceding labels...] |
         * ------------------------------------------------
         */
        private LinkedHashMap<HasseNode, ArrayList<Integer>> getRepresentable() {
            LinkedHashMap<HasseNode, ArrayList<Integer>> map = new LinkedHashMap<>();
            int label = 0;
            for (int i = 0; i < setMembers.length; i++) {
                if (diagram[i].prevs.isEmpty()) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(label);
                    map.put(diagram[i], list);
                    label++;
                }
            }
            ArrayList<HasseNode> skipped = new ArrayList<>();
            for (int i = 0, lastPrevSize = 0; i < setMembers.length; i++) {
                int minPrevSize = Integer.MAX_VALUE;

                HasseNode node = null;
                for (int z = 0; z < setMembers.length; z++) {
                    if (diagram[z].prevs.size() >= lastPrevSize && diagram[z].prevs.size() < minPrevSize
                            && !map.containsKey(diagram[z])) {
                        if (diagram[z].prevs.size() > 0) {
                            minPrevSize = diagram[z].prevs.size();
                            node = diagram[z];
                        }
                    }
                }
                if (node != null) {
                    ArrayList<Integer> list = new ArrayList<>();
                    list.add(label);
                    for (int k = 0; k < node.prevs.size(); k++) {
                        try {
                            list.add(map.get(node.prevs.get(k)).get(0));
                            HasseNode n0 = node.prevs.get(k);
                            ArrayList<Integer> arr = map.get(n0);
                            list.add(arr.get(0));
                        } catch (NullPointerException e) {
                            skipped.add(node);
                            break;
                        }
                    }
                    map.put(node, list);
                    lastPrevSize = node.prevs.size();
                    label++;
                }

            }
            for (int j = 0; j < skipped.size(); j++) {
                ArrayList<Integer> list = map.get(skipped.get(j));
                for (int k = 0; k < skipped.get(j).prevs.size(); k++) {
                    list.add(map.get(skipped.get(j).prevs.get(k)).get(0));

                }

                map.put(skipped.get(j), list);
            }

            return map;
        }

        /*
         * Maximal: nodes with size 0 of <nexts> Minimal: nodes with size 0 of <prevs>
         * Greatest: if 1 maximal -> itself. else -> none Lest: if 1 minimal -> itself.
         * else -> none
         *
         * These just loop an comperes the size of <prevs, nexts>.
         */
        private ArrayList<String> getMaximals() {
            ArrayList<String> maximals = new ArrayList<>();

            for (int i = 0; i < setMembers.length; i++) {
                if (diagram[i].nexts.size() == 0)
                    maximals.add(diagram[i].data);
            }
            return maximals;
        }

        private ArrayList<String> getMinimals() {
            ArrayList<String> minimals = new ArrayList<>();

            for (int i = 0; i < setMembers.length; i++) {
                if (diagram[i].prevs.size() == 0)
                    minimals.add(diagram[i].data);
            }
            return minimals;
        }

        private String getGreatest() {
            if (getMaximals().size() == 1)
                return getMaximals().get(0);
            else
                return "NONE";
        }

        private String getLest() {
            if (getMinimals().size() == 1)
                return getMinimals().get(0);
            else
                return "NONE";
        }

        private class HasseNode {
            String data;
            ArrayList<HasseNode> nexts;
            ArrayList<HasseNode> prevs;

            public HasseNode(String data) {
                this.data = data;
                this.nexts = new ArrayList<>();
                this.prevs = new ArrayList<>();
            }

        }

    }

    public static String toString(String s) {
        String str = setMembers.length + "\n" + Arrays.toString(setMembers) + "\n";

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                str += matrix[i][j] + " ";
            str += "\n";
        }
        return (str);
    }
}

class Container<T, L extends Comparable> {
    private T key;
    private TreeSet<L> list;

    public Container(T key) {
        this.setKey(key);
        list = new TreeSet<L>();
    }

    public void addArray(L array[]) {
        for (int i = 0; i < array.length; i++)
            if (array[i] != null)
                list.add(array[i]);
    }

    public L[] getList() {
        return (L[]) (list.toArray());
    }

    public T getKey() {
        return key;
    }

    private void setKey(T key) {
        this.key = key;
    }

}