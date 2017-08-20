package d.candy.f.com.ralgo.utils;

import android.graphics.Paint;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by daichi on 8/20/17.
 */

public class UndirectedGraph<T> {

    public static class Edge {

        // For hashCode(), always, mSecond <= mFirst
        private int mFirst;
        private int mSecond;

        public Edge(int a, int b) {
            setNodes(a, b);
        }

        public boolean containsNode(int node) {
            return (mFirst == node || mSecond == node);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;
            return (mFirst == edge.mFirst && mSecond == edge.mSecond);
        }

        @Override
        public int hashCode() {
            int result = mFirst;
            result = 31 * result + mSecond;
            return result;
        }

        public void setNodes(int a, int b) {
            mFirst = (a < b) ? b : a;
            mSecond = (a < b) ? a : b;
        }

        public int first() {
            return mFirst;
        }

        public int second() {
            return mSecond;
        }
    }

    abstract public static class JoinOperator<U> {
        abstract boolean isJoinable(U node1, U node2);
        abstract boolean doPruningOrContinueJoiningForNode1
                (ArrayList<U> nodes, int prevProcessedNode1Index, int prevProcessedNode2Index);
    }

    private ArrayList<T> mNodes;
    private Set<Edge> mEdgeSet;
    private JoinOperator<T> mJoinOperator;

    public UndirectedGraph() {}

    public UndirectedGraph(Collection<T> nodes, Set<Edge> edges) {
        mNodes = new ArrayList<>(nodes);
        mEdgeSet = new HashSet<>(edges);
    }

    public void generateGraph(@NonNull Collection<T> nodes, @NonNull JoinOperator<T> joinOperator) {
        generateGraph(nodes, joinOperator, -1);
    }

    public void generateGraph(@NonNull Collection<T> nodes, @NonNull JoinOperator<T> joinOperator, int maxEdges) {
        mNodes = new ArrayList<>(nodes);
        mJoinOperator = joinOperator;

        if (maxEdges < 0) {
            mEdgeSet = new HashSet<>();
        } else {
            mEdgeSet = new HashSet<>(maxEdges);
        }

        joinNodes(mNodes, mEdgeSet, mJoinOperator);
    }

    private void joinNodes(@NonNull ArrayList<T> nodes, @NonNull Set<Edge> edges, @NonNull JoinOperator<T> joinOperator) {
        edges.clear();

        for (int i = 0; i < nodes.size(); ++i) {
            for (int j = i + 1; j < nodes.size(); ++j) {
                if (joinOperator.isJoinable(nodes.get(i), nodes.get(j))) {
                    edges.add(new Edge(i, j));
                }
                if (!joinOperator.doPruningOrContinueJoiningForNode1(nodes, i, j)) {
                    break;
                }
            }
        }
    }

    @NonNull
    public ArrayList<UndirectedGraph<T>> generateClusters() {
        ArrayList<UndirectedGraph<T>> clusters = new ArrayList<>();
        if (mNodes == null || mEdgeSet == null) {
            return clusters;
        }

        ArrayList<Edge> edges = new ArrayList<>(mEdgeSet);
        Edge edge;
        ArrayList<T> clusterNodes = new ArrayList<>();
        while (edges.size() != 0) {
            edge = edges.get(0);

        }
    }

    @NonNull
    private Set<Edge> findAndPopJoindedEdges(@NonNull Edge edge, @NonNull Set<Edge> edges) {
        Set<Edge> poped = new HashSet<>();
        ArrayList<Edge> stub = new ArrayList<>();
        for (Edge e: edges) {
            if (e.containsNode(edge.first()) || e.containsNode(edge.second())) {
                poped.add(e);
                stub.add(e);
            }
        }
        edges.removeAll(stub);
        return poped;
    }
}
