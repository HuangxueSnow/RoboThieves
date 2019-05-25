/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robothieves;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author admin
 */
public class RoboThieves {

    static Scanner input = new Scanner(System.in);
    static char[][] graph;
    static int[][] NumGraph;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        dotestCase();
    }

    private static void dotestCase() {
        int row = input.nextInt();
        int col = input.nextInt();
        input.nextLine();
        graph = new char[row][col];
        NumGraph = new int[row][col];
        int[] sp = new int[2];
        ArrayList<Position> d = new ArrayList();
        ArrayList<Position> cp = new ArrayList();
        for (int i = 0; i < row; i++) {
            String li = input.nextLine();
            for (int j = 0; j < col; j++) {
                graph[i][j] = li.charAt(j);
                Position c = new Position();
                c.x = i;
                c.y = j;
                switch (graph[i][j]) {
                    case 'W':
                        NumGraph[i][j] = -1;
                        break;
                    case 'C':
                        NumGraph[i][j] = -2;
                        cp.add(c);
                        break;
                    case 'S':
                        NumGraph[i][j] = 0;
                        sp[0] = i;
                        sp[1] = j;
                        break;
                    case '.':
                        NumGraph[i][j] = -3;
                        d.add(c);
                        break;
                    default:
                        NumGraph[i][j] = -3;
                        break;
                }
            }
        }
        checkCposition(cp, row, col);
        Path(sp);
        print(row, col, d);
    }

    private static void checkCposition(ArrayList<Position> cp, int row, int col) {
        for (Position c : cp) {
            int x = c.x;
            int y = c.y;
            for (int i = x; i < row; i++) {
                if (graph[i][y] == 'W') {
                    break;
                }
                if (graph[i][y] == '.' || graph[i][y] == 'S') {
                    NumGraph[i][y] = -2;
                }
            }
            for (int i = x; i >= 0; i--) {
                if (graph[i][y] == 'W') {
                    break;
                }
                if (graph[i][y] == '.' || graph[i][y] == 'S') {
                    NumGraph[i][y] = -2;
                }
            }
            for (int i = y; i < col; i++) {
                if (graph[x][i] == 'W') {
                    break;
                }
                if (graph[x][i] == '.' || graph[x][i] == 'S') {
                    NumGraph[x][i] = -2;
                }
            }
            for (int i = y; i >= 0; i--) {
                if (graph[x][i] == 'W') {
                    break;
                }
                if (graph[x][i] == '.' || graph[x][i] == 'S') {
                    NumGraph[x][i] = -2;
                }
            }
        }

    }

    private static void Path(int[] sp) {
        if (NumGraph[sp[0]][sp[1]] == -2) {
            return;
        }
        ArrayList<Position> nowlist = new ArrayList();
        ArrayList<Position> nextlist = new ArrayList();
        Position pos = new Position();
        pos.x = sp[0];
        pos.y = sp[1];

        nowlist.add(pos);
        int s = 1;
        while (!nowlist.isEmpty()) {
            for (Position position : nowlist) {
                step(0, -1, nextlist, position, s);
                step(0, 1, nextlist, position, s);
                step(1, 0, nextlist, position, s);
                step(-1, 0, nextlist, position, s);
            }
            nowlist = nextlist;
            nextlist = new ArrayList();
            s++;
        }
    }

    private static void step(int i, int j, ArrayList<Position> nextlist, Position pos, int s) {
        Position pos1 = new Position();
        pos1.x = pos.x + i;
        pos1.y = pos.y + j;
        if (!allow(pos1.x, pos1.y)) {
            return;
        }
        if (NumGraph[pos1.x][pos1.y] == -2) {
            return;
        }
        if (NumGraph[pos1.x][pos1.y] == -3) {
            switch (graph[pos1.x][pos1.y]) {
                case 'L':
                    step(0, -1, nextlist, pos1, s);
                    break;
                case 'R':
                    step(0, 1, nextlist, pos1, s);
                    break;
                case 'U':
                    step(-1, 0, nextlist, pos1, s);
                    break;
                case 'D':
                    step(1, 0, nextlist, pos1, s);
                    break;
                case '.':
                    NumGraph[pos1.x][pos1.y] = s;
                    nextlist.add(pos1);
                    break;
            }
        } else if (NumGraph[pos1.x][pos1.y] > 0 && s < NumGraph[pos1.x][pos1.y]) {
            NumGraph[pos1.x][pos1.y] = s;
        }
    }

    private static boolean allow(int x, int y) {
        return x >= 0 && x < graph.length && y >= 0 && y < graph[0].length;
    }

    private static void print(int row, int col, ArrayList<Position> d) {
        for (Position p : d) {
            if (NumGraph[p.x][p.y] < 0) {
                System.out.println("-1");
            } else {
                System.out.println(NumGraph[p.x][p.y]);
            }
        }

    }

}

class Position {
    int x;
    int y;
}
