import java.util.*;

public class LiquidSortSolver {
    static class State {
        List<Stack<String>> tubes;
        List<String> moves;

        State(List<Stack<String>> tubes, List<String> moves) {
            this.tubes = tubes;
            this.moves = moves;
        }

        String serialize() {
            StringBuilder sb = new StringBuilder();
            for (Stack<String> tube : tubes) {
                sb.append(tube.toString()).append("|");
            }
            return sb.toString();
        }

        boolean isGoal() {
            for (Stack<String> tube : tubes) {
                if (tube.isEmpty()) continue;
                String color = tube.peek();
                for (String s : tube) {
                    if (!s.equals(color)) return false;
                }
            }
            return true;
        }

        State deepCopy() {
            List<Stack<String>> newTubes = new ArrayList<>();
            for (Stack<String> tube : tubes) {
                newTubes.add(new Stack<>());
                newTubes.get(newTubes.size() - 1).addAll(tube);
            }
            return new State(newTubes, new ArrayList<>(moves));
        }
    }

    public static List<String> solve(String[][] initial) {
        int N = initial.length;
        int V = initial[0].length;

        List<Stack<String>> startTubes = new ArrayList<>();
        for (String[] tubeArr : initial) {
            Stack<String> tube = new Stack<>();
            for (int i = tubeArr.length - 1; i >= 0; i--) {
                if (!tubeArr[i].isEmpty()) {
                    tube.push(tubeArr[i]);
                }
            }
            startTubes.add(tube);
        }

        Queue<State> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        State start = new State(startTubes, new ArrayList<>());
        queue.add(start);
        visited.add(start.serialize());

        while (!queue.isEmpty()) {
            State current = queue.poll();
            if (current.isGoal()) return current.moves;

            for (int i = 0; i < N; i++) {
                Stack<String> from = current.tubes.get(i);
                if (from.isEmpty()) continue;
                String color = from.peek();

                int count = 1;
                for (int j = from.size() - 2; j >= 0; j--) {
                    if (from.get(j).equals(color)) count++;
                    else break;
                }

                for (int j = 0; j < N; j++) {
                    if (i == j) continue;
                    Stack<String> to = current.tubes.get(j);
                    if (to.size() == V) continue;
                    if (!to.isEmpty() && !to.peek().equals(color)) continue;

                    int space = V - to.size();
                    int moveCount = Math.min(space, count);

                    State next = current.deepCopy();
                    Stack<String> nextFrom = next.tubes.get(i);
                    Stack<String> nextTo = next.tubes.get(j);
                    for (int k = 0; k < moveCount; k++) {
                        nextTo.push(nextFrom.pop());
                    }
                    next.moves.add("Move " + moveCount + " " + color + " from " + i + " to " + j);
                    String serialized = next.serialize();
                    if (!visited.contains(serialized)) {
                        visited.add(serialized);
                        queue.add(next);
                    }
                }
            }
        }

        return List.of("No solution found");
    }

    // Пример использования
    public static void main(String[] args) {
        String[][] input = {
                {"R", "B", "G", "G"},
                {"B", "G", "R", "R"},
                {"B", "", "", ""},
                {"", "", "", ""}
        };

        List<String> moves = solve(input);
        for (String move : moves) {
            System.out.println(move);
        }
    }
}