Решение приведенного в примере в методе main набора пробирок:
> Task :LiquidSortSolver.main()
Move 1 R from 0 to 3
Move 1 B from 0 to 2
Move 1 B from 1 to 2
Move 1 G from 1 to 0

Чтобы задать другое условие, нужно ввести другой двумерный массив String[][] input в
методе main перед компиляцией и запуском программы. Осторожно! Решение плохо
масштабируется при N > 10, т.к. количество состояний и используемая память для их
хранения растет экспоненциально.