package cn.seiua.skymatrix.ai;

public class Node implements Comparable<Node> {
    int x, y, z;
    int g, h;
    Node parent;

    public Node(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getF() {
        return g + h;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.getF(), other.getF());
    }
}
